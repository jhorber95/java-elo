import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import { GLOBAL } from '../../global';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class FiltrosService {

  private urlFiltros: string;

  constructor( private _http: Http,  private httpClient: HttpClient) {

  }

  getFiltros(idcat, idInteligencia, idDepartamento, idmun, idofrece, idoferta,  idpreciomin, idpreciomax, stringCarrera): Observable<any> {
    const url = '/api/oferta/filtros?' +
                    'start=0' +
                    '&length=30' +
                    '&categoria=' + idcat.replace('all', '') +
                    '&inteligencia=' + idInteligencia.replace('all', '') +
                    '&departamento=' + idDepartamento.replace('all', '') +
                    '&municipio=' + idmun.replace('all', '') +
                    '&tipoOfrece=' + idofrece.replace('all', '') +
                    '&tipoOferta=' + idoferta.replace('all', '') +
                    '&precioMinimo=' + idpreciomin +
                    '&precioMaximo=' + idpreciomax +
                    '&stringCarrera=' + stringCarrera ;

    //  console.log('filtrosService: ' );
    //  console.log(url);
    return this.httpClient.get(url);
  }

}
