export class BusquedaRequestVo {
caratula:number=0;
tipoDocumento:string="";
fechaDesde:string="";
fechaHasta:string="";
constructor(
  caratula:number,
  tipoDocumento:string,
  fechaDesde:string,
  fechaHasta:string) {
  this.caratula=caratula;
  this.tipoDocumento=tipoDocumento;
  this.fechaDesde=fechaDesde;
  this.fechaHasta=fechaHasta
}
}
