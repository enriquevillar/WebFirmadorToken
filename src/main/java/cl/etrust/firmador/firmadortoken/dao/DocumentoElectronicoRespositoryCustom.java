package cl.etrust.firmador.firmadortoken.dao;

import java.time.OffsetDateTime;
import java.util.List;

public interface DocumentoElectronicoRespositoryCustom {
    public List<DocumentoFirmaElectronica> findDocumentoFirmaElectronicaByCaratulaAndDescripcionDocumentoStartsWithAndFechaDocumentoBetween(Long caratula, String descripcion, OffsetDateTime fechaDesde, OffsetDateTime fechaHasta);
}
