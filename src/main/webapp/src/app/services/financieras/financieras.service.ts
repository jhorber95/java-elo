import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import { GLOBAL } from '../global';

@Injectable()
export class FinancierasService {

  private urlFinancieras: string;

  constructor( private _http: Http) {
    this.urlFinancieras = GLOBAL.url + '/api/institucion/financieras';
  }

  getFinancieras() {
    return this._http.get(this.urlFinancieras)
                .map(res => res.json());
  }

}
