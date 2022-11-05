import { Component, ViewChild, ElementRef,HostListener } from '@angular/core';
import {FirmadorServices} from "../../services/firmador.service";
import {Archivo} from "../../model/archivo";
import {DocumentoFirmaElectronicaVO} from "../../model/documento-firma-electronica-vo";
import { DomSanitizer} from '@angular/platform-browser';
import {FirmaRequestVO} from "../../model/firma-request-vo";
import {FirmadorLocalServiceService} from "../../services/firmador-local-service.service";
import {IconSetService} from "@coreui/icons-angular";
import {brandSet, cilListNumbered, cilPaperPlane,cilPencil,cilPageview} from "@coreui/icons-pro";



@Component({
  templateUrl: 'dashboard.component.html',
  styleUrls: ['dashboard.component.scss']
})
export class DashboardComponent {
  @ViewChild("outsideElement", {static: true}) outsideElement! : ElementRef;
  @ViewChild('modalView', {static: true}) modalView$! : ElementRef;
  documentos:DocumentoFirmaElectronicaVO[]=[];
 // pdfDefaultOptions.assetsFolder = 'bleeding-edge';
  public visible = false;
  public fileURL:any;
  public cargando= false;
  constructor(private servicioFirma:FirmadorServices,private sanitizer: DomSanitizer,private servicioFirmaLocal:FirmadorLocalServiceService,public iconSet: IconSetService) {
    this.obtenerArchivos();
    iconSet.icons = { cilListNumbered, cilPaperPlane,cilPageview,cilPencil, ...brandSet };
  }


 firmar(nombre:string,idDocumento:number){
      console.log("ID documento "+idDocumento)
      this.cargando=true;
      var firmaRequest:FirmaRequestVO=new FirmaRequestVO(0,0,0,0,"0","","",1,nombre,idDocumento);
      this.servicioFirmaLocal.firmar(firmaRequest).subscribe({
        next: data => {
              this.obtenerArchivos();
               this.cargando=false;
        }
      })
 }
  ver(nombre:string){

  }
  private obtenerArchivos() {
    this.cargando=true;
    this.servicioFirma.getArchivosParaFirmar().subscribe({
      next: data => {
        this.documentos=data;
        this.cargando=false;
      }
    })
  }


  handleLiveDemoChange(event: any) {
    this.visible = event;
  }
  handleLiveDemoChange2(event: any) {
    this.cargando = event;
  }

  toggleLiveDemo() {
    this.visible = !this.visible;
  }


  openModal(codigoVerificacion:string) {
    this.servicioFirma.verArchivo(codigoVerificacion).subscribe((response)=>{

        var file = new Blob([response], { type: 'application/pdf' });
        //var file = new Blob([data.data], {type: 'text/html'});
        this.fileURL = URL.createObjectURL(file);
        //var fileURL = URL.createObjectURL(file);
        //this.fileURL= this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(file));
        //window.open(this.fileURL);

    });
    this.visible = !this.visible;
  }

  firmarTodo() {
    this.cargando=true;
    var documentosParaFirmar:FirmaRequestVO[]=[];
    for(var i:number=0;i<this.documentos.length;i++) {
      var firmaRequest: FirmaRequestVO = new FirmaRequestVO(0, 0, 0, 0, "0", "", "", 1, this.documentos[i].codigoVerificacion, this.documentos[i].id);
      documentosParaFirmar.push(firmaRequest)
    }
    this.servicioFirmaLocal.firmarTodo(documentosParaFirmar).subscribe({
      next: data => {
        this.documentos=data;
        this.cargando=false;
      }, error: (err: Error) => {console.error('Observer got an error: ' + err)
        this.obtenerArchivos();
        this.cargando=false;
      },complete:()=>{
        this.obtenerArchivos();
        this.cargando=false;
      }
    })
  }

  cerrar() {
    this.visible = !this.visible;
  }


}
