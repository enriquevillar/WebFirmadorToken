package cl.vo;

import lombok.Data;

@Data
public class FirmaRequestVO {
    private float llx;
    private float lly;
    private float urx;
    private float ury;
    private String documentoBase64;
    private String usuarioFirmador;
    private String nombreArchivo;
    private int pagina;
 }
