package cl.etrust.firmador.firmadortoken.dao;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "documento_firma_electronica")
public class DocumentoFirmaElectronica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre_documento", nullable = false, length = 500)
    private String nombreDocumento;

    @Column(name = "descripcion_documento", length = 500)
    private String descripcionDocumento;

    @Column(name = "fecha_documento", nullable = false)
    private OffsetDateTime fechaDocumento;

    @Column(name = "codigo_verificacion",  length = 500)
    private String codigoVerificacion;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "caratula")
    private Long caratula;

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @Column(name = "fecha_estado")
    private OffsetDateTime fechaEstado;

    @Column(name = "rut_usuario_por_firmar", length = 10)
    private String rutUsuarioPorFirmar;

    @OneToMany(mappedBy = "idDocumento")
    private Set<DocumentoFirma> documentoFirmas = new LinkedHashSet<>();

    public Long getCaratula() {
        return caratula;
    }

    public void setCaratula(Long caratula) {
        this.caratula = caratula;
    }

    public Set<DocumentoFirma> getDocumentoFirmas() {
        return documentoFirmas;
    }

    public void setDocumentoFirmas(Set<DocumentoFirma> documentoFirmas) {
        this.documentoFirmas = documentoFirmas;
    }

    public String getRutUsuarioPorFirmar() {
        return rutUsuarioPorFirmar;
    }

    public void setRutUsuarioPorFirmar(String rutUsuarioPorFirmar) {
        this.rutUsuarioPorFirmar = rutUsuarioPorFirmar;
    }

    public OffsetDateTime getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(OffsetDateTime fechaEstado) {
        this.fechaEstado = fechaEstado;
    }



    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public void setCodigoVerificacion(String codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }

    public OffsetDateTime getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(OffsetDateTime fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getDescripcionDocumento() {
        return descripcionDocumento;
    }

    public void setDescripcionDocumento(String descripcionDocumento) {
        this.descripcionDocumento = descripcionDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
