import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
// import { Registro } from '../../models/registro/registro';

@Injectable()
export class DepartamentoService {

	private httpHeaders = new HttpHeaders();

  constructor(private httpClient: HttpClient) { }

  getDepartamentos(): Observable<any> {
  	const urlApi = '/api/departamento';
  	return this.httpClient.get(urlApi);
  }

}