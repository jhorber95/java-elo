import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import { GLOBAL } from '../global';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class OfertasService {

  private urlSearchSinP: string;
  private urlSearchConP: string;
  private urlInstituciones: string;
  private urlFinancieras: string;

  private perPage = 10;
  private start: number;
  private end: number;

  constructor( private _http: Http, private httpClient: HttpClient) {
    this.urlSearchSinP = '/api/oferta/buscador?start=0&length=10';
    this.urlSearchConP = '/api/oferta/buscador?start=0&length=10&search=';
    this.urlInstituciones = '/api/institucion?start=10&length=10';
    this.urlFinancieras = '/api/institucion/financieras?start=0&length=10';
  }

  getSerarchSinP(page): Observable<any> {
    this.start = (page - 1) * this.perPage;
    this.end = this.start + this.perPage;

    const urlApi = '/api/oferta/buscador?start=' + this.start + '&length=' + this.perPage ;
    return this.httpClient.get(urlApi);
  }

  getSerarchConP(search): Observable<any> {
    const urlApi = '' ;
    return this.httpClient.get(this.urlSearchConP + search);
  }

  search(start, length, stringSeach): Observable<any> {
    const urlApi = '/api/oferta/buscador?start=' + start + '&length=' + length + '&search=' + stringSeach ;
    // console.log(urlApi);
    return this.httpClient.get(urlApi);
  }

  getInsticiones(): Observable<any> {
    return this.httpClient.get(this.urlInstituciones);
  }

  getFinancieras(): Observable<any> {
    return this.httpClient.get(this.urlFinancieras);
  }

}
