import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import { GLOBAL } from '../global';
import { AuthService } from '../../shared/guard/auth.service';





@Injectable()
export class DetalleofertaService {

  private urlDetalleOferta: string;
  private urlInstitucion: string;
  private urlFreelancer: string;

  constructor( private _http: Http, 
               private httpClient: HttpClient,
               private _authService: AuthService
              ) {

    this.urlDetalleOferta = GLOBAL.url + '/api/oferta/';
    this.urlInstitucion = GLOBAL.url + '/api/institucion/';
    this.urlFreelancer = GLOBAL.url + '/api/freelancer/';
  }

  getDetalleOferta(id) {
    return this._http.get(this.urlDetalleOferta + id)
              .map(res => res.json());
  }

  getNombreInstitucion(id) {
    return this._http.get(this.urlInstitucion + id)
                .map(res => res.json());
  }

  getNombreFrelancer(id) {
    return this._http.get(this.urlFreelancer + id)
                .map(res => res.json());
  }

  subscribeToCourse(idTipoOfrece: number, idCourse:number, idUser: number): Observable<any>{
    let urlApi = this.setUpUrlTipoOfrece(idTipoOfrece);
    urlApi = urlApi + idCourse + '/' + idUser;
    const headers = this._authService.headerAuthorization();
    return this.httpClient.get(urlApi, {headers});


  }

  setUpUrlTipoOfrece(idTipoOfreceid: number){
    switch (idTipoOfreceid) {
      case 1:
        return '/api/inscripcion/ofertaInstitucion/';
      
      case 2:
        return '/api/inscripcion/ofertaFreelancer/';
    }

  }

}
