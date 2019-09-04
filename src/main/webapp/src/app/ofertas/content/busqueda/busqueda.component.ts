import * as $ from 'jquery';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { OfertasService } from '../../../services/ofertas/ofertas.service';

import { Search } from '../../../models/search';
import {Filtros} from '../../../models/filtros';
import {MunicipioService} from '../../../services/municipio.service';
import {FiltrosService} from '../../../services/ofertas/filtros/filtros.service';
import {TipoofertaService} from '../../../services/tipooferta.service';
import {TipoofreceService} from '../../../services/tipoofrece.service';
import {CategoriaService} from '../../../services/categoria.service';
import {Categoria} from '../../../models/categoria';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { AuthService } from '../../../shared/guard/auth.service';

@Component({
  selector: 'app-busqueda',
  templateUrl: './busqueda.component.html',
  styleUrls: ['./busqueda.component.css'],
  providers: [OfertasService]
})
export class BusquedaComponent implements OnInit, AfterViewInit  {

  public u_search;
  public search_data;
  public instituciones;
  public financieras;
  public idFiltros: Filtros;
  public modalfiltros: any;

  public inputbuscador: Search;

  public total: number;
  public loadingOfertas: boolean;

  public isLoged: boolean;


  constructor(private _ofertasService: OfertasService,
              private _route: ActivatedRoute,
              private authService: AuthService,
            private _router: Router,
              private modalService: NgbModal) {

    this.inputbuscador = new Search('');
    this.loadingOfertas = true;

    this.isLoged = false;
    if (localStorage.getItem('token') && this.authService.isAuthenticated()) {
        this.isLoged = true;
    }
   }

  ngOnInit() {
    this._route.params.forEach((params: Params) => {
      this.u_search = params['search'] ? params['search'] : '';
    });
    this.Search();
    this.getInstituciones();
    this.getFinancieras();
  }

  Search() {

      this._ofertasService.search(0, 10, this.u_search).subscribe(
      response => {
          this.search_data = response.data;
      },
      error => {
        console.log(<any>error);
      }
      );
  }

  openModalFiltros(content) {
    this.modalfiltros = this.modalService.open(content, { windowClass: 'animated pulse' });
  }

  ngAfterViewInit() {
    window.scrollTo(0, 0);

    this.hidePreloader();
  }

  hidePreloader() {
    $(function() {
      $('.preloader').fadeOut();
    });
  }

  loadOfertas() {
    this.hidePreloader();

    const len =  this.search_data.length;
    const search = this.u_search ? this.u_search : '';
    // console.log(len);
    this.loadingOfertas = !this.loadingOfertas;

    this._ofertasService.search(len + 1, 10, search ).subscribe(
      response => {
        const oferta = response.data;
        this.loadingOfertas = !this.loadingOfertas;

        // console.log(response);
        for (let i = 0; i < oferta.length; i++) {
          this.search_data.push(oferta[i]);
        }
      },
      error => {
        console.log(<any>error);
      }
    );

  }

  clickBuscador() {
    this._router.navigate(['/ofertas/busqueda', this.inputbuscador.search]);
    this.search_data = '';
    this.u_search = this.inputbuscador.search;
    this.Search();
  }
  public datosFiltroPartial(event) {
    this.search_data = '';
    this.search_data = event;
     console.log('Capturando ofertas de  PartialComponent');
    console.log(event);
  }

  getInstituciones() {
    this._ofertasService.getInsticiones().subscribe(
      response => {
          this.instituciones = response.data;
      },
      error => {
        console.log(<any>error);
      }
     );
  }

  getFinancieras() {
    this._ofertasService.getFinancieras().subscribe(
      response => {
          this.financieras = response.data;
      },
      error => {
        console.log(<any>error);
      }
     );
  }

}
