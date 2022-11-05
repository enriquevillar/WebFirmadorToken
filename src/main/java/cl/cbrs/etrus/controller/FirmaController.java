package cl.cbrs.etrus.controller;


import cl.etrust.firmador.firmadortoken.dao.DocumentoFirma;
import cl.etrust.firmador.firmadortoken.dao.DocumentoFirmaElectronica;
import cl.etrust.firmador.firmadortoken.dao.DocumentoFirmaElectronicaRepository;
import cl.etrust.firmador.firmadortoken.dao.DocumentoFirmaRepository;
import cl.s3.ClienteS3;
import cl.vo.Archivo;
import cl.vo.BusquedaDocumentoRequestVO;
import cl.vo.CertificadoVO;
import com.amazonaws.util.IOUtils;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.text.pdf.security.*;
import lombok.extern.log4j.Log4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import java.util.*;
import java.util.stream.Stream;

@RestController
@CrossOrigin
@RequestMapping("/api/firma")
@Log4j
public class FirmaController {
    private static int ESTADO_NUEVO=1;
    private static final int ESTADO_ESPERA_FIRMA =2 ;
    private static int ESTADO_FIRMADO=3;
    private static com.safenetinc.luna.provider.LunaProvider provider;
    private static final String PASSWORD = "zp$Go$6?";
    private static KeyStore ks;
    @Value("${S3_BUCKET_NAME}")
    private String S3_BUCKET_NAME;

    private final DocumentoFirmaElectronicaRepository documentoFirmaElectronicaRepository;
    private final DocumentoFirmaRepository documentoFirmaRepository;
    public static final String PATH_IMG_FIRMAS = "c:/FIRMA/img";

    public FirmaController(DocumentoFirmaElectronicaRepository documentoFirmaElectronicaRepository, DocumentoFirmaRepository documentoFirmaRepository) {
        this.documentoFirmaElectronicaRepository = documentoFirmaElectronicaRepository;
        this.documentoFirmaRepository=documentoFirmaRepository;
    }


    @RequestMapping(value = "/firmar", method = RequestMethod.POST)
    public ResponseEntity<Map> firmarDocumentoPorUsuario(@RequestBody Archivo archivo){

        return new ResponseEntity<Map>(new HashMap(), HttpStatus.OK);
    }

