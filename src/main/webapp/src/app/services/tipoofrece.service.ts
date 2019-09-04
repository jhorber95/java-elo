import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import { GLOBAL } from './global';

@Injectable()
export class TipoofreceService {

  private urlTipoOfrece: string;

  constructor( private _http: Http) {
    this.urlTipoOfrece = GLOBAL.url + '/api/tipoOfrece';
  }

  getTipoOfrece() {
    return this._http.get(this.urlTipoOfrece)
                .map(res => res.json());
  }

}
