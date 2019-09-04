import { Injectable } from '@angular/core';
// import { Http, Response} from '@angular/http';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
// import 'rxjs/add/operator/toPromise';
// import { Observable } from 'rxjs/Observable';
// import { Categoria} from '../models/categoria';
import { GLOBAL } from './global';

@Injectable()
export class SubCategoriaService {

  private urlApi = GLOBAL.url + '/api/categoria';

  constructor(private httpClient: HttpClient) { }

  getSubCategorias(): Observable<any> {
    const urlApi = '/api/categoria';
    return this.httpClient.get(urlApi);
  }
}
