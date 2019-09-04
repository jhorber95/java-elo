import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Categoria } from '../../models/categoria';
import { CategoriaService }  from '../../services/categoria.service';
import { MunicipioService }  from '../../services/municipio.service';
import { TipoofertaService } from '../../services/tipooferta.service';
import { TipoofreceService } from '../../services/tipoofrece.service';

import { FiltrosService } from '../../services/ofertas/filtros/filtros.service';
import { Filtros } from '../../models/filtros';
import { Router } from '@angular/router';

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

import { AuthService } from '../../shared/guard/auth.service';
import { DepartamentoService } from '../../services/departamento.service';




@Component({
  selector: 'app-header-home',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  providers: [CategoriaService,
    MunicipioService,
    TipoofertaService,
    TipoofreceService,
    FiltrosService,
    DepartamentoService
  ]
})
export class HeaderComponent implements AfterViewInit, OnInit {
  closeResult: string;

    public categorias: Categoria[];
    public departamentos: any;
    public municipios;
    public tipoOfertas;
    public tipoOfrece;
    private modal: any;

    public selectDepartamento: boolean;
    name: string;
    showHide: boolean;

    public isLogged: boolean;
    public dataUser: any;
    private roles: any [];
    public profileUrlImage: string;


    public idFiltros: Filtros;

    constructor(public _router: Router,
                private categoriaService: CategoriaService,
                private _departamentoService: DepartamentoService,
                private _municipioService: MunicipioService,
                private _tipoOfertaServices: TipoofertaService,
                private _tipoOfreceService: TipoofreceService,
                private _filtrosService: FiltrosService,
                private modalService: NgbModal,
                private authService: AuthService) {
      this.showHide = false;
      this.idFiltros = new Filtros( 'all', 'all', 'all', 'all', 'all', 'all', 0, 10000000, '');

      this.isLogged = false;
      this.dataUser = this.authService.getAllDataUser();
      this.selectDepartamento = false;


    }

   ngOnInit() {
    this.getDepartamentos();
   //  this.getMunicipios();
    this.getCategorias();
    this.getTipoOferta();
    this.getTipoOfrece();
    this.checkSesion();
  }

  checkSesion() {

    if (localStorage.getItem('token') && this.authService.isAuthenticated()) {
      this.isLogged = true;
      this.roles = this.dataUser.roles;
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

  salir() {
    this.authService.Logout();
    location.reload();
  }

  open(content) {
    // console.log(content);
    this.modal = this.modalService.open(content, { windowClass: 'animated pulse' });
  }

  testVocacional() {
    this.modal.close();
    this._router.navigate(['/guiavocacional']);
  }

  getCategorias() {
    this.categoriaService.getCategorias()
      .subscribe(
        response => {
           this.categorias = response;
           // console.log(response);
         },
         error =>  {
          console.log(<any>error);
        }
      );
  }

  getMunicipios() {
    this._municipioService.getMunicipio().subscribe(
      result => {
        this.municipios = result;
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  getTipoOferta() {
    this._tipoOfertaServices.getTipoOferta().subscribe(
      result => {
        this.tipoOfertas = result;
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  getTipoOfrece() {
    this._tipoOfreceService.getTipoOfrece().subscribe(
      result => {
        this.tipoOfrece = result;
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  clickFiltrar() {
    this._router.navigate([
        '/filtrar',
        this.idFiltros.idDepartamento,
        this.idFiltros.idMunicipio,
        this.idFiltros.idCateroria,
        this.idFiltros.idInteligencia,
        this.idFiltros.idOferta,
        this.idFiltros.idOfrece,
        this.idFiltros.idPrecioMin,
        this.idFiltros.idPrecioMax,
        this.idFiltros.nombreCarrera,
      ]);
  }

  changeShowStatus() {
    this.showHide = !this.showHide;
  }

    ngAfterViewInit() {
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



}
