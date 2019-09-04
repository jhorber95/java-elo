import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OfertasService } from '../../../services/freelancer/ofertas.service';
import { AuthService } from '../../../shared/guard/auth.service';
import swal from 'sweetalert';

@Component({
  selector: 'ins-inscripciones-insti',
  templateUrl: './inscripciones-insti.component.html',
  styleUrls: ['./inscripciones-insti.component.css'],
  providers: [ OfertasService]
})
export class InscripcionesInstiComponent implements OnInit {

  public inscripciones: any;
	public dataUser: any;
	public dataStudent: any;
	private modal: any;

  constructor(
  	private ofertasService: OfertasService,
  	private authService: AuthService,
  	private modalService: NgbModal) {

  	this.dataUser = this.authService.getAllDataUser();
  }

  ngOnInit() {
  	this.getIncripciones();
  }

  getIncripciones() {
  	this.ofertasService.getIncripcionesInstitucion(this.dataUser.id)
  		.subscribe(
  			response => {
  				this.inscripciones = response.data;
  				// console.log(response);
  			},
  			error => {
				console.log(<any>error);
  			}
  		);
  }


  dataEstudiante(content, row) {
  	this.dataStudent = this.inscripciones[row]['estudiante'];
  	// console.log('--student data ');
  	// console.log(this.dataStudent);
    this.modal = this.modalService.open(content, { windowClass: 'animated jello' });
  }

  confirmarInscripcion(idOferta: number, idEstudiante: number) {
  	// console.log('codOFer: ' +idOferta +' codEstudiante:'+ idEstudiante);
  	this.ofertasService.confirmarInscripcionInstitucion(idOferta, idEstudiante)
  		.subscribe(
  			response => {
  				// this.inscripciones = response.data;
  				this.getIncripciones();
  				// console.log(response);
  				swal('¡Bien hecho!', 'Operación exitosa', 'success');
  			},
  			error => {
          swal('¡Error!', 'Lo sentimos, halgo salio mal. Intentalo nuevamente.', 'error');
				console.log(<any>error);
  			}
  		);
  }

  confirmarRechazoIncripcion(idOferta: number, idEstudiante: number) {
     swal({
      title: 'Está seguro que quiere rechazar esta inscripción?',
      text: 'Para rechazar, click en Confirmar!',
      icon: 'warning',
      buttons: ['Cancelar', 'Confirmar'],
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          this.rechazarInscripcion(idOferta, idEstudiante);
        } else {
          swal('Por el momento todo seguira igual.');
        }
      });
  }

  rechazarInscripcion(idOferta: number, idEstudiante: number) {
    // console.log('codOFer: ' +idOferta +' codEstudiante:'+ idEstudiante);
    this.ofertasService.rechazarIncripcionInstitucion(idOferta, idEstudiante)
      .subscribe(
        response => {
          this.getIncripciones();
          // console.log(response);
          swal('¡Bien hecho!', 'Operación exitosa', 'success' );
        },
        error => {
          swal('¡Error!', 'Lo sentimos, halgo salio mal. Intentalo nuevamente.', 'error');
        console.log(<any>error);
        }
      );
  }

}
