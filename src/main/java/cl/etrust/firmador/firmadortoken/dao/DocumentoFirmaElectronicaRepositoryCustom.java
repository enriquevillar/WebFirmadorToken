package cl.etrust.firmador.firmadortoken.dao;

import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface DocumentoFirmaElectronicaRepositoryCustom {
    List<DocumentoFirmaElectronica> findDocumentoFirmaElectronicaByCaratulaAndDescripcionDocumentoAndFechaDocumentoBetween(Long caratula, String descripcion, Instant fechaDesde, Instant fechaHasta);
}
