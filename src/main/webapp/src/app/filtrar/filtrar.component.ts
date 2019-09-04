import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Categoria } from '../models/categoria';
import { Filtros } from '../models/filtros';
import { CategoriaService } from '../services/categoria.service';
import { MunicipioService } from '../services/municipio.service';
import { TipoofertaService } from '../services/tipooferta.service';
import { TipoofreceService } from '../services/tipoofrece.service';
import { Router, Params, ActivatedRoute } from '@angular/router';

import { OfertasService } from '../services/ofertas/ofertas.service';
import { FiltrosService } from '../services/ofertas/filtros/filtros.service';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {Search} from '../models/search';
import {AuthService} from '../shared/guard/auth.service';
import { DepartamentoService } from '../services/departamento.service';


@Component({
  selector: 'app-filtrar',
  templateUrl: './filtrar.component.html',
  styleUrls: ['./filtrar.component.css'],
  providers: [CategoriaService,
    DepartamentoService,
    MunicipioService,
    TipoofertaService,
    TipoofreceService,
    OfertasService,
    FiltrosService
  ]
})
export class FiltrarComponent implements OnInit {
  closeResult: string;

  public categorias: Categoria[];
  public departamentos: any;
  public municipios;
  public tipoOfertas;
  public tipoOfrece;
  private modal: any;
  private modalfiltros: any;
  name: string;
  showHide: boolean;

  public selectDepartamento: boolean;

  public u_search;
  public search_data;
  public instituciones;
  public financieras;

  public idFiltros: Filtros;
  public inputbuscador: Search;

  public Filtros;

  public isLogged: boolean;
  public dataUser: any;
  private roles: any [];
  public profileUrlImage: string;

    constructor(public _router: Router,
                private categoriaService: CategoriaService,
                private _municipioService: MunicipioService,
  					    private _departamentoService: DepartamentoService,
                private _tipoOfertaServices: TipoofertaService,
                private _tipoOfreceService: TipoofreceService,
                private _route: ActivatedRoute,
                private _ofertasService: OfertasService,
                private _filtrosService: FiltrosService,
                private modalService: NgbModal,
                private modalServiceFiltros: NgbModal,
                private authService: AuthService) {

      this.showHide = false;
      this.idFiltros = new Filtros('all', 'all', 'all', 'all', 'all', 'all', 0, 10000000, '');
      this.inputbuscador = new Search('');

      this.isLogged = false;
      this.dataUser = this.authService.getAllDataUser();
      this.selectDepartamento = false;
    }

   ngOnInit() {
     window.scrollTo(0, 0);
    this._route.params.forEach((params: Params) => {
          this.idFiltros = new Filtros(
              params['iddep'],
              params['idmun'],
              params['idcat'],
              params['idInteligencia'],
              params['idoferta'],
              params['idofrece'],
              params['preciomin'],
              params['preciomax'],
              params['stringCarrera']
        );
    });
    this.getDepartamentos();
    this.getInstituciones();
    this.getFinancieras();
    this.getDatosFiltros();
    this.checkSesion();
  }

  getDepartamentos() {
  	this._departamentoService.getDepartamentos()
  		.subscribe(
  			response => {
  				this.departamentos = response;
  				// console.log(this.departamentos);
  			},
  			error => {
  				console.log(<any>error);
  			}
  		);
  }

  onSelectDepartamento(event) {
    console.log(event);
    this.municipios = null;
  	this._municipioService.getMunicipioByIdDepartamento(event)
  				.subscribe(
  					response => {
              this.municipios = response;
              this.selectDepartamento = true;
		  				// console.log(this.municipios);
		  			},
		  			error => {
		  				console.log(<any>error);
		  			}
  				);
  }


  checkSesion() {

    if (localStorage.getItem('token') && this.authService.isAuthenticated()) {
      this.isLogged = true;
      this.roles    = this.dataUser.roles;
      this.profileUrlImage = this.checkRolUser();
    }else {
      this.isLogged = false;
    }
  }

