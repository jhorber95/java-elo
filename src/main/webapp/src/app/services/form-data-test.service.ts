import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
// modelo test
import {Fases, FormDataTest} from '../models/test/form-data-test';
import {Observable} from 'rxjs/Observable';
import {ResultData} from '../models/test/result-data';

@Injectable()
export class FormDataTestService {

  private formDataTest: FormDataTest = new FormDataTest();
  private resultTest: ResultData = new ResultData();

  constructor(private httpClient: HttpClient) { }

  getFormDataTest(): Observable<any> {
    return this.httpClient.get('/api/test/vocacional');
  }

  setFormDataTest(formDataTestP: FormDataTest) {
    this.formDataTest = formDataTestP;
  }

  getFormDataTestSubmit(): FormDataTest {
    return this.formDataTest;
  }

  sendTest(body: FormDataTest): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    return this.httpClient.post('/api/test/vocacional', body, {headers});
  }
  setTestResult(resultTest: ResultData) {
    this.resultTest = resultTest;
  }
  getTestResult(): ResultData {
    return this.resultTest;
  }

  getOfertasTest(body: ResultData): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('content-type', 'application/json');
    return this.httpClient.post('/api/test/vocacional/ofertas', body, {headers});
  }

  listarAds(): Observable<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json');
    return this.httpClient.get('/api/publicidad', {headers});
  }

}
