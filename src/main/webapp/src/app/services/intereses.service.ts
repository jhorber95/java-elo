import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';


@Injectable()
export class InteresesService {


  constructor(private httpClient: HttpClient) { }

  getIntereses(): Observable<any> {
    const urlApi = '/api/interes';
    return this.httpClient.get(urlApi);
  }
}
