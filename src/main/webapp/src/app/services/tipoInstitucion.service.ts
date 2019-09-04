import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class TipoInstitucionService {

  private urlTipoOferta: string;

  constructor( private httpClient: HttpClient) {
  }

  getTipoInstitucion(): Observable<any> {
  	const urlApi = '/api/tipoInstitucion';
    return this.httpClient.get(urlApi);
  }

}
