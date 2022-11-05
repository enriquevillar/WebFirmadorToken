package cl.etrust.firmador.firmadortoken.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoFirmaRepository extends JpaRepository<DocumentoFirma, Integer> {
    DocumentoFirma findFirstByFirmadoOrderByPrioridadDesc(Boolean firmado);

}
