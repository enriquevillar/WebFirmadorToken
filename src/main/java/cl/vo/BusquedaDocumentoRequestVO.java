package cl.vo;

import lombok.Data;

import java.time.Instant;
import java.time.OffsetDateTime;

@Data
public class BusquedaDocumentoRequestVO {
   private Long caratula;
   private String tipoDocumento;
   private OffsetDateTime fechaDesde;
   private OffsetDateTime fechaHasta;
}
