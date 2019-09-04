import * as $ from 'jquery';
import {Component, OnInit, AfterViewInit, OnDestroy }   from '@angular/core';
import {ActivatedRoute, Router, Params, NavigationEnd } from '@angular/router';

// wowngx
import { NgwWowService } from 'ngx-wow';
import 'rxjs/add/operator/filter';

import { Search }     from '../../../models/search';
import { Categoria }  from '../../../models/categoria';
import { Filtros }    from '../../../models/filtros';

import { Subscription }       from 'rxjs/Subscription';
import { UserService }        from '../../../services/user/user.service';
import { AuthService }        from '../../../shared/guard/auth.service';
import { TipoofertaService }  from '../../../services/tipooferta.service';
import { CategoriaService }   from '../../../services/categoria.service';
import { MunicipioService }   from '../../../services/municipio.service';
import { TipoofreceService }   from '../../../services/tipoofrece.service';
import { FormDataTestService } from '../../../services/form-data-test.service';
import { OfertasService }      from '../../../services/ofertas/ofertas.service';


import * as decode from 'jwt-decode';



@Component({
  selector: 'app-guiavocacional',
  templateUrl: './guiavocacional.component.html',
  styleUrls: ['./guiavocacional.component.css'],
  providers: [
    OfertasService,
    CategoriaService,
    MunicipioService,
    TipoofertaService,
    TipoofreceService,
    FormDataTestService
  ]
})
export class GuiavocacionalComponent implements OnInit, AfterViewInit, OnDestroy {

  private wowSubscription: Subscription;

  public categorias: Categoria[];
  public municipios;
  public tipoOfertas;
  public tipoOfrece;

  public search_data: any;
  public resultTest: any;
  public instituciones;
  public financieras;
  public inputbuscador: Search;
  public idFiltros: Filtros;


  public Ad1: any = {
    id: 0,
    imagen: '',
    url: ''
  };


  constructor(private router: Router,
              private _ofertasService: OfertasService,
              private _route: ActivatedRoute,
              private _router: Router,
              private categoriaService: CategoriaService,
              private _municipioService: MunicipioService,
              private _tipoOfertaServices: TipoofertaService,
              private _tipoOfreceService: TipoofreceService,
              private formDataTestService: FormDataTestService,
              private wowService: NgwWowService,
              private userService: UserService,
              private authService: AuthService) {
    // wowngx
    this.router.events.filter(event => event instanceof NavigationEnd).subscribe(event => {
      // Reload WoW animations when done navigating to page,
      // but you are free to call it whenever/wherever you like
      this.wowService.init();
    });
    // wowngx
    this.inputbuscador = new Search('');
    this.idFiltros = new Filtros('all', 'all', 'all', 'all', 'all', 'all', 0, 10000000, '');
   }

  ngOnInit() {
    if (localStorage.getItem('token') && this.authService.isAuthenticated()) {
      const token = localStorage.getItem('token');
      const tokenPayload: any = decode(token);
      const email = tokenPayload.user_name;
      this.userService.getUserData(email).subscribe(
        response => {
          if (response) {
            localStorage.setItem('user', JSON.stringify(response));
          } else {
            this.router.navigate(['/login']);
          }
        },
        error => {
          console.log(<any>error);
        }
      );
    }

    // you can subscribe to WOW observable to react when an element is revealed
    this.wowSubscription = this.wowService.itemRevealed$.subscribe(
      (item: HTMLElement) => {
        // do whatever you want with revealed element
      });

    this.listarAds();
    this.getOfertasTest();
    this.getInstituciones();
    this.getFinancieras();
    this.getResultTest();
    // this.getMunicipios();
    // this.getCategorias();
    // this.getTipoOferta();
    // this.getTipoOfrece();
    sessionStorage.removeItem('redirect-test');
  }

  listarAds() {
    this.formDataTestService.listarAds().subscribe(
      response => {
        if (response.exito === true) {
          for (let i = 0; i < response.data.length; i++) {
            switch (response.data[i].id) {
              case 1: {
                this.Ad1.id = response.data[i].id;
                this.Ad1.imagen = '/api/publicidad/banner/' + response.data[i].imagen;
                this.Ad1.url = response.data[i].url;
                break;
              }
            }
          }

        }
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  ngOnDestroy() {
    // unsubscribe (if necessary) to WOW observable to prevent memory leaks
    this.wowSubscription.unsubscribe();
    // this.categorias;
  }

  public datosFiltroPartial(event) {
    this.search_data = '';
    this.search_data = event;
    // console.log('Capturando ofertas de  PartialComponent');
    // console.log(event);
  }

  // filtros
  getMunicipios() {
    this._municipioService.getMunicipio().subscribe(
      result => {
        this.municipios = result;
      },
      error => {
        const errorMessage = <any>error;
        console.log(errorMessage);
      }
    );
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

  getTipoOferta() {
    this._tipoOfertaServices.getTipoOferta().subscribe(
      result => {
        this.tipoOfertas = result;
      },
      error => {
        const errorMessage = <any>error;
        console.log(errorMessage);
      }
    );
  }

  getTipoOfrece() {
    this._tipoOfreceService.getTipoOfrece().subscribe(
      result => {
        this.tipoOfrece = result;
      },
      error => {
        // let errorMessage = <any>error;
        console.log(<any>error);
      }
    );
  }

  clickFiltrar() {
    this._router.navigate(['/filtrar', this.idFiltros.idMunicipio,
      this.idFiltros.idCateroria, this.idFiltros.idOferta, this.idFiltros.idOfrece, this.idFiltros.idPrecioMin
      , this.idFiltros.idPrecioMax]);
  }
  // fin filtros

  getOfertasTest() {
      if (sessionStorage.getItem('testVocacional')) {
        this.search_data = JSON.parse(sessionStorage.getItem('testVocacional'));
      } else {
        this._router.navigate(['/guiavocacional']);
      }
  }

  getResultTest() {
    if (sessionStorage.getItem('resultTest')) {
      this.resultTest = JSON.parse(sessionStorage.getItem('resultTest'));
    } else {
      this._router.navigate(['/guiavocacional']);
    }
  }

  ngAfterViewInit() {
    window.scrollTo(0, 0);
        $(function() {
            $('.preloader').fadeOut();
        });
  }

  clickBuscador() {
    this._router.navigate(['/ofertas/busqueda', this.inputbuscador.search]);
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
        // let errorMessage = <any>error;
        console.log(<any>error);
      }
     );
  }

}
