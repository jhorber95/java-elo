import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import { GLOBAL } from './global';


@Injectable()
export class MunicipioService {

  private urlMuicipio: string;

  constructor( private _http: Http, private httpClient: HttpClient) {
    this.urlMuicipio = GLOBAL.url + '/api/municipio?idDepartamento=1';
  }

  getMunicipio(): Observable<any> {
  	const urlApi = '/api/municipio?idDepartamento=1';
    return this.httpClient.get(urlApi);
  }

  getMunicipioByIdDepartamento(idDepartamento: number): Observable<any> {
   	const urlApi = '/api/municipio?idDepartamento=' + idDepartamento;
   	return this.httpClient.get(urlApi);
  }

}
