package cl.cbrs.etrus.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.log.SysoLogger;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.CertificateUtil;
import com.itextpdf.text.pdf.security.CrlClient;
import com.itextpdf.text.pdf.security.CrlClientOnline;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.OcspClient;
import com.itextpdf.text.pdf.security.OcspClientBouncyCastle;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import com.itextpdf.text.pdf.security.TSAClient;
import com.itextpdf.text.pdf.security.TSAClientBouncyCastle;

public class FirmadorBO {
	public void firmarDocumento(String origen, String destino) throws Exception {
		LoggerFactory.getInstance().setLogger(new SysoLogger());
		char[] pass = "Morande.440#".toCharArray();

		eTokenPkcs11Helper helper = new eTokenPkcs11Helper();
		helper.registerProvider();
		Provider providerPKCS11 = helper.getProvider(0);
		System.out.println(providerPKCS11.getName());
		BouncyCastleProvider providerBC = new BouncyCastleProvider();
		Security.addProvider(providerBC);
		KeyStore ks = KeyStore.getInstance("PKCS11");
		ks.load(null, pass);
		String alias = (String) ks.aliases().nextElement();
		PrivateKey pk = (PrivateKey) ks.getKey(alias, pass);
		Certificate[] chain = ks.getCertificateChain(alias);
		OcspClient ocspClient = new OcspClientBouncyCastle();
		TSAClient tsaClient = null;
		for (int i = 0; i < chain.length; i++) {
			X509Certificate cert = (X509Certificate) chain[i];
			String tsaUrl = CertificateUtil.getTSAURL(cert);
			if (tsaUrl != null) {
				tsaClient = new TSAClientBouncyCastle(tsaUrl);
				break;
			}
		}
		List<CrlClient> crlList = new ArrayList<CrlClient>();
		crlList.add(new CrlClientOnline(chain));
		this.sign(origen, destino, chain, pk, DigestAlgorithms.SHA256, providerPKCS11.getName(), CryptoStandard.CMS,
				"Firma Certificados", "Temuco", crlList, ocspClient, tsaClient, 0);
	}

	public void sign(String src, String dest, Certificate[] chain, PrivateKey pk, String digestAlgorithm,
			String provider, CryptoStandard subfilter, String reason, String location, Collection<CrlClient> crlList,
			OcspClient ocspClient, TSAClient tsaClient, int estimatedSize)
			throws GeneralSecurityException, IOException, DocumentException {
		// Creating the reader and the stamper
		PdfReader reader = new PdfReader(src);
		
		FileOutputStream os = new FileOutputStream(dest);
		PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
		// Creating the appearance
		PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
		AcroFields form = reader.getAcroFields();
		String campoFirma=getCampoFirma(form);
		if (!campoFirma.equals("")) {
			appearance.setVisibleSignature(campoFirma);
			appearance.setAcro6Layers(true);
			Image img = Image.getInstance("c:/FIRMA/firma.png");// (usuarioFirmador.getFirma());
			appearance.setLayer2Text(null);
			appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
			appearance.setSignatureGraphic(img);
			appearance.setImageScale(0);
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
			PdfContentByte over = stamper.getOverContent(1);
			over.beginText();
			over.setFontAndSize(bf, 7);
			appearance.getPageRect().getRight();
			over.endText();
			over.stroke();
		}
		appearance.setReason(reason);
		appearance.setLocation(location);
		//appearance.setVisibleSignature(new Rectangle(36, 748, 144, 780), 1, "sig");
		// Creating the signature
		ExternalSignature pks = new PrivateKeySignature(pk, digestAlgorithm, provider);
		ExternalDigest digest = new BouncyCastleDigest();
		MakeSignature.signDetached(appearance, digest, pks, chain, crlList, ocspClient, tsaClient, estimatedSize,
				subfilter);
		// stamper.close();
		reader.close();
		os.close();

	}

	private String getCampoFirma(AcroFields form) {
		ArrayList<String> fields = form.getBlankSignatureNames();
		String campoFirma = "";
		for (Iterator<String> iterator = fields.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			campoFirma = object.toString();
		}
		return campoFirma;
	}
}
