import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import { GLOBAL } from '../global';


@Injectable()
export class DetallefinancieraService {

  private urlDetalleFinanciera: string;

  constructor( private _http: Http) {
    this.urlDetalleFinanciera = GLOBAL.url + '/api/financiacion/financiera/';
  }

  getDetalleFinanciera(id) {
    return this._http.get(this.urlDetalleFinanciera + id)
              .map(res => res.json());
  }

}
