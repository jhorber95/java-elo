import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { Router } from '@angular/router';

import { OfertasService }       from '../../../services/ofertas/ofertas.service';
import { CategoriaService }     from '../../../services/categoria.service';
import { DepartamentoService }  from '../../../services/departamento.service';
import { MunicipioService }     from '../../../services/municipio.service';
import { TipoofertaService }    from '../../../services/tipooferta.service';
import { TipoofreceService }    from '../../../services/tipoofrece.service';
import { FiltrosService }       from '../../../services/ofertas/filtros/filtros.service';


import { Categoria } from '../../../models/categoria';
import { Filtros }   from '../../../models/filtros';

@Component({
  selector: 'app-partial-formulario-filtros',
  templateUrl: './partial.component.html',
  styles: [],
  providers: [OfertasService,
    CategoriaService,
    DepartamentoService,
    MunicipioService,
    TipoofertaService,
    TipoofreceService,
    FiltrosService
  ]
})
export class PartialComponent implements OnInit {

  @Output() OfertasFiltradas: EventEmitter<any> = new EventEmitter();
  // @Input() public stringSearch: any;

  public categorias: Categoria[];
  public idFiltros: Filtros;
  public idDepartamento: number;
  public departamentos: any;
  public selectDepartamento: boolean;
  public municipios;
  public tipoOfertas;
  public tipoOfrece;
  public partialFiltros: any;
 // servidigitalneiva@gmail.com
  constructor(private _ofertasService: OfertasService,
    private categoriaService: CategoriaService,
    private _departamentoService: DepartamentoService,
    private _municipioService: MunicipioService,
    private _tipoOfertaServices: TipoofertaService,
    private _tipoOfreceService: TipoofreceService,
    private _filtrosService: FiltrosService,
    private _router: Router) {

      this.idFiltros = new Filtros('all', 'all', 'all', 'all', 'all', 'all', 0, 10000000, '');
      this.selectDepartamento = false;

     }

  ngOnInit() {
   // this.getDatosFiltros();
    this.getDepartamentos();
    this.getCategorias();
    this.getTipoOferta();
    // this.getTipoOfrece();
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
    this.idDepartamento = event;
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
        console.log( <any>error);
      }
    );
  }

  getDatosFiltros() {
    // this.partialFiltros = '';
    console.log(this.idFiltros);
    this._filtrosService.getFiltros(
        this.idFiltros.idCateroria,
        this.idFiltros.idInteligencia,
        this.idFiltros.idDepartamento,
        this.idFiltros.idMunicipio,
        this.idFiltros.idOfrece,
        this.idFiltros.idOferta,
        this.idFiltros.idPrecioMin,
        this.idFiltros.idPrecioMax,
        this.idFiltros.nombreCarrera
      ).subscribe(
        response => {
          if (response) {
            this.partialFiltros = response.data;
            //  console.log('ofertas filtradas desdes partials');
            // console.log(this.partialFiltros);
          }
        },
          error => {
            console.log(<any>error);
          }
        );
  }

  clickFiltrarPartial() {
    this.getDatosFiltros();
    // Esperamos 1 seg
    setTimeout(() => {
      console.log('ofertas filtradas desdes partials');
      console.log(this.partialFiltros);
      this.OfertasFiltradas.emit(this.partialFiltros);
    }, 3000);
  }
}