    @RequestMapping(value = "/registrarDocumento", method = RequestMethod.POST)
    public ResponseEntity<DocumentoFirmaElectronica> registrarDocumento(@RequestBody DocumentoFirmaElectronica documento){
        try {
            long codigo=System.currentTimeMillis();
            Date date = new Date();
            OffsetDateTime offsetDateTime = date.toInstant().atOffset(ZoneOffset.UTC);
            documento.setFechaDocumento(offsetDateTime);
            documento.setEstado(ESTADO_NUEVO);
            documento.setFechaEstado(offsetDateTime);

            Optional<DocumentoFirma> optional= documento.getDocumentoFirmas().stream().findFirst();
            if (optional.isPresent())
               documento.setRutUsuarioPorFirmar(optional.get().getRutFirmador());
            documento=this.documentoFirmaElectronicaRepository.save(documento);
            String codigo_verificacion=documento.getId().toString()+"-"+Long.toHexString(codigo);
            documento.setCodigoVerificacion(codigo_verificacion);
            for (DocumentoFirma doc:documento.getDocumentoFirmas()) {
                doc.setIdDocumento(documento.getId());
                documentoFirmaRepository.save(doc);

            }
            this.documentoFirmaElectronicaRepository.saveAndFlush(documento);


            /*for (int i=0;i<documentos.length;i++) {
                  DocumentoFirma unDocumento=documentos[i];
                  unDocumento.setIdDocumento(documentoSaved.getId());
                 documentoFirmaRepository.save(unDocumento);
            }*/
            return new ResponseEntity<DocumentoFirmaElectronica>(documento,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/actualizarDocumento", method = RequestMethod.POST)
    public ResponseEntity<DocumentoFirmaElectronica> actualizarDocumento(@RequestBody DocumentoFirmaElectronica documento){
        try {
            DocumentoFirmaElectronica doc =this.documentoFirmaElectronicaRepository.getById(documento.getId());
            Date date = new Date();
            OffsetDateTime offsetDateTime = date.toInstant().atOffset(ZoneOffset.UTC);
            doc.setFechaEstado(offsetDateTime);
            doc.setEstado(documento.getEstado());
            DocumentoFirmaElectronica docSaved= this.documentoFirmaElectronicaRepository.save(doc);
            return new ResponseEntity<DocumentoFirmaElectronica>(docSaved,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/buscarDocumentosFirmados", method = RequestMethod.POST)
    public ResponseEntity<List<DocumentoFirmaElectronica>> buscarDocumentosFirmados(@RequestBody BusquedaDocumentoRequestVO busquedaVo){
        try {
             List<DocumentoFirmaElectronica> documentos=this.documentoFirmaElectronicaRepository.findDocumentoFirmaElectronicaByCaratulaAndDescripcionDocumentoStartsWithAndFechaDocumentoBetween(busquedaVo.getCaratula(),busquedaVo.getTipoDocumento(),busquedaVo.getFechaDesde(),busquedaVo.getFechaHasta());

            return new ResponseEntity<List<DocumentoFirmaElectronica>>(documentos,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/marcarComoFirmado", method = RequestMethod.POST)
    public ResponseEntity<DocumentoFirmaElectronica> marcarComoFirmado(@RequestBody DocumentoFirma documento){
        try {
            Date date = new Date();
            OffsetDateTime offsetDateTime = date.toInstant().atOffset(ZoneOffset.UTC);
            //DocumentoFirma documentoUpdate=this.documentoFirmaRepository.getById(documento.getId());
            documento.setFirmado(true);
            documento.setRutFirmador(documento.getRutFirmador());
            documento.setFechaFirma(offsetDateTime);
            this.documentoFirmaRepository.save(documento);
            DocumentoFirma nuevoFirmador=null;
            DocumentoFirmaElectronica documentoFirmaElectronica= this.documentoFirmaElectronicaRepository.getById(documento.getIdDocumento());
            for (DocumentoFirma unaFirma:documentoFirmaElectronica.getDocumentoFirmas()
                 ) {
                if (unaFirma.getId().intValue()!=documento.getId().intValue()){
                    nuevoFirmador=documento;
                }
            }
            if (nuevoFirmador!=null){
                documentoFirmaElectronica.setRutUsuarioPorFirmar(nuevoFirmador.getRutFirmador());
            }else{
                documentoFirmaElectronica.setEstado(ESTADO_FIRMADO);
            }
           DocumentoFirmaElectronica unDocumento= this.documentoFirmaElectronicaRepository.save(documentoFirmaElectronica);
            return new ResponseEntity<DocumentoFirmaElectronica>(unDocumento,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin
    @GetMapping("/getArchivosParaFirmar/{rutFirmador}")
    public ResponseEntity <List<DocumentoFirmaElectronica>> getDocumentosPorFirmador(@PathVariable("rutFirmador") String rutFirmador) {
          try {
              List<DocumentoFirmaElectronica> lista=  this.documentoFirmaElectronicaRepository.findAllByRutUsuarioPorFirmarAndEstado(rutFirmador,ESTADO_ESPERA_FIRMA);
            return new ResponseEntity<List<DocumentoFirmaElectronica>>(lista, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin
    @GetMapping("/getDocumentoById/{id}")
    public ResponseEntity <DocumentoFirmaElectronica> getDocumentoById(@PathVariable("id") Integer id) {
        try {
           Optional<DocumentoFirmaElectronica> unDocumento= this.documentoFirmaElectronicaRepository.findById(id);
           if(unDocumento.isPresent()) {
               DocumentoFirmaElectronica documento = (DocumentoFirmaElectronica) unDocumento.get();
               documento.setDocumentoFirmas(unDocumento.get().getDocumentoFirmas());
               return new ResponseEntity<DocumentoFirmaElectronica>(documento, HttpStatus.OK);
           }else
               return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin
    @GetMapping("/getDocumentoByCodigo/{codigo}")
    public ResponseEntity <DocumentoFirmaElectronica> getDocumentoByCodigo(@PathVariable("codigo") String codigo) {
        try {
            DocumentoFirmaElectronica unDocumento= this.documentoFirmaElectronicaRepository.findDocumentoFirmaElectronicaByCodigoVerificacion(codigo);
            if(unDocumento!=null) {
                  return new ResponseEntity<DocumentoFirmaElectronica>(unDocumento, HttpStatus.OK);
            }else
                 return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/verDocumento/{codigoVerificacion}", produces = MediaType.TEXT_HTML_VALUE, method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadByIdBoleta(@PathVariable("codigoVerificacion") String codigo, Principal principal){

        try {
            ClienteS3 clienteS3= new ClienteS3();
            InputStream is=clienteS3.downloadObject(codigo,S3_BUCKET_NAME);
            byte[] bytes = IOUtils.toByteArray(is);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            headers.add("content-disposition", "inline;filename=" + codigo + ".pdf");
            headers.setContentDispositionFormData(codigo + ".pdf", codigo + ".pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);

            //File targetFile = new File("c:/download/"+codigo+".pdf");
            //OutputStream outStream = new FileOutputStream(targetFile);
            //outStream.write(bytes);
            //outStream.close();

            return response;
        }catch(Exception e){
            return new ResponseEntity<byte[]>( HttpStatus.BAD_REQUEST);
        }
    }


    @CrossOrigin
    @GetMapping("/getArchivosParaFirmar")
    public ResponseEntity <String[]> getArchivosParaFirmar() {

        List<String> lista = new ArrayList<String>();
        try {
           File dir = new File("c:/firma/in");
           return new ResponseEntity<>(dir.list(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public  void sign(String src, String dest, Certificate[] chain, PrivateKey pk, int certificationLevel, String reason, String location,String campo,Rectangle rec,String imgFirma, String providerNeme,int pagina)
            throws GeneralSecurityException, IOException, DocumentException {
        try {
            System.out.println("Firmando --> ");
            PdfReader reader =  new PdfReader(Base64.decode(src));
            FileOutputStream os = new FileOutputStream(dest);
            PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
            PdfContentByte overContent = stamper.getOverContent( pagina);
            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
            appearance.setReason(reason);
            appearance.setLocation(location);
            //appearance.setVisibleSignature(rec,reader.getNumberOfPages(), campo);//1 = numero de pagina
            appearance.setVisibleSignature(rec,1, campo);//1 = numero de pagina
            Image img = Image.getInstance(PATH_IMG_FIRMAS + imgFirma);
            img.setTransparency(new int[] { 0x00, 0x10 });
            appearance.setLayer2Text(null);
            appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
            appearance.setSignatureGraphic(img);
            appearance.setCertificationLevel(certificationLevel);
            ExternalSignature pks = new PrivateKeySignature(pk, "SHA-256", providerNeme);
            ExternalDigest digest = new BouncyCastleDigest();
            MakeSignature.signDetached(appearance, digest, pks, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);
            System.out.println("Documento firmado Exitosamente destino "+dest);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
