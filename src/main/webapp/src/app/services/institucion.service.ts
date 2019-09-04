import { Injectable } from '@angular/core';
import { HttpClient,
  HttpHeaders,
  HttpRequest } from '@angular/common/http';
import { Observable} from 'rxjs/Observable';

@Injectable()
export class InstitucionService {

  constructor(private httpClient: HttpClient) { }

  getAllIntitciones(): Observable<any> {
    const urlApi = '/api/institucion/todas';
    return this.httpClient.get(urlApi);
  }
}
