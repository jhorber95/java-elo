import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import { GLOBAL } from '../global';


@Injectable()
export class DetalleuniversidadService {

  constructor( private httpClient: HttpClient) {
  }

  getDetalleUniversidad(id): Observable<any> {
    const ulrApi = '/api/institucion/' + id;
    return this.httpClient.get(ulrApi);
  }

}
