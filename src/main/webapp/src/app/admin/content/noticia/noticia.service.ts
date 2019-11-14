import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpHeaders, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {INoticia} from '../../../models/noticia';
import {AuthService} from '../../../shared/guard/auth.service';


type EntityResponseType = HttpResponse<INoticia>;

@Injectable()
export class NoticiaService {

  private apiUrl = '/api/noticia';

  constructor(private http: HttpClient,  private authService: AuthService) { }

  getAll(page: number, size: number): Observable<any> {
    return this.http.get(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  getOne(id: number): Observable<INoticia> {
    return this.http.get(`${ this.apiUrl }/${ id }`);
  }

  create(entity: INoticia): Observable<INoticia> {
    let headers =  new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
    return this.http.post(this.apiUrl, entity, { headers });
  }

  update(entity: INoticia): Observable<INoticia> {
    let headers =  new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
    return this.http.put(this.apiUrl, entity, { headers });
  }

  delete(id: number): Observable<any> {
    let headers =  new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
    return this.http.delete(`${this.apiUrl}/${id}`, { headers });
  }

  uploadImage(file: File): Observable<HttpEvent<{}>> {
    const formdata: FormData = new FormData();

    formdata.append('file', file);

    const req = new HttpRequest('POST', '/api/imagen/noticia', formdata, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.http.request(req);
  }

  getNewsImage(filename: string) {

  }
}
