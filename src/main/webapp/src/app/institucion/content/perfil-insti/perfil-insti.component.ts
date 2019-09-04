import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';

import { UserService } from '../../../services/user/user.service';
import { TipoInstitucionService } from '../../../services/tipoInstitucion.service';

import { FormGroup } from '@angular/forms';
import { NgForm } from '@angular/forms';
import swal from 'sweetalert';

@Component({
  selector: 'ins-perfil-insti',
  templateUrl: './perfil-insti.component.html',
  styleUrls: ['./perfil-insti.component.css'],
  providers: [
		UserService,
		TipoInstitucionService
	]
})
export class PerfilInstiComponent implements OnInit {

	public allDataUser: any = {};
	public dataUser: any;
	public rolUser:any []= [];
	public nameRoles = [];
	public departamentos: any;
	public municipios: any;
	public user:any = {};
	public tipoIntituciones: any;

	lat: number;
  lng: number;
  zoom: number;

    // upload Image
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(private _userService: UserService,
  			private tipoInstitucionService: TipoInstitucionService) {

  	this.getAllUserData();
  	this.dataUser = {
  		id: this.allDataUser.id,
  		nit: this.allDataUser.nit,
  		nombre : this.allDataUser.nombre,
  		latitud: this.allDataUser.latitud,
  		longitud: this.allDataUser.longitud,
			telefono : this.allDataUser.telefono,
			direccion: this.allDataUser.direccion,
      email: this.allDataUser.email,
			descripcion: this.allDataUser.descripcion,
			url: this.allDataUser.url,
			tipoInstitucion: {
				id: this.allDataUser.tipoInstitucion.id
			}
  	};

	}

   ngOnInit() {
  	this.getRoles();
  	this.getTipoIntituciones();
  }

  getAllUserData() {
  	this.allDataUser = JSON.parse(localStorage.getItem('user'));
  	// console.log(this.allDataUser);
  	this.lat = Number(this.allDataUser.latitud);
    this.lng = Number(this.allDataUser.longitud);
    this.zoom = 15;
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

  	this._userService.updateProfileInstitucion(this.dataUser)
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
  	console.log(this.dataUser);

  }

  updateToken() {
  	this._userService.getUserData(this.allDataUser.email)
  			.subscribe(
  				response => {
  					localStorage.setItem('user', JSON.stringify(response));
  					this.getAllUserData();
  				},
  				error => {
  					console.log(<any>error);
  				}
  			);
  }

  getRoles(){
  	this.rolUser = this.allDataUser.roles;
  	// console.log(this.rolUser);

  	for (let i = 0 ; i < this.rolUser.length; i++) {
  		switch (this.rolUser[i]['id']) {
  			case 1:
  				this.nameRoles.push('Administrador');
  				break;
  			case 2:
  				this.nameRoles.push('Estudiante');
  				break;
  			case 3:
  				this.nameRoles.push('Freelancer');
  				break;
  			case 4:
  				this.nameRoles.push('Publicador');
  				break;
  			case 5:
  				this.nameRoles.push('Institución');
  				break;
  		}
  	}
  	// console.log(this.nameRoles);
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload() {
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFiles.item(0);
    this._userService.uploadProfileImageInstitucion(this.currentFileUpload,this.allDataUser.id )
      .subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            /// console.log('File is completely uploaded!');
            swal('¡Bien hecho!','Operación exitosa', 'success' )
              .then((value) => {
                location.reload();
              });
            this.updateToken();
            this.getAllUserData();
          }
          //console.log(event);

        },
        error => {
          swal('¡Ups!','Error de conexión o imagen muy pesada', 'error' )
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
