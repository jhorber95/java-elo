import { Injectable,  } from '@angular/core';
import * as decode from 'jwt-decode';
import { HttpClient,
  HttpHeaders,
  HttpParams,
  HttpEvent,
  HttpRequest }      from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {TOKEN_AUTH_PASSWORD, TOKEN_AUTH_USERNAME} from '../../services/auth/auth.constant';

@Injectable()
export class AuthService {

  private httpHeaders = new HttpHeaders();

  constructor(private httpClient: HttpClient) { }

  public getToken(): string {
    return localStorage.getItem('token');
  }
  public getAllDataUser() {
    return JSON.parse(localStorage.getItem('user'));
  }

  public headerAuthorization() {
    return this.httpHeaders.set('Authorization', 'Bearer ' + this.getToken());
  }

  public getTokenExpirationDate(token: string): Date {
    let decoded: any;
    decoded = decode(token);

    if (!decoded.hasOwnProperty('exp')) {
      return null;
    }

    const date = new Date(0); // The 0 here is the key, which sets the date to the epoch
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  public isTokenExpired(token: string, offsetSeconds?: number): boolean {
    const date = this.getTokenExpirationDate(token);
    offsetSeconds = offsetSeconds || 0;

    if (date == null) {
      return false;
    }

    // Token expired?
    return (date.valueOf() > (new Date().valueOf() + (offsetSeconds * 1000)));
  }

  public isAuthenticated(): boolean {
    // get the token
    const token = this.getToken();
    // return a boolean reflecting
    // whether or not the token is expired
    // retorna true si esta autentticado o no ha expirado el token
    return this.isTokenExpired(token);
  }

  Login(email: string, password: string): Observable<any> {

    const body = `username=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}&grant_type=password`;
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');
    headers = headers.set('Authorization', 'Basic ' + btoa(TOKEN_AUTH_USERNAME + ':' + TOKEN_AUTH_PASSWORD));


  //  return this.httpClient.post('http://localhost:8080/oauth/token', body, {headers});

    return this.httpClient.post('https://estudialo.co/oauth/token', body, {headers});



  }

  resetPassword(email): Observable<any> {
    const urlApi = '/api/recuperacion-password/generate-token';

    let headers =  new HttpHeaders();
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');

    const body = new HttpParams().set('email', email);

    return this.httpClient.post(urlApi, body, {headers});
  }

  checkToken(idUser, token): Observable<any>  {
    const urlApi = '/api/recuperacion-password/check-token';

    let headers =  new HttpHeaders();
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');

    const body = new HttpParams()
          .set('idUser', idUser)
          .set('token', token);

    return this.httpClient.post(urlApi, body, {headers});
  }

  Logout() {
    localStorage.clear();
  }

}
