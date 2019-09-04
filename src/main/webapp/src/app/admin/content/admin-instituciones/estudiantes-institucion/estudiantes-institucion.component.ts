import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute, Params, Router }    from '@angular/router';
import swal from 'sweetalert';

import { OfertasService } from '../../../../services/freelancer/ofertas.service';
import { AuthService } from '../../../../shared/guard/auth.service';


@Component({
  selector: 'app-estudiantes-institucion',
  templateUrl: './estudiantes-institucion.component.html',
  styleUrls: ['./estudiantes-institucion.component.css'],
  providers: [ OfertasService]
})
export class EstudiantesInstitucionComponent implements OnInit {

  public inscripciones: any;
	public dataUser: any;
	public dataStudent: any;
  private modal: any;
  private idInstitucion: number;
  public nombreInstitucion: string;

  constructor(private _route: ActivatedRoute,
    private ofertasService: OfertasService,
  	private authService: AuthService,
  	private modalService: NgbModal) {

      this._route.params.forEach((params: Params) => {
        this.idInstitucion = params['idInstitucion'];
        this.nombreInstitucion = params['nombreInstitucion'];
      });

    }

  ngOnInit() {
    this.getIncripciones();
  }

  getIncripciones() {
  	this.ofertasService.getIncripcionesInstitucion(this.idInstitucion)
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
