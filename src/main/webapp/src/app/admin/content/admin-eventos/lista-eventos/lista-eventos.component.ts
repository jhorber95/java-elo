import { Component, NgZone, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import swal from 'sweetalert';

import {  EventoService } from '../../../../services/evento.service';
import { Ofertasdatatable } from '../../../../models/ofertasDatatable/ofertasdatatable';
import { Router } from '@angular/router';

class DataTablesResponse {
  data: any[];
  draw: number;
  recordsFiltered: number;
  recordsTotal: number;
}

@Component({
  selector: 'app-lista-eventos',
  templateUrl: './lista-eventos.component.html',
  styleUrls: ['./lista-eventos.component.css'],
  providers: [ EventoService ]
})
export class ListaEventosComponent implements OnInit {

  eventos: any[];
  OfertasDatatable: Ofertasdatatable[];

  dataUser: any;
  dtOptions: DataTables.Settings = {};

  message = '';
  private row: number;
  clickSelected = '';
  public eventoInfo: any;

  private urlApi;

  constructor(
              private modalService: NgbModal,
              private httpClient: HttpClient,
              private zone: NgZone,
              public router: Router,
              private eventoService: EventoService) {
     this.dataUser = JSON.parse(localStorage.getItem('user'));

     this.urlApi = '/api/evento/dataTables';
   // this.urlApi = '/api/oferta/dataTables';
  }

  ngOnInit() {
    this.getAllEvents();
  }
/*
  getEventos() {
    this.eventoService.getEventos(this.dataUser.id)
      .subscribe(
        response => {
           this.eventos = response;
          // console.log(response);
        },
        error => {
          console.log(<any>error );
      });
  }*/

  getAllEvents() {
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

        this.httpClient.post<DataTablesResponse>(this.urlApi, dataTablesParameters, {headers}).subscribe(
          resp => {
            this.eventos = resp.data;
            console.log(this.eventos);
            callback({
              recordsTotal: resp.recordsTotal,
              recordsFiltered: resp.recordsFiltered,
              data: []
            });
          },
          error => {
            console.log(<any>error);
          }
        );
      },
      columns: [
        { data: 'id', width: '10%' },
        { data: 'titulo', width: '30%' },
        { data: 'descripcion' },
        { data: 'institucion'},
        { data: 'tipoEvento'},
        { data: 'estado'}


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
  someClickHandler(info: any): void {
     this.message = info.id + ' | ' + info.titulo;
     this.eventoInfo = info;
  }
  ClickRow(rowId, element: any) {
    this.row = rowId;
    if (element === this.clickSelected) {
      this.clickSelected = '0';
    } else {
      this.clickSelected = element;
    }
    this.someClickHandler(this.eventos[rowId]);
  }

  editarEvento() {
    if (this.eventoInfo == null) {
      swal({
        title: 'Error!',
        icon: 'warning',
        text: 'Debe seleccionar un evento de la tabla.',
        timer: 2000,
        buttons: ['Cancelar', 'Aceptar'],
        dangerMode: true,
      });
      // swal('Error!', 'Debe seleccionar un evento de la tabla.', 'error');
    }else {
      this.router.navigate(['/admin/editar-evento', this.eventoInfo.id]);
    }
  }

  eliminar() {
    if (!this.checkIsSelectedEvent()) {

    }else {
      swal({
        title: 'Está seguro que quiere eliminar este evento',
        text: 'Tenga en cuenta que la información no se podrá recuperar!',
        icon: 'warning',
        buttons: ['Cancelar', 'Aceptar'],
        dangerMode: true,
      })
        .then((willDelete) => {
          if (willDelete) {
            this.confirmDeleteEvent(this.eventoInfo.id);
          } else {
            swal('Por el momento todo seguira igual.');
          }
        });
    }
  }

  checkIsSelectedEvent() {
    if (this.eventoInfo == null) {
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



  confirmDeleteEvent(idEvento) {
    this.eventoService.deleteEvento(idEvento)
      .subscribe(
        response => {
           swal('Buen Trabajo!', 'Transacción exitosa.', 'success');
           this.getAllEvents();
           this.eventos.splice(this.row, 1);
           // console.log(response);
        },
        error => {
          swal('Oppps!', 'Ocurrión un error. Intentalo nuevamente.', 'error');
          console.log(<any>error );
      });

  }
}
