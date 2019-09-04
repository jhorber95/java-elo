import { Component, NgZone, OnInit, ViewEncapsulation } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AdminService } from '../../services/admin.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/toPromise';

//sweat alert
import swal from 'sweetalert';
import {Ofertasdatatable} from "../../../models/ofertasDatatable/ofertasdatatable";

class DataTablesResponse {
  data: any[];
  draw: number;
  recordsFiltered: number;
  recordsTotal: number;
}

@Component({
  selector: 'app-admin-pqrs',
  templateUrl: './admin-pqrs.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./admin-pqrs.component.css'],
})
export class AdminPqrsComponent implements OnInit {
  //dtOptions: Promise<DataTables.Settings>;
  PqrsDatatable: any[];
  //modal
  private modal: any;
  public PQRsInfo: any;
  public departamentos: any;
  public municipios: any;
  message = '';
  private row: number;

  // private usuariosDatatable: any[] = [];
  dtOptions: DataTables.Settings = {};

  private api_url_pqrs;
  clickSelected: string = '';

  // api_url = 'https://angular-datatables-demo-server.herokuapp.com/';

  constructor(private adminUsuarioService: AdminService,
              private httpClient: HttpClient,
              private zone: NgZone,
              private modalService: NgbModal) {
    this.api_url_pqrs = '/api/denuncia/dataTables';
  }


  someClickHandler(info: any): void {
      this.message = 'ID del mensaje: ' + info.id + ' | Id del usuario: ' + info.usuario.id + ' | Usuario: ' + info.usuario.nombres;
     this.PQRsInfo = info;
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
        url: "/assets/data/spanish.json"
      },
      ajax: (dataTablesParameters: any, callback) => {
        let headers = new HttpHeaders();
        headers = headers.set('Authorization', 'Bearer ' + localStorage.getItem('token'));

        this.httpClient.post<DataTablesResponse>(this.api_url_pqrs, dataTablesParameters, {headers}).subscribe(
          resp => {
            this.PqrsDatatable = resp.data;
            console.log(this.PqrsDatatable);
            callback({
              recordsTotal: resp.recordsTotal,
              recordsFiltered: resp.recordsFiltered,
              data: []
            });
          });
      },
      columns: [
        { data: 'idmensaje' }, { data: 'idusuario' }, { data: 'nombres' },
        { data: 'identificacion' }, { data: 'fecha' }, { data: '' }
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
    this.someClickHandler(this.PqrsDatatable[rowId]);
  }

  marcarLeido() {
    this.PQRsInfo.estado.id = 10;
    this.adminUsuarioService.editarDenuncia(this.PQRsInfo.id, this.PQRsInfo).subscribe(
      response => {
        if(response.exito === true){
          swal("Buen trabajo!", "La oferta se ha elimimado con éxito del sistema", "success")
            .then((value) => {
              this.PqrsDatatable.splice(this.row,1)
            });
        } else{
          swal("Algo a salido mal", "Error al intentar eliminar la oferta", "error");
        }
        this.modal.close();
      },
      error => {
        swal("Algo a salido mal", "Error al elminar la oferta", "error");
        console.log(<any>error);
      }
    );
  }


}

