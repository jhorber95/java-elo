import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpRequest, HttpEvent, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Registro } from '../../models/registro/registro';
import { ProfileUsers }  from '../../models/profile-user';

@Injectable()
export class UserService {

  constructor(private httpClient: HttpClient) { }

  private getToken() {
    return  localStorage.getItem('token');
  }

  wannaBeFreelancer(idUsuario) : Observable<any> {
    const urlApi = '/api/freelancer/afiliar/' + idUsuario;
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.getToken() );
    // headers = headers.set('Content-Type', 'application/json');
    return this.httpClient.get(urlApi, {headers});
  }

  // noinspection JSAnnotator
  getUserData(email: string): Observable<any> {
    const body = `username=${encodeURIComponent(email)}`;
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');
    headers = headers.set('Authorization', 'Bearer ' + this.getToken());
    return this.httpClient.post('/api/usuario/obtenerPorUsername', body, {headers});
  }


  getIdUser(){
    const dataUser = JSON.parse(localStorage.getItem('user'));
    return dataUser.id;
  }

  getAllDataUser(){
    return JSON.parse(localStorage.getItem('user'));
  }
  /*
    *****************************************************************
                          Freelancer
    *****************************************************************
  */
  updateProfileUser(profileUsers: any): Observable<any>{
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    headers = headers.set('Authorization', 'Bearer ' + this.getToken());
    const urlApi = '/api/usuario/perfil/' + profileUsers.id;
    return this.httpClient.put(urlApi, profileUsers,{headers});
  }

  uploadProfileImage(file: File, idUsuario): Observable<HttpEvent<{}>> {
    let formdata: FormData = new FormData();
 
    formdata.append('file', file);
    formdata.append('idUsuario', idUsuario);
 
    const req = new HttpRequest('POST', '/api/imagen/usuario', formdata, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.httpClient.request(req);
  }

  /*
    -------------------------------------------------------------------
                          Institucion
    -------------------------------------------------------------------
  */

  updateProfileInstitucion(intitucion: any): Observable<any>{
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    headers = headers.set('Authorization', 'Bearer ' + this.getToken());
    const urlApi = '/api/institucion/' + intitucion.id;
    return this.httpClient.put(urlApi, intitucion,{headers});
  }

  uploadProfileImageInstitucion(file: File, idInstitucion): Observable<HttpEvent<{}>> {
    let formdata: FormData = new FormData();
 
    formdata.append('file', file);
    formdata.append('idInstitucion', idInstitucion);
 
    const req = new HttpRequest('POST', '/api/imagen/institucion', formdata, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.httpClient.request(req);
  }

  getAllDataIntitucion(): Observable<any>{
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    headers = headers.set('Authorization', 'Bearer ' + this.getToken());
    const urlApi = '/api/institucion/' + this.getIdUser();
    return this.httpClient.get(urlApi, {headers});
  }

  Registrar(registro: Registro): Observable<any> {
    const body = registro;
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    return this.httpClient.post('/api/usuario/signup', body, {headers});
  }

  updatePassword(userPass: any): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.getToken());
    const body = new HttpParams()
            .set('idUsuario', userPass.idUsuario)
            .set('oldPassword', userPass.oldPassword)
            .set('newPassword', userPass.newPassword);
    return this.httpClient.post('/api/usuario/perfil/cambiarPassword', body, {headers});
  }


  resetPassword(userPass: any): Observable<any> {
    const urlApi = '/api/recuperacion-password/password-recovery-token';
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');
    const body = new HttpParams()
            .set('idUser', userPass.idUser)
            .set('password', userPass.password)
            .set('token', userPass.token);

    return this.httpClient.post(urlApi, body, {headers});
  }

}
