import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router }    from '@angular/router';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';
// import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

import swal from 'sweetalert';

import {  EventoService } from '../../../../services/evento.service';
import {  Evento } from '../../../../models/evento.interface';



@Component({
  selector: 'app-editar-evento',
  templateUrl: './editar-evento.component.html',
  styleUrls: ['./editar-evento.component.css'],
  providers: [ EventoService ]

})
export class EditarEventoComponent implements OnInit {

	public id;
	public evento: any;
	private detailEvent: Evento;
	public eventToEdit: any;

	//date
	modelDate: any;


	// upload Image
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(private _route: ActivatedRoute,
						private router: Router,
						private eventoService: EventoService) {

		this._route.params.forEach((params: Params) => {
	  	this.id = params['id'];
	  });
	  this.getEventoById(this.id);
	  this.modelDate = {
	  	year: 2018,
	  	month: 3,
	  	day: 6
	  };

	  
	}

  ngOnInit() { 

  }

  onSubmitForm(){
  	this.detailEvent.fecha  = this.modelDate.year + '-'+this.modelDate.month+'-'+this.modelDate.day;
  	this.eventoService.updateEvento(this.detailEvent)
  		.subscribe(
        response => {
           //this.evento = response;
           swal('¡Bien hecho!','Operación exitosa', 'success' );
            console.log(response);
        },
        error => {
          console.log(<any>error );
          swal('¡Opps!','Ocurrió un error. Intentalo de nuevo', 'error' );
      });
  }

  getEventoById(idEvento){
  	this.eventoService.getEventoById(idEvento)
  		.subscribe(
        response => {
           this.evento = response.data;
           this.detailEvent = this.evento[0];
           // console.log('--model evento')	;
  				 // console.log(this.detailEvent);
           // // this.evento = evento[0];
           // console.log('--event');
           // console.log(this.evento);
           //console.log(response);
        },
        error => {
          console.log(<any>error );
      });
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }
 
  upload() {
    this.progress.percentage = 0;
 
    this.currentFileUpload = this.selectedFiles.item(0);
    this.eventoService.uploadImageEvent(this.currentFileUpload,this.id )
      .subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            console.log('File is completely uploaded!');
            //his.getDetailOferta();
            // console.log(event);
            swal('¡Bien hecho!','Operación exitosa', 'success' );
          }
          
        },
        error => {
          console.log(<any>error);
           swal('¡Opps!','Ocurrió un error. Intentalo de nuevo.', 'error' );
        }
      );
 
    this.selectedFiles = undefined;
    // this.currentFileUpload = undefined;
  }

}
