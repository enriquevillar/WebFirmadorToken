export class FirmaRequestVO {
  llx:number=0;
  lly:number=0;
  urx:number=0;
  ury:number=0;
  documentoBase64:string="";
  usuarioFirmador:string="";
  nombreArchivo:string="";
  pagina:number=1;
  codigoFirma:string="";
  idDocumento:number=0;
  constructor(llx:number,
  lly:number,
  urx:number,
  ury:number,
  documentoBase64:string,
  usuarioFirmador:string,
  nombreArchivo:string,
  pagina:number, codigoFirma:string,
  idDocumento:number
  ) {
    this.llx=llx;
    this.lly=lly;
    this.ury=ury;
    this.urx=urx;
    this.usuarioFirmador=usuarioFirmador;
    this.nombreArchivo=nombreArchivo;
    this.pagina=pagina;
    this.codigoFirma=codigoFirma;
    this.idDocumento=idDocumento;
  }
}
