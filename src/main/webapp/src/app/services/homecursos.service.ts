import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import { GLOBAL } from './global';

@Injectable()
export class HomecursosService {

  private urlOfertas: string;
  private urlEventos: string;
  private urlFinancieras: string;

  constructor( private _http: Http,
               public _httpClient: HttpClient) {
    this.urlOfertas = GLOBAL.url + '/api/oferta/destacadas';
    this.urlEventos = GLOBAL.url + '/api/evento?';
    this.urlFinancieras = GLOBAL.url + '/api/institucion/financieras?';
  }

  getCursosHome(): Observable<any> {
    return this._httpClient.get(this.urlOfertas);
  }

  getEventosHome() {
    return this._http.get(this.urlEventos)
                .map(res => res.json());
  }

  getFinancierasLimit(start: number, length: number ): Observable<any>{
    return this._httpClient.get(this.urlFinancieras + 'start=' + start + '&length=' + length);
  }

  getEventosLimit(start: number, length: number ): Observable<any>{
    return this._httpClient.get(this.urlEventos + 'start=' + start + '&length=' + length);
  }

}
