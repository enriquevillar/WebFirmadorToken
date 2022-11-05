export class DocumentoFirmaVO {
  id:number=0;
  idDocumento:number=0;
  prioridad:number=0;
  rutFirmador:string="";
  firmado:boolean=false;
  fechaFirma:string="";
  documentoFirmas:DocumentoFirmaVO[]=[];
  constructor(id:number, idDocumento:number,
  prioridad:number,
  rutFirmador:string,
  firmado:boolean,
  fechaFirma:string,documentoFirmas:DocumentoFirmaVO[]) {
    this.id=id
    this.idDocumento=idDocumento
    this.prioridad=prioridad
    this.rutFirmador=rutFirmador
    this.firmado=firmado
    this.fechaFirma=fechaFirma;
    this.documentoFirmas=documentoFirmas;
  }
}
