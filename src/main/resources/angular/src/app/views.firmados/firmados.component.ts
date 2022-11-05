import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {FirmadorServices} from "../services/firmador.service";
import {BusquedaRequestVo} from "../model/busqueda-request-vo";
import {DocumentoFirmaElectronicaVO} from "../model/documento-firma-electronica-vo";

@Component({
  selector: 'app-firmados',
  templateUrl: './firmados.component.html',
  styleUrls: ['./firmados.component.scss']
})
export class FirmadosComponent implements OnInit {
  documentos:DocumentoFirmaElectronicaVO[]=[];
  public cargando= false;
  form= new FormGroup({
    caratula: new FormControl(),
    tipoDocumento: new FormControl(),
    fechaDesde: new FormControl(),
    fechaHasta: new FormControl(),
  })
  constructor(private fb: FormBuilder,private servicioFirma:FirmadorServices) {

  }

  ngOnInit(): void {
  }

  buscar(form: FormGroup) {
    var caratula:number=this.form.get("caratula")?.value;
    var descripcion:string=this.form.get("tipoDocumento")?.value;
    var fechaDesde:string=this.form.get("fechaDesde")?.value;
    var fechaHasta:string=this.form.get("fechaHasta")?.value;
    var  busqueda:BusquedaRequestVo=new BusquedaRequestVo(caratula,descripcion,fechaDesde,fechaHasta);
    this.cargando=true;
    this.servicioFirma.buscar(busqueda).subscribe({
      next: data => {
        this.documentos=data;
        this.cargando=false;
      }
    })


  }
  handleLiveDemoChange2(event: any) {
    this.cargando = event;
  }

}
