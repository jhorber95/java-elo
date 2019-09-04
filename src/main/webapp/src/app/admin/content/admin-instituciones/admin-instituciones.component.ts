import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Params, Router }    from '@angular/router';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AdminService } from '../../services/admin.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/toPromise';

import swal from 'sweetalert';
import {DepartamentoService} from '../../../services/departamento.service';
import {MunicipioService} from '../../../services/municipio.service';


class DataTablesResponse {
  data: any[];
  draw: number;
  recordsFiltered: number;
  recordsTotal: number;
}

@Component({
  selector: 'app-admin-instituciones',
  templateUrl: './admin-instituciones.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./admin-instituciones.component.css'],
})
export class AdminInstitucionesComponent implements OnInit {

  InstitucionesDatatable: any[];
  // modal
  private modal: any;
  public institucionInfo: any;
  public municipios: any;
  message = '';
  private row: number;

  dtOptions: DataTables.Settings = {};

  private api_url_instituciones;
  clickSelected = '';


  constructor(private adminUsuarioService: AdminService,
              private httpClient: HttpClient,
              private modalService: NgbModal,
              private router: Router,
              private _departamentoService: DepartamentoService,
              private _municipioService: MunicipioService) {
    this.api_url_instituciones = '/api/institucion/dataTables';
  }


  someClickHandler(info: any): void {
      this.message = info.id + ' | ' + info.nombre;
     this.institucionInfo = info;
  }

  openModal(content) {
    this.modal = this.modalService.open(content, { windowClass: 'animated jello' });
  }


  ngOnInit() {
    this.getDataRow();
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

        this.httpClient.post<DataTablesResponse>(this.api_url_instituciones, dataTablesParameters, {headers}).subscribe(
          resp => {
            this.InstitucionesDatatable = resp.data;
           //  console.log(this.InstitucionesDatatable);
            callback({
              recordsTotal: resp.recordsTotal,
              recordsFiltered: resp.recordsFiltered,
              data: []
            });
          });
      },
      columns: [
        { data: 'urlImagen', width: '5%'},
        { data: 'id', width: '10%' },
        { data: 'nombre', width: '30%'},
        { data: 'email'},
        { data: 'nit'},
        { data: 'telefono'},
        { data: 'estado.nombre'},
        { data: 'tipoInstitucion.nombre'}
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
    this.someClickHandler(this.InstitucionesDatatable[rowId]);
  }

  eliminarInstitucion() {
    if (this.checkIsSelectedEvent()) {
    swal({
      title: 'Está segudo de ELIMINAR la Institución ' + this.institucionInfo.id + '?',
      text: 'Para ELIMINAR esta institución, click en Confirmar!',
      icon: 'warning',
      buttons: ['Cancelar', 'Confirmar'],
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          this.Eliminar();
        } else {
          swal('usted no eliminó la institución');
          this.modal.close();
        }
      });
    }
  }

  Eliminar() {
    this.institucionInfo.estado.id = 7;
    this.adminUsuarioService.editarInstitucion(this.institucionInfo.id, this.institucionInfo).subscribe(
      response => {
        if (response.exito === true) {
          swal('Buen trabajo!', 'La oferta se ha elimimado con éxito del sistema', 'success')
            .then((value) => {
              this.InstitucionesDatatable.splice(this.row, 1);
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

  editarInstitucion() {
    if (this.checkIsSelectedEvent()) {
      this.router.navigate(['/admin/admin-editar-institucion', this.institucionInfo.id]);
    }
  }

  estudiantesInstitucion() {
    if (this.checkIsSelectedEvent()) {
      this.router.navigate(['/admin/estudiantes-institucion', this.institucionInfo.id, this.institucionInfo.nombre]);
    }
  }

  checkIsSelectedEvent() {
    if (this.institucionInfo == null) {
      swal({
        title: 'Error!',
        icon: 'warning',
        text: 'Debe seleccionar una institución de la tabla.',
        timer: 2000,
        buttons: ['Cancelar', 'Aceptar'],
        dangerMode: true,
      });
      return false;
    }else {
      return true;
    }
  }


  disponible(id: number) {
    this.adminUsuarioService.editarInstitucion(id, this.institucionInfo).subscribe(
      response => {
        if (response.exito === true) {
          swal('Buen trabajo!', 'Se cambio el estado de la Institución', 'success');
          console.log(response);
        }else {
          swal('Algo a salido mal', 'Error al cambiar estado de la Institución', 'error')
            .then((value) => {
              location.reload();
            });
        }
      },
      error => {
        swal('Algo a salido mal', 'Error al cambiar estado de la Institución', 'error')
          .then((value) => {
            location.reload();
          });
        console.log(<any>error);
      }
    );
  }

}

