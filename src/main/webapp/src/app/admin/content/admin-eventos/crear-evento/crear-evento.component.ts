import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';

import swal from 'sweetalert';

import {  EventoService } from '../../../../services/evento.service';
import {  Evento, Institucion } from '../../../../models/evento.interface';
import { InstitucionService } from '../../../../services/institucion.service';



@Component({
  selector: 'app-crear-evento',
  templateUrl: './crear-evento.component.html',
  styleUrls: ['./crear-evento.component.css'],
  providers: [ EventoService, InstitucionService ]

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
  public instituciones: any;

  constructor(private eventoService: EventoService, private  institucionService: InstitucionService) {

  	this.submitForm = false;

  	this.dataIns = JSON.parse(localStorage.getItem('user'));
  	this.inicializedModel();

  }

  ngOnInit() {
    this.getinstituciones();
  }

  inicializedModel() {
  	this.evento = {
  		titulo: '',
  		descripcion: '',
  		institucion: {
  			id: 0
  		},
  		tipoEvento: {
  			id: 0
  		},
  		estado: {
  			id: 0
  		},
  		fecha: ''
  	};
  }

  getinstituciones() {
    this.institucionService.getAllIntitciones().subscribe(
      response => {
          this.instituciones = response.data;
      },
      error =>  {
         //  swal('Upps!', 'Hubo un error. Intentalo de nuevo', 'error' );¿
        console.log(<any>error);
      }
    );
  }

  onSubmitForm() {

  	this.evento.fecha  = this.modelDate.year + '-' + this.modelDate.month + '-' + this.modelDate.day;

  	this.eventoService.createEvento(this.evento)
  		.subscribe(
        response => {
        		console.log(response);
        		// console.log(this.evento);
            // swal('¡Bien hecho!','Operación exitosa', 'success' );
            this.submitForm = true;
            this.lastInsert = response.body.id;
        },
       	error =>  {
          swal('Upps!', 'Hubo un error. Intentalo de nuevo', 'error' );

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
            swal('¡Bien hecho!', 'Operación exitosa', 'success' );
  					this.inicializedModel();

          }


        },
        error => {
          swal('Upps!', 'Parece que la imagen es muy pesada. Intentalo de nuevo', 'error' );
          console.log(<any>error);
        }
      );

    this.selectedFiles = undefined;
    // this.currentFileUpload = undefined;
  }

}
