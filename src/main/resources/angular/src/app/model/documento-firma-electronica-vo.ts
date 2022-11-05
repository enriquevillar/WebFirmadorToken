export class DocumentoFirmaElectronicaVO {
  id:number=0;
  nombreDocumento:string=""
  descripcionDocumento:string=""
  fechaDocumento:string=""
  codigoVerificacion:string=""
  estado:number=0;
  fechaEstado:string=""
  rutUsuarioPorFirmar:string="";

  constructor( id:number,
  nombreDocumento:string,
  descripcionDocumento:string,
  fechaDocumento:string,
  codigoVerificacion:string,
  estado:number,
  fechaEstado:string,
  rutUsuarioPorFirmar:string) {
    this.id=id
    this.nombreDocumento=nombreDocumento
    this.descripcionDocumento=descripcionDocumento
    this.fechaDocumento=fechaDocumento
    this.codigoVerificacion=codigoVerificacion
    this.estado=estado
    this.fechaEstado=fechaEstado
    this.rutUsuarioPorFirmar=rutUsuarioPorFirmar;
  }

}

