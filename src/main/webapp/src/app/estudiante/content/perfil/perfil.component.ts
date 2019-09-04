import { Component,
         OnInit }         from '@angular/core';
import { HttpClient,
         HttpResponse,
         HttpEventType }  from '@angular/common/http';
import { FormGroup }      from '@angular/forms';
import { NgForm }         from '@angular/forms';

import { UserService }         from '../../../services/user/user.service';
import { DepartamentoService } from '../../../services/departamento.service';
import { MunicipioService }    from '../../../services/municipio.service';
import { InteresesService }    from '../../../services/intereses.service';
import { Persona }             from '../../../models/persona';


import swal from 'sweetalert';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css'],
  providers: [
  				UserService,
  				DepartamentoService,
  				MunicipioService,
          InteresesService
  ]
})
export class PerfilComponent implements OnInit {

	public allDataUser: any = {};
	public dataUser: Persona;
	public rolUser: any []= [];
	public nameRoles = [];
	public departamentos: any;
	public municipios: any;
  public intereses: any;

	public user: any = {};
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
  	// this.getMunicipios();
    this.getIntereses();
  }

  checkDataUser() {
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
      this.userInteres = null;

    }else {
      this.dataUser = this.allDataUser;
      this.userInteres = this.dataUser.intereses;

    }
  }

  getAllUserData() {
    this._userService.getUserData(this.dataUser.email)
      .subscribe(
        response => {
          this.dataUser = response;
          this.allDataUser = this.dataUser;
          console.log('lista usuario');
         // console.log(response);
        },
        error => {
          console.log(<any>error);
        }
      );
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

  checkEventInteres(event) {
    const selectElementText = event.target['options'][event.target['options'].selectedIndex].text;
   // this.userInteres.push({id: event.target.value, nombre: selectElementText});
    this.dataUser.intereses.push({id: Number(event.target.value), nombre: selectElementText});
  }

  deleteInteres(index) {
    // this.userInteres.splice(index,1);
    this.dataUser.intereses.splice(index, 1);
  }

  onSubmitDataUser() {
    // this.dataUser.intereses = this.userInteres;
    // console.log(this.dataUser);

  	this._userService.updateProfileUser(this.dataUser)
  		.subscribe(
  			response => {
  			  if (response.exito === true) {
            swal('Bien Hecho!', 'Información actualizada correntamente', 'success');
            this.updateToken();
            this.getAllUserData();
          }else {
            swal('uppps!', 'Algo salio mal. Revisa  que todos los datos esten correctos', 'error');
          }
  				// console.log(response);
  			},
  			error => {
  				console.log(<any>error);
          swal('uppps!', 'Algo salio mal. Revisa  que todos los datos esten correctos', 'error');

  			}
  		);
  	// console.log(this.dataUser);

  }

  updateToken() {
  	this._userService.getUserData(this.allDataUser.email)
  			.subscribe(
  				response => {
  					localStorage.setItem('user', JSON.stringify(response));
  					// this.getAllUserData();
           // console.log(response);
  				},
  				error => {
  					console.log(<any>error);
  				}
  			);
  }

  getRoles() {
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
    this._userService.uploadProfileImage(this.currentFileUpload, this.allDataUser.id )
      .subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            swal('¡Bien hecho!', 'Operación exitosa', 'success' )
              .then((value) => {
                location.reload();
              });
            this.updateToken();
            this.getAllUserData();
          }

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