  checkRolUser() {
    for (let i = 0; i <= this.roles.length ; i++) {
      if (this.roles[i]['id'] == 5) {
        return 'institucion';
      }else {
        return 'usuario';
      }
    }
  }

  open(content) {
    this.modal = this.modalService.open(content, { windowClass: 'animated jello' });
  }
  openModalFiltros(content) {
    this.modalfiltros = this.modalServiceFiltros.open(content, { windowClass: 'animated pulse' });
  }

  testVocacional() {
    this.modal.close();
    this._router.navigate(['/guiavocacional']);
  }

   getDatosFiltros() {
    //  console.log(this.idFiltros);
    this._filtrosService.getFiltros(
        this.idFiltros.idCateroria,
        this.idFiltros.idInteligencia,
        this.idFiltros.idDepartamento,
        this.idFiltros.idMunicipio,
        this.idFiltros.idOfrece,
        this.idFiltros.idOferta,
        this.idFiltros.idPrecioMin,
        this.idFiltros.idPrecioMax,
        this.idFiltros.nombreCarrera,
      ).subscribe(
          result => {
            this.Filtros = result.data;
          },
          error => {
            console.log(<any>error);
          }
        );
  }

  getInstituciones() {
    this._ofertasService.getInsticiones().subscribe(
      response => {
          this.instituciones = response.data;
      },
      error => {
        // var errorMessage = <any>error;
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

  clickFiltrar() {
   /* this._router.navigate([
      '/filtrar',
      this.idFiltros.idMunicipio,
      this.idFiltros.idCateroria,
      this.idFiltros.idOferta,
      this.idFiltros.idOfrece,
      this.idFiltros.idPrecioMin,
       this.idFiltros.idPrecioMax
      ]);*/
    this.Filtros = '';
    this.getDatosFiltros();
  }

  public datosFiltroPartial(event) {
    this.Filtros = '';
    this.Filtros = event;
    console.log('Capturando ofertas de  \'PartialComponent\'');
    console.log(event);
  }

    changeShowStatus() {
      this.showHide = !this.showHide;
    }

    // tslint:disable-next-line:use-life-cycle-interface
    ngAfterViewInit() {
      window.scrollTo(0, 0);

        $(function () {
            $('.preloader').fadeOut();
        });

      // Header scroll class
      $(window).scroll(function() {
        if ($(this).scrollTop() > 100) {
          $('#header').addClass('header-scrolled');
        } else {
          $('#header').removeClass('header-scrolled');
        }
      });

        const set = function () {
            const width = (window.innerWidth > 0) ? window.innerWidth : this.screen.width;
            const topOffset = 0;
            if (width < 1170) {
                $('body').addClass('mini-sidebar');
                $('.navbar-brand span').hide();
                $('.sidebartoggler i').addClass('ti-menu');
            } else {
                $('body').removeClass('mini-sidebar');
                $('.navbar-brand span').show();
            }

            let height = ((window.innerHeight > 0) ? window.innerHeight : this.screen.height) - 1;
            height = height - topOffset;
            // tslint:disable-next-line:curly
            if (height < 1) height = 1;
            if (height > topOffset) {
                $('.page-wrapper').css('min-height', (height) + 'px');
            }

        };
        $(window).ready(set);
        $(window).on('resize', set);

        $(document).on('click', '.mega-dropdown', function (e) {
            e.stopPropagation();
        });

        $('.search-box a, .search-box .app-search .srh-btn').on('click', function () {
            $('.app-search').toggle(200);
        });

        (<any>$('.scroll-sidebar, .right-sidebar, .message-center')).perfectScrollbar();

        $('body').trigger('resize');


    }

  clickBuscador() {
    this._router.navigate(['/ofertas/busqueda', this.inputbuscador.search]);
  }

  salir() {
    this.authService.Logout();
    this._router.navigate(['/']);
  }

}
