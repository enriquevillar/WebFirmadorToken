
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {AppConfigService} from '../providers/app-config-service.service';
import {Archivo} from "../model/archivo";
import {BusquedaRequestVo} from "../model/busqueda-request-vo";


@Injectable({
  providedIn: 'root'
})
export class FirmadorServices{
  errorMsg: string="";
  private baseUrl: string;
  private URL_BASE: string;
  constructor(private http: HttpClient,private config: AppConfigService) {
    this.URL_BASE=this.config.getConfig().URL;
    this.baseUrl= this.URL_BASE+"/api/firma";
  }

  getArchivosParaFirmar() :Observable<any>{
    return this.http.get<any>(this.baseUrl+'/getArchivosParaFirmar/13836929-3');
  }
  verArchivo(codigoVerificacion:string) :Observable<any>{
   let headers = new HttpHeaders({ 'Content-Type': 'application/arraybuffer' });
    return this.http.get(this.baseUrl+'/verDocumento/'+codigoVerificacion, { responseType: "blob" });
  }

  firmar(archivo:Archivo):Observable<any>{
    return this.http.post<any>(this.baseUrl+'/firmar', archivo )
    //return this.http.post<any>(this.baseUrl+'/resumenEmpleado/'+numero);
  }
  eliminar(idLicencia: number) :Observable<any>{
    return this.http.get<any>(this.baseUrl+'/eliminaLicencia/'+idLicencia);
  }
  buscar(buscador:BusquedaRequestVo){
    return this.http.post<any>(this.baseUrl+'/buscarDocumentosFirmados',buscador);
  }

}
