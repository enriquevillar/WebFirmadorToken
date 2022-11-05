import { Injectable } from '@angular/core';
import {Archivo} from "../model/archivo";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {FirmaRequestVO} from "../model/firma-request-vo";

@Injectable({
  providedIn: 'root'
})
export class FirmadorLocalServiceService {
  errorMsg: string="";
  private baseUrl: string;
  private URL_BASE: string;
  constructor(private http: HttpClient,) {
    this.URL_BASE="http://localhost:4040";
    this.baseUrl= this.URL_BASE+"/api/firma";

  }
  firmar(archivo:FirmaRequestVO):Observable<any>{
    return this.http.post<any>(this.baseUrl+'/firmar', archivo )
    //return this.http.post<any>(this.baseUrl+'/resumenEmpleado/'+numero);
  }
  firmarTodo(documentosParaFirmar: FirmaRequestVO[]) {
    return this.http.post<any>(this.baseUrl+'/firmarTodo', documentosParaFirmar )
  }
}
