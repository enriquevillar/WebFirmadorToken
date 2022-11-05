package cl.etrust.firmador.firmadortoken.dao;

import cl.vo.BusquedaDocumentoRequestVO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class DocumentoElectronicoRespositoryImpl implements DocumentoElectronicoRespositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    public List<DocumentoFirmaElectronica> findDocumentoFirmaElectronicaByCaratulaAndDescripcionDocumentoStartsWithAndFechaDocumentoBetween(Long caratula, String descripcion, OffsetDateTime fechaDesde, OffsetDateTime fechaHasta) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DocumentoFirmaElectronica> cq = cb.createQuery(DocumentoFirmaElectronica.class);
        Root<DocumentoFirmaElectronica> documento = cq.from(DocumentoFirmaElectronica.class);
        List<Predicate> predicates = new ArrayList<>();
        if (caratula!=null){
            predicates.add(cb.equal(documento.get("caratula"),caratula));
        }
        if (descripcion!=null){
            predicates.add(cb.like(documento.get("descripcionDocumento"),descripcion+"%"));
        }
        if (fechaDesde!=null){
            predicates.add(cb.between(documento.get("fechaDocumento"),fechaDesde,fechaHasta));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }


}
