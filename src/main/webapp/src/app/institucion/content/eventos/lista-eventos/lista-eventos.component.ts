import { Component,NgZone, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import swal from 'sweetalert';

import {  EventoService } from '../../../../services/evento.service';


@Component({
  selector: 'app-lista-eventos',
  templateUrl: './lista-eventos.component.html',
  styleUrls: ['./lista-eventos.component.css'],
  providers: [ EventoService ]
})
export class ListaEventosComponent implements OnInit {

	eventos: any;
  dataUser: any;

  constructor( 
              private modalService: NgbModal,
              private eventoService: EventoService) {
     this.dataUser = JSON.parse(localStorage.getItem('user'));
  }

  ngOnInit() {
    this.getEventos();
  	
  }

  getEventos(){
    this.eventoService.getEventos(this.dataUser.id)
      .subscribe(
        response => {
           this.eventos = response;
          // console.log(response);
        },
        error => {
          console.log(<any>error );
      });
  }

  eliminar(idEvento){
      swal({
      title: "Está seguro que quiere elimiinar este evento",
      text: "Tenga en cuenta que la información no se podrá recuperar!",
      icon: "warning",
      buttons: ["Cancelar", "Eliminar"],
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          this.confirmDeleteEvent(idEvento);
        } else {
          swal("Por el momento todo seguira igual.");
        }
      });
  }


  confirmDeleteEvent(idEvento){
    this.eventoService.deleteEvento(idEvento)
      .subscribe(
        response => {
           swal('Buen Trabajo!','Transacción exitosa.','success');
           this.getEventos();
           //console.log(response);
        },
        error => {
          swal('Oppps!','Ocurrión un error. Intentalo nuevamente.','error');
          console.log(<any>error );
      });

  }
}
