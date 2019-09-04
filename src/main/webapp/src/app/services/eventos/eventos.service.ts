import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import { GLOBAL } from '../global';
import {GlobCopyWebpackPlugin} from '@angular/cli/plugins/glob-copy-webpack-plugin';

@Injectable()
export class EventosService {

  private urlEventos: string;
  private apiEventos: string;

  constructor(
    public _http: HttpClient
  ) {
    this.urlEventos = GLOBAL.url + '/api/evento';
    this.apiEventos = GLOBAL.url + '/api/evento?start=0&length=999999';
  }

  getEventos(): Observable<any> {
    return this._http.get(this.apiEventos);
  }

  getDetalleEvento(id): Observable<any> {
    return this._http.get(this.urlEventos + '/' + id);
  }

}
