import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';

import { UserService } from '../../../services/user/user.service';
import { DepartamentoService } from '../../../services/departamento.service';
import { MunicipioService }  from '../../../services/municipio.service';
import { Persona, Roles }  from '../../../models/persona';
import { FormGroup } from '@angular/forms';
import { NgForm } from '@angular/forms';
import swal from 'sweetalert';
import {InteresesService} from '../../../services/intereses.service';


@Component({
  selector: 'free-perfil-freelancer',
  templateUrl: './perfil-freelancer.component.html',
  styleUrls: ['./perfil-freelancer.component.css'],
  providers: [
		UserService,
		DepartamentoService,
		MunicipioService,
    InteresesService
  ]
})
export class PerfilFreelancerComponent implements OnInit {

  // localStorage model
  public allDataUser: Persona ;
  // form model
	public dataUser: Persona;
	public rolUser: any[] = [];
	public nameRoles = [];
	public departamentos: any;
	public municipios: any;

	public user:any = {};
  public intereses: any;
  public userInteres: any [] = [];

  // upload Image
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(private _userService: UserService,
  					 private _departamentoService: DepartamentoService,
  					 private _municipioService: MunicipioService,
              private _interesesService: InteresesService

  ) {
    this.allDataUser = JSON.parse(localStorage.getItem('user'));

  	this.checkDataUser();
  }

  ngOnInit() {
  	this.getRoles();
  	this.getDepartamentos();
  	this.getMunicipios();
  	this.getIntereses();
  }

   checkDataUser(){
    if (this.allDataUser.identificacion == null || this.allDataUser.nivelEducativo == null) {
      this.dataUser = {
        id: this.allDataUser.id,
        nombres: this.allDataUser.nombres ,
        apellidos: this.allDataUser.apellidos,
        tipoIdentificacion: {
          id: 0
        },
        identificacion: '',
        telefono: '',
        municipio: {
          id: '',
          departamento: {
            id: '',
            pais: {
              id: ''
            }
          }
        },
        nivelEducativo: {
          id: 0
        },
        email: this.allDataUser.email,
        genero: {
          id: ''
        },
        roles: [],
        intereses: [],
        urlImagen: ''
      };
    }else {
      this.dataUser = this.allDataUser;
      this.userInteres = this.dataUser.intereses;
    }
  }


  getDepartamentos() {
  	this._departamentoService.getDepartamentos()
  		.subscribe(
  			response => {
  				this.departamentos = response;
  				// console.log(this.departamentos);
  			},
  			error => {
  				console.log(<any>error);
  			}
  		);
  }

  getMunicipios() {
  	this._municipioService.getMunicipio()
  		.subscribe(
  			response => {
  				this.municipios = response;
  				// console.log(this.municipios);
  			},
  			error => {
  				console.log(<any>error);
  			}
  		) ;
  }

  onSelectDepartamento(event) {
    // console.log(event);
    this.municipios = null;
  	this._municipioService.getMunicipioByIdDepartamento(event)
  				.subscribe(
  					response => {
		  				this.municipios = response;
		  				// console.log(this.municipios);
		  			},
		  			error => {
		  				console.log(<any>error);
		  			}
  				);
  	// console.log(this.allDataUser.municipio.departamento.id);
  }

  getIntereses() {
    this._interesesService.getIntereses()
      .subscribe(
        response => {
          this.intereses = response.data;
          // console.log(this.intereses);
        },
        error => {
          console.log(<any>error);
        }
      );
  }

  checkEventInteres(event) {
    const selectElementText = event.target['options'][event.target['options'].selectedIndex].text;
    // this.userInteres.push({id: event.target.value, nombre: selectElementText});
    // console.log('id: ' + event.target.value + ' text: ' + selectElementText );
    this.dataUser.intereses.push({id: Number(event.target.value), nombre: selectElementText});
  }

  deleteInteres(index) {
    // this.userInteres.splice(index,1);
    this.dataUser.intereses.splice(index,1);
  }

  onSubmitDataUser() {

  	this._userService.updateProfileUser(this.dataUser)
  		.subscribe(
  			response => {
  			// console.log(response);
  				this.updateToken();
          swal('Bien Hecho!', 'Información actualizada correntamente', 'success');
  			},
  			error => {
  				console.log(<any>error);
          swal('uppps!', 'Algo salio mal. Intentalo nuevamente', 'error');
  			}
  		);
  	// console.log(this.dataUser);
  }

  updateToken(){
  	this._userService.getUserData(this.allDataUser.email)
  			.subscribe(
  				response => {
  					localStorage.setItem('user', JSON.stringify(response));
  					this.allDataUser = response;
  				},
  				error => {
  					console.log(<any>error);
  				}
  			);
  }

  getRoles(){
  	this.rolUser = this.allDataUser.roles;
  	// console.log(this.rolUser);

  	for (var i = 0 ; i < this.rolUser.length; i++) {
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
    this._userService.uploadProfileImage(this.currentFileUpload,this.allDataUser.id )
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
