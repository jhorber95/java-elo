import { Component, NgZone, OnInit, ViewEncapsulation } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AdminService } from '../../services/admin.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/toPromise';

// sweat alert
import swal from 'sweetalert';
import {DepartamentoService} from '../../../services/departamento.service';
import {MunicipioService} from '../../../services/municipio.service';
import {Ofertasdatatable} from '../../../models/ofertasDatatable/ofertasdatatable';
import { Router } from '@angular/router';

class DataTablesResponse {
  data: any[];
  draw: number;
  recordsFiltered: number;
  recordsTotal: number;
}

@Component({
  selector: 'app-admin-oferta',
  templateUrl: './admin-oferta.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./admin-oferta.component.css'],
})
export class AdminOfertaComponent implements OnInit {
  // dtOptions: Promise<DataTables.Settings>;
  OfertasDatatable: Ofertasdatatable[];
  // modal
  private modal: any;
  public ofertaInfo: any;
  public departamentos: any;
  public municipios: any;
  message = '';
  private row: number;

  // private usuariosDatatable: any[] = [];
  dtOptions: DataTables.Settings = {};

  private api_url_ofertas;
  clickSelected = '';

  // api_url = 'https://angular-datatables-demo-server.herokuapp.com/';

  constructor(private adminUsuarioService: AdminService,
              private httpClient: HttpClient,
              private zone: NgZone,
              private router: Router,
              private modalService: NgbModal,
              private _departamentoService: DepartamentoService,
              private _municipioService: MunicipioService) {
    this.api_url_ofertas = '/api/oferta/dataTables';
  }


  someClickHandler(info: any): void {
      this.message = info.id + ' | ' + info.titulo;
     this.ofertaInfo = info;
  }

  openModal(content) {
    this.modal = this.modalService.open(content, { windowClass: 'animated jello' });
  }


  ngOnInit() {
    this.getDataRow();
  }

  checkIsSelectedEvent() {
    if (this.ofertaInfo == null) {
      swal({
        title: 'Error!',
        icon: 'warning',
        text: 'Debe seleccionar un evento de la tabla.',
        timer: 2000,
        buttons: ['Cancelar', 'Aceptar'],
        dangerMode: true,
      });
      return false;
    }else {
      return true;
    }
  }



  getDataRow() {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
      serverSide: true,
      processing: true,
      language: {
        url: '/assets/data/spanish.json'
      },
      ajax: (dataTablesParameters: any, callback) => {
        let headers = new HttpHeaders();
        headers = headers.set('Authorization', 'Bearer ' + localStorage.getItem('token'));

        this.httpClient.post<DataTablesResponse>(this.api_url_ofertas, dataTablesParameters, {headers}).subscribe(
          resp => {
            this.OfertasDatatable = resp.data;
            callback({
              recordsTotal: resp.recordsTotal,
              recordsFiltered: resp.recordsFiltered,
              data: []
            });
          });
      },
      columns: [
        { data: 'id', width: '10%' }, { data: 'titulo', width: '30%' },
        { data: 'precio' }, { data: 'telefono'},
        { data: 'destacada'}, { data: 'nombre' },
        { data: 'ofrece'}
      ],
      rowCallback: (row: Node, data: any[] | Object, index: number) => {
        const self = this;
        $('td', row).unbind('click');
        $('td', row).bind('click', () => {
          self.someClickHandler(data);
        });
        return row;
      }
    };
  }

  ClickRow(rowId, element: any) {
    this.row = rowId;
    if (element === this.clickSelected) {
      this.clickSelected = '0';
    } else {
      this.clickSelected = element;
    }
    this.someClickHandler(this.OfertasDatatable[rowId]);
  }

  editarOferta() {
    if (this.checkIsSelectedEvent()) {
      this.router.navigate(['/admin/admin-editar-oferta', this.ofertaInfo.id]);
    } else {
      swal('Error!', 'Debe seleccionar una oferta de la tabla.', 'error');
    }
  }

  eliminarOferta() {
    if (this.checkIsSelectedEvent()) {
      swal({
        title: 'Está segudo de ELIMINAR la oferta ' + this.ofertaInfo.id + '?',
        text: 'Para ELIMINAR este oferta, click en Confirmar!',
        icon: 'warning',
        buttons: ['Cancelar', 'Confirmar'],
        dangerMode: true,
      })
        .then((willDelete) => {
          if (willDelete) {
            this.Eliminar();
          } else {
            swal('usted no eliminó la oferta');
            this.modal.close();
          }
        });
    } else {
      swal('Error!', 'Debe seleccionar un item de la tabla.', 'error');
    }


  }

  Eliminar() {
    this.ofertaInfo.estado.id = 7;
    this.adminUsuarioService.setDestacada(this.ofertaInfo.id, this.ofertaInfo).subscribe(
      response => {
        if (response.exito === true) {
          swal('Buen trabajo!', 'La oferta se ha elimimado con éxito del sistema', 'success')
            .then((value) => {
              this.OfertasDatatable.splice(this.row, 1);
            });
        } else {
          swal('Algo a salido mal', 'Error al intentar eliminar la oferta', 'error');
        }
        this.modal.close();
      },
      error => {
        swal('Algo a salido mal', 'Error al elminar la oferta', 'error');
        console.log(<any>error);
      }
    );
  }

  destacada(id: number) {
    this.adminUsuarioService.setDestacada(id, this.ofertaInfo).subscribe(
      response => {
        if (response.exito === true) {
          swal('Buen trabajo!', 'Las ofertas destacadas aparecen en la pagina de inicio', 'success');
        }else {
          swal('Algo a salido mal', 'Error al destacar la oferta', 'error')
            .then((value) => {
              location.reload();
            });
        }
      },
      error => {
        swal('Algo a salido mal', 'Error al destacar la oferta', 'error')
          .then((value) => {
            location.reload();
          });
        console.log(<any>error);
      }
    );
  }

  disponible(id: number) {
    this.adminUsuarioService.setDestacada(id, this.ofertaInfo).subscribe(
      response => {
        if (response.exito === true) {
          swal('Buen trabajo!', 'Se cambio el estado de la oferta', 'success');
        }else {
          swal('Algo a salido mal', 'Error al cambiar estado de la oferta', 'error')
            .then((value) => {
              location.reload();
            });
        }
      },
      error => {
        swal('Algo a salido mal', 'Error al cambiar estado de la oferta', 'error')
          .then((value) => {
            location.reload();
          });
        console.log(<any>error);
      }
    );
  }

}

