import { Injectable } from '@angular/core';
import { HttpClient,
         HttpHeaders,
         HttpParams,
         HttpEvent,
         HttpRequest }      from '@angular/common/http';
import { AuthService }      from '../../shared/guard/auth.service';
import { OfertaFreelancer } from '../../models/freelancer/OfertaFreelancer.interface';
import { Observable }       from 'rxjs/Observable';

@Injectable()
export class OfertasService {

	// private headers = new HttpHeaders();

  constructor(private httpClient: HttpClient, private authService: AuthService ) { }

  /*
    ************************************************************
                           FREELANCER
    ************************************************************
      - Lista de todas las oferta del freelancer
      - Obtener la  lista de estudiantes pendientes por aceptar o rechazar a
        determinada oferta
      - Confirmar incripcion a las ofertas
      - Rechazar inscripcion a ofertas
      - Eliminar oferta
  */
  getAllOfertas(idUsuario: number): Observable<any> {
    const urLpi = '/api/oferta/ofrecidas/freelancer/' + idUsuario;
    const headers = this.authService.headerAuthorization();
    return this.httpClient.get(urLpi, {headers});
  }

  getIncripciones(idUsuario): Observable<any> {
    const urlApi = '/api/inscripcion/ofertaFreelancer/preinscritos/freelancer';
    let headers =  new HttpHeaders();
    const body = new HttpParams().set('idUsuario', idUsuario);
    headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
    // headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');
    return this.httpClient.post(urlApi, body, {headers});
  }


  confirmarInscripcion(idOferta, idEstudiante ): Observable<any> {
    const urlApi = '/api/inscripcion/ofertaFreelancer/confirmar/';
    let headers =  new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');

    const body = new HttpParams()
            .set('idOferta', idOferta)
            .set('idUsuario', idEstudiante);
    return this.httpClient.post(urlApi, body, {headers});
  }


  rechazarIncripcion(idOferta, idEstudiante ): Observable<any> {
    const urlApi = '/api/inscripcion/ofertaFreelancer/rechazar';
    let headers =  new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');

    const body = new HttpParams()
            .set('idOferta', idOferta)
            .set('idUsuario', idEstudiante);
    return this.httpClient.post(urlApi, body, {headers});
  }

  deleteOfertaFreelancer(idOferta: any, idUsuario: any): Observable<any> {
    const urlApi = '/api/oferta/freelancer/eliminar';
    const headers = this.authService.headerAuthorization();

    const body = new HttpParams()
            .set('idOferta', idOferta)
            .set('idUsuario', idUsuario);

    return this.httpClient.post(urlApi, body, {headers});
  }

  /* ==============================================================
                             INSTITUCIÓN
    ==============================================================
     - Lista de todas la oferta de  la institucion
     - lista de de estudiantes pendientes por confirmar una oferta
     - rechazar incripcion a estudiantes
     - confirmar inscripcion de ofertas a estudiantes
     - eliminar oferta
  */

  getAllOfertasIntitucion(idInstitucion: number): Observable<any> {
    const urLpi = '/api/oferta/ofrecidas/institucion/' + idInstitucion;
    const headers = this.authService.headerAuthorization();
    return this.httpClient.get(urLpi, {headers});
  }

  getIncripcionesInstitucion(idInstitucion): Observable<any> {
    const urlApi = '/api/inscripcion/ofertaInstitucion/preinscritos/institucion';
    let headers =  new HttpHeaders();
    const body = new HttpParams().set('idInstitucion', idInstitucion);
    headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
    // headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');
    return this.httpClient.post(urlApi, body, {headers});
  }

  rechazarIncripcionInstitucion(idOferta, idEstudiante ): Observable<any> {
    const urlApi = '/api/inscripcion/ofertaInstitucion/rechazar';
    let headers =  new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');

    const body = new HttpParams()
            .set('idOferta', idOferta)
            .set('idUsuario', idEstudiante);
    return this.httpClient.post(urlApi, body, {headers});
  }

  deleteOfertaInstitucion(idOferta, idInstitucion): Observable<any> {
    const urlApi = '/api/oferta/institucion/eliminar';
    const headers = this.authService.headerAuthorization();

     const body = new HttpParams()
            .set('idOferta', idOferta)
            .set('idInstitucion', idInstitucion);

    return this.httpClient.post(urlApi, body, {headers});
  }

  confirmarInscripcionInstitucion(idOferta, idEstudiante ): Observable<any> {
    const urlApi = '/api/inscripcion/ofertaInstitucion/confirmar';
    let headers =  new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');

    const body = new HttpParams()
            .set('idOferta', idOferta)
            .set('idUsuario', idEstudiante);
    return this.httpClient.post(urlApi, body, {headers});
  }
  /*
    ############################# FREELANCER  #############################
    ############################# INSTITUCION #############################

     - crear oferta
     - subir foto
     - obtener una oferta por id
     - actualizar una oferta
     - lista de todas las ofertas

  */
  getListAllOfertas(): Observable<any> {
    const urlApi = '/api/oferta/all';
    return this.httpClient.get(urlApi);
  }

  getOfertaByItemSearch(search): Observable<any> {
    const urlApi = '/api/oferta/buscador?start=0&length=9999999&search=' + search;
    return this.httpClient.get(urlApi);
  }

  createOferta(oferta ): Observable<any> {
    const urlApi = '/api/oferta';
    let headers =  new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
    headers = headers.set('Content-Type', 'application/json');
    return this.httpClient.post(urlApi, oferta, {headers});
  }

  uploadImage(file: File, idOferta): Observable<HttpEvent<{}>> {
    const formdata: FormData = new FormData();

    formdata.append('file', file);
    formdata.append('idOferta', idOferta);

    const req = new HttpRequest('POST', '/api/imagen/oferta', formdata, {
      reportProgress: true,
      responseType: 'text'
    });
    return this.httpClient.request(req);
  }

  getDetailOferta(idOferta: number): Observable<any> {
    // const urlApi = '/api/inscripcion/ofertaFreelancer/' + idOferta;
    const urlApi = '/api/oferta/' + idOferta;
    let headers =  new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
    return this.httpClient.get(urlApi, {headers});
  }

  updateOferta(oferta): Observable<any> {
    const urlApi = '/api/oferta/admin/' + oferta.id;
    let headers =  new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + this.authService.getToken());
    return this.httpClient.put(urlApi, oferta, {headers});
  }

}
