import { Component, AfterViewInit } from '@angular/core';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';

import { UserService } from '../../services/user/user.service';
import { DepartamentoService } from '../../services/departamento.service';
import { MunicipioService }  from '../../services/municipio.service';
import { ProfileUsers }  from '../../models/profile-user';
import { FormGroup } from '@angular/forms';
import { NgForm } from '@angular/forms';
import swal from 'sweetalert';
import {Persona} from "../../models/persona";


@Component({
	templateUrl: './starter.component.html',
	providers: [
		UserService,
		DepartamentoService,
		MunicipioService
  ]
})
export class StarterComponent implements AfterViewInit {
	public allDataUser: any = {};
	public dataUser: Persona;
	public rolUser:any []= [];
	public nameRoles = [];
	public departamentos: any;
	public municipios: any;
	public user:any = {};

    // upload Image
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(private _userService: UserService,
  					 private _departamentoService: DepartamentoService,
  					 private _municipioService: MunicipioService
  	) {
    this.allDataUser = JSON.parse(localStorage.getItem('user'));
    this.checkDataUser();
  }

  checkDataUser(){
    if (this.allDataUser.identificacion == null) {
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


    }else{
      this.dataUser = this.allDataUser;


    }
  }

  ngOnInit() {
  	this.getRoles();
  	this.getDepartamentos();
  	this.getMunicipios();
  }

  getAllUserData(){
  	this.allDataUser = this._userService.getAllDataUser();
  }

  getDepartamentos(){
  	this._departamentoService.getDepartamentos()
  		.subscribe(
  			response =>{
  				this.departamentos = response;
  				// console.log(this.departamentos);
  			},
  			error => {
  				console.log(<any>error);
  			}
  		);
  }

  getMunicipios(){
  	this._municipioService.getMunicipio()
  		.subscribe(
  			response =>{
  				this.municipios = response;
  				// console.log(this.municipios);
  			},
  			error => {
  				console.log(<any>error);
  			}
  		) ;
  }

   onSubmitDataUser(){

  	this._userService.updateProfileUser(this.dataUser)
  		.subscribe(
  			response => {
  			  swal('Buen Trabajo', 'Perfil actualizado con éxito', 'success');
  				this.updateToken();
  			},
  			error => {
  				console.log(<any>error);
  			}
  		);
  	console.log(this.dataUser);

  }

  updateToken(){
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

	ngAfterViewInit(){ }
}
