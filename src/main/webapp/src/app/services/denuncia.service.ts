import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { AuthService }      from '../shared/guard/auth.service';

@Injectable()
export class DenunciaService {


  constructor(private httpClient: HttpClient,
  			  private authService: AuthService) { }

  sendDenuncia(pqr: any): Observable<any> {
    const urlApi = '/api/denuncia';
    const headers = this.authService.headerAuthorization();
    return this.httpClient.post(urlApi, pqr, {headers});
  }
}
