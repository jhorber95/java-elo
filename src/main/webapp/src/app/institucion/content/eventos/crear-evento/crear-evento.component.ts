import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';

import swal from 'sweetalert';

import {  EventoService } from '../../../../services/evento.service';
import {  Evento, Institucion } from '../../../../models/evento.interface';


 
@Component({
  selector: 'app-crear-evento',
  templateUrl: './crear-evento.component.html',
  styleUrls: ['./crear-evento.component.css'],
  providers: [ EventoService ]

})
export class CrearEventoComponent implements OnInit {

	public evento: any;
	public dataIns: Institucion;
	public submitForm: boolean;
	modelDate: any;

	 // upload Image
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  private lastInsert: number;

  constructor(private eventoService: EventoService) {

  	this.submitForm = false;

  	this.dataIns = JSON.parse(localStorage.getItem('user'));
  	this.inicializedModel();

  }

  ngOnInit() {
  }

  inicializedModel() {
  	this.evento = {
  		titulo: '',
  		descripcion: '',
  		institucion: {
  			id: this.dataIns.id
  		},
  		tipoEvento:{
  			id: 0
  		},
  		estado:{
  			id: 0
  		},
  		fecha: ''
  	};
  }

  onSubmitForm() {

  	this.evento.fecha  = this.modelDate.year + '-'+this.modelDate.month+'-'+this.modelDate.day;

  	this.eventoService.createEvento(this.evento)
  		.subscribe( 
        response => {
        		console.log(response);
            // swal('¡Bien hecho!','Operación exitosa', 'success' );
            this.submitForm = true;
            this.lastInsert = response.body.id;
        },
       	error =>  {
          swal('Upps!','Hubo un error. Intentalo de nuevo', 'error' );

        console.log(<any>error)
      }
     );
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }
 
  upload() {
    this.progress.percentage = 0;
 
    this.currentFileUpload = this.selectedFiles.item(0);
    this.eventoService.uploadImageEvent(this.currentFileUpload,this.lastInsert )
      .subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            console.log('File is completely uploaded!');
           // console.log(event);
            swal('¡Bien hecho!','Operación exitosa', 'success' );
  					this.inicializedModel();
            
          }
          

        },
        error => {
          swal('Upps!','Parece que la imagen es muy pesada. Intentalo de nuevo', 'error' );
          console.log(<any>error);
        }
      );
 
    this.selectedFiles = undefined;
    // this.currentFileUpload = undefined;
  }

}
