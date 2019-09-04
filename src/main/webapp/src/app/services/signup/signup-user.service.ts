import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/toPromise';
import { Observable } from 'rxjs/Observable';
import { User } from '../../models/user-singup.interface';
import { GLOBAL } from '../global';

@Injectable()
export class SignupService {

    //private urlApi = GLOBAL.url + '/api/usuario/signup';
    private urlApi = '/api/usuario/signup';
    // private headers = new HttpHeaders({'Content-Type': 'application/json'});

    constructor(private httpClient: HttpClient) { }

    createUser(user: User): Observable<any>{
        let headers = new HttpHeaders();
        headers = headers.set('Content-Type', 'application/json');
        return this.httpClient.post(this.urlApi, user, {headers});

    }

    createFreelancer(user: User): Observable<any>{
        let urlApi = '/api/freelancer/signup';
        let headers = new HttpHeaders();
        headers = headers.set('Content-Type', 'application/json');
        return this.httpClient.post(urlApi, user, {headers});
    }

    private handleError(error: any): Promise<any> {
        console.error('Error: ', error);
        return Promise.reject(error.message || error);
    }
}