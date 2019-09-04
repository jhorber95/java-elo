import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router }    from '@angular/router';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { NgForm } from '@angular/forms';
import swal from 'sweetalert';

import { DetalleuniversidadService } from '../../../../services/universidad/detalleuniversidad.service';
import { UserService } from '../../../../services/user/user.service';
import { TipoInstitucionService } from '../../../../services/tipoInstitucion.service';



@Component({
  selector: 'app-editar-institucion',
  templateUrl: './editar-institucion.component.html',
  styleUrls: ['./editar-institucion.component.css'],
  providers: [
    DetalleuniversidadService,
    UserService,
		TipoInstitucionService]
})
export class EditarInstitucionComponent implements OnInit {

  public idInstitucion;
  public dataInstitucion: any = {};
	public tipoIntituciones: any;

  lat: number;
  lng: number;
  zoom: number;

    // upload Image
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(private _route: ActivatedRoute,
    private router: Router,
    private detalleuniversidadService: DetalleuniversidadService,
    private _userService: UserService,
    private tipoInstitucionService: TipoInstitucionService) {
      this.dataInstitucion = {
        id: 0,
        nit: 0,
        nombre : '',
        latitud: 0,
        longitud: 0,
        telefono : '',
        direccion: '',
        email: '',
        descripcion: '',
        url: '',
        tipoInstitucion: {
          id: 0
        }
      };
      this._route.params.forEach((params: Params) => {
        this.idInstitucion = params['idInstitucion'];
      });
      this.getInstitucionById(this.idInstitucion);
      this.zoom = 15;
     }

  ngOnInit() {
    this.getTipoIntituciones();
    this.lat = Number(this.dataInstitucion.latitud);
    this.lng = Number(this.dataInstitucion.longitud);
  }

  getInstitucionById(idInstitucion) {
    this.detalleuniversidadService.getDetalleUniversidad(idInstitucion).subscribe(
      response => {
        this.dataInstitucion = response.data[0];
        // console.log(this.dataInstitucion);
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  getTipoIntituciones() {
  	this.tipoInstitucionService.getTipoInstitucion()
  		.subscribe(
  			response => {
  				this.tipoIntituciones = response;
  				// console.log(this.tipoIntituciones);
  			},
  			error => {
  				console.log(<any>error);
  			}
  		);
  }

  onSubmitDataUser() {
  	this._userService.updateProfileInstitucion(this.dataInstitucion)
  		.subscribe(
  			response => {
  				swal('¡Bien hecho!', 'Operación exitosa', 'success');
  				// console.log(response);
  				this.updateToken();
  			},
  			error => {
  				console.log(<any>error);
					swal('¡Error!', 'Lo sentimos, halgo salio mal. Intentalo nuevamente.', 'error');
  			}
  		);
    // console.log(this.dataInstitucion);

  }

  updateToken() {
  	this._userService.getUserData(this.dataInstitucion.email)
  			.subscribe(
  				response => {
  					localStorage.setItem('user', JSON.stringify(response));
            this.getInstitucionById(this.idInstitucion);
  				},
  				error => {
  					console.log(<any>error);
  				}
  			);
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload() {
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFiles.item(0);
    this._userService.uploadProfileImageInstitucion(this.currentFileUpload, this.idInstitucion )
      .subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            /// console.log('File is completely uploaded!');
            swal('¡Bien hecho!', 'Operación exitosa', 'success' )
              .then((value) => {
                location.reload();
              });
            this.updateToken();
            this.getInstitucionById(this.idInstitucion);
          }
          // console.log(event);

        },
        error => {
          swal('¡Ups!', 'Error de conexión o imagen muy pesada', 'error' )
            .then((value) => {
              location.reload();
            });
          console.log(<any>error);
        }
      );

    this.selectedFiles = undefined;
    // this.currentFileUpload = undefined;
  }

}
