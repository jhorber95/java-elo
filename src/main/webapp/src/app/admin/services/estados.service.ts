import { Injectable } from '@angular/core';
import { Observable } from "rxjs/Observable";
import { HttpClient } from '@angular/common/http'

@Injectable()
export class EstadosService {

  private urlEstados = "";
  constructor(private httpClient: HttpClient) {
    this.urlEstados = '/api/estado';
  }

  getEstados(): Observable<any> {
    return this.httpClient.get(this.urlEstados);
  }

}
