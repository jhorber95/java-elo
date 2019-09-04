import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';


import { OfertasService }       from '../../services/ofertas/ofertas.service';
import { CategoriaService }     from '../../services/categoria.service';
import { DepartamentoService }  from '../../services/departamento.service';
import { MunicipioService }     from '../../services/municipio.service';
import { TipoofertaService }    from '../../services/tipooferta.service';
import { TipoofreceService }    from '../../services/tipoofrece.service';

import { Categoria } from '../../models/categoria';
import { Filtros }   from '../../models/filtros';


@Component({
  selector: 'app-fitro-busqueda',
  templateUrl: './fitro-busqueda.component.html',
  styleUrls: ['./fitro-busqueda.component.css'],
  providers: [OfertasService,
    CategoriaService,
    DepartamentoService,
    MunicipioService,
    TipoofertaService,
    TipoofreceService]
})
export class FitroBusquedaComponent implements OnInit {

  public idFiltros: Filtros;
  public u_search;
  public search_data;

  public modalfiltros: any;

  constructor(
    private _route: ActivatedRoute,
    private _router: Router,
    private modalService: NgbModal) {

      this.idFiltros = new Filtros('all', 'all', 'all', 'all', 'all', 'all', 0, 10000000, '');

     }

  ngOnInit() {

  }
  openModalFiltros(content) {
    this.modalfiltros = this.modalService.open(content, { windowClass: 'animated pulse' });
  }

  clickFiltrar() {
    this.modalfiltros.close();
    this._router.navigate([
      '/filtrar',
      this.idFiltros.idMunicipio,
      this.idFiltros.idCateroria,
      this.idFiltros.idOferta,
      this.idFiltros.idOfrece,
      this.idFiltros.idPrecioMin,
      this.idFiltros.idPrecioMax,
      this.idFiltros.nombreCarrera
    ]);
  }

}
