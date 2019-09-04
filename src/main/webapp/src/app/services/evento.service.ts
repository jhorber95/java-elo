import { Injectable } from '@angular/core';
import { HttpClient, 
		 HttpHeaders,
		 HttpParams,
		 HttpEvent,
		 HttpRequest } from '@angular/common/http';
import { Observable} from 'rxjs/Observable';
import { AuthService }      from '../shared/guard/auth.service';

@Injectable()
export class EventoService {

  constructor(private httpClient: HttpClient, private authService: AuthService ) { }

  getEventos(idInstitucion: number): Observable<any> {
    const urlApi = '/api/evento/institucion/' + idInstitucion;
    const headers = this.authService.headerAuthorization();
    return this.httpClient.get(urlApi, {headers});
  }

  getAllEvents(): Observable<any> {
    const urlApi = '/api/evento/dataTables/';
    const headers = this.authService.headerAuthorization();
    return this.httpClient.post(urlApi, {headers});
  }

  deleteEvento(idEvento: number):  Observable<any> {
  	const urlApi = '/api/evento/' + idEvento;
  	const headers = this.authService.headerAuthorization();
    return this.httpClient.delete(urlApi, {headers});
  }

  createEvento(evento: any):  Observable<any>{
  	const urlApi = '/api/evento/';
  	const headers = this.authService.headerAuthorization();
    return this.httpClient.post(urlApi, evento, {headers});
  }

  getEventoById(idEvento):  Observable<any>{
  	const urlApi = '/api/evento/' + idEvento;
  	const headers = this.authService.headerAuthorization();
    return this.httpClient.get(urlApi, {headers});
  }

  updateEvento(evento: any):  Observable<any>{
    const urlApi = '/api/evento/' + evento.id;
    const headers = this.authService.headerAuthorization();
    return this.httpClient.put(urlApi, evento, {headers});
  }

  uploadImageEvent(file: File, idEvento): Observable<HttpEvent<{}>> {
    let formdata: FormData = new FormData();
 
    formdata.append('file', file);
    formdata.append('idEvento', idEvento);
 
    const req = new HttpRequest('POST', '/api/imagen/evento', formdata, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.httpClient.request(req);
  }
}