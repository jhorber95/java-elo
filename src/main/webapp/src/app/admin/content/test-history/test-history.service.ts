import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ITestHistory} from '../../../models/test-history';
import {AuthService} from '../../../shared/guard/auth.service';

@Injectable()
export class TestHistoryService {

  private url: string = '/api/test-history';

  constructor(private http: HttpClient,  private authServices: AuthService) { }

  create(entity: ITestHistory): Observable<ITestHistory> {
    return this.http.post(this.url, entity);
  }

  update(entity: ITestHistory): Observable<ITestHistory> {
    let headers = new HttpHeaders();
    headers = headers.set('Authorization','Bearer ' + this.authServices.getToken());
    return this.http.put(this.url, entity, { headers });
  }

  findOne(id: number): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Authorization','Bearer ' + this.authServices.getToken());
    return this.http.get(`${this.url}/${id}`, { headers });
  }

  getAll() {
    let headers = new HttpHeaders();
    headers = headers.set('Authorization','Bearer ' + this.authServices.getToken());
    return this.http.get(`${this.url}`, { headers });
  }

  delete(id: number): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Authorization','Bearer ' + this.authServices.getToken());
    return this.http.delete(`${this.url}/${id}`, { headers });
  }

  sendUserTest(entity: any): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Authorization','Bearer ' + this.authServices.getToken());
    return this.http.post('/api/test/vocacional/save-user-test', entity,{ headers });
  }
}
