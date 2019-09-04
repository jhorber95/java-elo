import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class ContactoService {

  constructor(private httpClient: HttpClient) { }

  contacto(nombres: string, email: string, celular: number, asunto: string, mensaje: string ): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');
    const body = 'nombres='+nombres+'&email='+email+'&celular='+celular+'&asunto='+asunto+'&mensaje='+mensaje;
    return this.httpClient.post('/api/contacto', body, {headers});
  }

}
