import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpHeaders, HttpRequest} from '@angular/common/http';
import {Observable} from "rxjs/Observable";
import {AuthService} from "../../shared/guard/auth.service";
import {Ofertasdatatable} from "../../models/ofertasDatatable/ofertasdatatable";

@Injectable()
export class AdminService {

  private apiEditUser: string;
  private apiDeleteUser: string;
  private apiDestacada: string;
  private apiIntituciones: string;
  private apiCrearInstitucion: string;
  private apiFinanciacion: string;
  private apiAds: string;
  private apiDenuncia: string;


  constructor(private httpClient: HttpClient,
              private authServices: AuthService) {
    this.apiEditUser = '/api/usuario/';
    this.apiDeleteUser = '/api/usuario/';
    this.apiDestacada = '/api/oferta/admin/';
    this.apiIntituciones = '/api/institucion/admin/';
    this.apiCrearInstitucion = '/api/institucion';
    this.apiFinanciacion = '/api/financiacion/';
    this.apiAds = '/api/publicidad';
    this.apiDenuncia = '/api/denuncia/';
  }

  editUser(id: number, body: any): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    headers = headers.set('Authorization','Bearer ' + this.authServices.getToken());
    return this.httpClient.put(this.apiEditUser + id, body, {headers});
  }

  eliminarUsuario(idUsuario: number): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Authorization','Bearer ' + this.authServices.getToken());
    return this.httpClient.delete(this.apiDeleteUser + idUsuario, {headers});
  }

  setDestacada(id: number, body: Ofertasdatatable): Observable<any>{
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    headers = headers.set('Authorization','Bearer ' + this.authServices.getToken());
    return this.httpClient.put(this.apiDestacada + id, body, {headers});
  }

  editarInstitucion(id: number, body: Ofertasdatatable): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    headers = headers.set('Authorization','Bearer ' + this.authServices.getToken());
    return this.httpClient.put(this.apiIntituciones + id, body, {headers});
  }

  crearInstitucion(institucion: any ): Observable<any> {
    let headers =  new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.authServices.getToken());
    headers = headers.set('Content-Type', 'application/json');
    return this.httpClient.post(this.apiCrearInstitucion, institucion, {headers});
  }

  editarFinanciacion(id: number, body: any): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    headers = headers.set('Authorization','Bearer ' + this.authServices.getToken());
    return this.httpClient.put(this.apiFinanciacion + id, body, {headers});
  }

  eliminarFinanciacion(id: number): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    headers = headers.set('Authorization','Bearer ' + this.authServices.getToken());
    return this.httpClient.delete(this.apiFinanciacion + id, {headers});
  }
  //publicidad
  uploadImageAd(file: File, url, idBanner): Observable<HttpEvent<{}>> {
    let formdata: FormData = new FormData();

    formdata.append('file', file);
    formdata.append('idBanner', idBanner);
    formdata.append('url', url);

    const req = new HttpRequest('POST', '/api/api/publicidad/banner', formdata, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.httpClient.request(req);
  }

  listarAds(): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    return this.httpClient.get(this.apiAds, {headers});
  }

  agregarFinanciacion(body: any): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    headers = headers.set('Authorization','Bearer ' + this.authServices.getToken());
    return this.httpClient.post(this.apiFinanciacion, body, {headers});
  }

  editarDenuncia(id: number, body: Ofertasdatatable): Observable<any>{
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    headers = headers.set('Authorization','Bearer ' + this.authServices.getToken());
    return this.httpClient.put(this.apiDenuncia + id, body, {headers});
  }

}
