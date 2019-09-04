import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import { CalificarCurso } from '../../models/calificar-curso.interface';
import { AuthService } from '../../shared/guard/auth.service';

@Injectable()
export class CursoService {

  // private httpHeaders = new HttpHeaders();

  constructor(
              private httpClient: HttpClient,
              private authService: AuthService) { }

  private getToken() {
    return localStorage.getItem('token');
  }

  public getCursos(): Observable<any> {
  	// const headers = this.httpHeaders.set('Authorization', 'Bearer ' + this.getToken());
    const headers =  this.authService.headerAuthorization();
    // headers = headers.set('Content-Type', 'application/json');
    return this.httpClient.get(this.setUpUrl(), {headers});
  }

  public setUpUrl(): string {
    const urlApi = '/api/estudiante/misInscripciones/';
  	const dataUser = this.authService.getAllDataUser();
  	// const idUser = dataUser.id;
  	// console.log('userId: ' + idUser);
  	return urlApi + dataUser.id;
  }

  public checkCalificacionCursoInstitucion(idCurso, idUsuario: number): Observable<any> {
    const url = '/api/oferta/verificarCalificacionInstitucion/' + idCurso + '/' + idUsuario;
    const headers = this.authService.headerAuthorization();
    return this.httpClient.get(url, {headers});
  }

  public checkCalificacionCursoFreelancer( idCurso, idUsuario: number): Observable<any> {
    const url = '/api/oferta/verificarCalificacion/' + idCurso + '/' + idUsuario;
    const headers = this.authService.headerAuthorization();
    return this.httpClient.get(url, {headers});
  }

  public calificarCurso(calificarCurso: CalificarCurso, tipoOfrece: number): Observable<any> {
    const url = this.chooseUrl(tipoOfrece);
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');
    headers = headers.set('Authorization', 'Bearer ' + this.getToken());
    const body =  new HttpParams()
                    .set('idOferta', String(calificarCurso.idOferta))
                    .set('idUsuario', String(calificarCurso.idUsuario))
                    .set('calificacion', String(calificarCurso.calificacion))
                    .set('comentario', calificarCurso.comentario);
    return this.httpClient.post(url, body, {headers});

  }

  public eliminarIncripcion(idIncripcion, idTipoOfrece): Observable<any> {
    const url = '/api/estudiante/eliminarInscripciones/' + idIncripcion + '/' + idTipoOfrece;
    const headers = this.authService.headerAuthorization();
    return this.httpClient.get(url, {headers});
  }

  private chooseUrl(idTipoOfrece: number) {
    switch (idTipoOfrece) {
      case 1:
        return '/api/oferta/calificarOfertaInstitucion';
      case 2:
        return '/api/oferta/calificarOfertaFreelancer';
    }
  }

  private handleError(error: any): Promise<any> {
    console.error('Error: ', error);
    return Promise.reject(error.message || error);
  }

}
