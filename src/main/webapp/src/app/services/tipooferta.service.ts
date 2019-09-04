import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { GLOBAL } from './global';

@Injectable()
export class TipoofertaService {

  private urlTipoOferta: string;

  constructor( private httpClient: HttpClient) {
    this.urlTipoOferta = GLOBAL.url + '/api/tipoOferta';
  }

  getTipoOferta(): Observable<any>{
  	const urlApi = '/api/tipoOferta';
    return this.httpClient.get(urlApi);
  }

}
