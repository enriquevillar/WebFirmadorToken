package cl.etrust.firmador.firmadortoken.dao;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "documento_firma")
public class DocumentoFirma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "id_documento", nullable = false)
    private Integer idDocumento;

    @Column(name = "prioridad", nullable = false)
    private Integer prioridad;

    @Column(name = "rut_firmador", nullable = false, length = 10)
    private String rutFirmador;

    @Column(name = "firmado")
    private Boolean firmado;

    @Column(name = "fecha_firma")
    private OffsetDateTime fechaFirma;

    @Column(name = "llx")
    private Double llx;

    @Column(name = "lly")
    private Double lly;

    @Column(name = "urx")
    private Double urx;

    @Column(name = "ury")
    private Double ury;

    public Double getUry() {
        return ury;
    }

    public void setUry(Double ury) {
        this.ury = ury;
    }

    public Double getUrx() {
        return urx;
    }

    public void setUrx(Double urx) {
        this.urx = urx;
    }

    public Double getLly() {
        return lly;
    }

    public void setLly(Double lly) {
        this.lly = lly;
    }

    public Double getLlx() {
        return llx;
    }

    public void setLlx(Double llx) {
        this.llx = llx;
    }

    public OffsetDateTime getFechaFirma() {
        return fechaFirma;
    }

    public void setFechaFirma(OffsetDateTime fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public Boolean getFirmado() {
        return firmado;
    }

    public void setFirmado(Boolean firmado) {
        this.firmado = firmado;
    }

    public String getRutFirmador() {
        return rutFirmador;
    }

    public void setRutFirmador(String rutFirmador) {
        this.rutFirmador = rutFirmador;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
