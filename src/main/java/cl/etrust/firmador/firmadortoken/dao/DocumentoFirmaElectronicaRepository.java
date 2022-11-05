package cl.etrust.firmador.firmadortoken.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.Doc;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

public interface DocumentoFirmaElectronicaRepository extends JpaRepository<DocumentoFirmaElectronica, Integer>,DocumentoElectronicoRespositoryCustom {
    public List<DocumentoFirmaElectronica> findAllByRutUsuarioPorFirmarAndEstado(String rutUsuarioFirmador,int Estado);
    public DocumentoFirmaElectronica findDocumentoFirmaElectronicaByCodigoVerificacion(String codigo);







}
