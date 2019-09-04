import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import swal from 'sweetalert';
import { DetalleofertaService } from '../services/detalleoferta/detalleoferta.service';
// import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-estudiante',
  templateUrl: './estudiante.component.html',
  styleUrls: ['./estudiante.component.scss'],
  providers: [DetalleofertaService]
})
export class EstudianteComponent implements OnInit {
	public dataUser;
  public rolUser: any []= [];
  public nameRoles: any [] = [];


  constructor(
    private router: Router,
    private _detalleOfertaService: DetalleofertaService
    // private toastr: ToastrService
    ) {

    /* this.toastr.success('Recuerda que puedes calificar las oferta en las que estas inscrito', 'Hola',
      {timeOut: 5000,
        progressBar: true,
        progressAnimation: 'increasing'
      });*/
    this.getDataUser();
  }

  ngOnInit() {
    this.checkSubscriptionToCourse();
    this.getRoles();
  }


  getDataUser() {
    this.dataUser = JSON.parse(localStorage.getItem('user'));
  }

  checkSubscriptionToCourse() {
    const dataCourseToSubscribre = JSON.parse(localStorage.getItem('CourseToSubscribe'));
    // console.log('--- Check course information');
    // console.log(dataCourseToSubscribre);

    if (dataCourseToSubscribre != null) {
      const idUser = this.dataUser.id;
      const idCourse = dataCourseToSubscribre.idCourse;
      const idTipoOfrece = dataCourseToSubscribre.idTipoOfrece;

      this._detalleOfertaService.subscribeToCourse(idTipoOfrece, idCourse, idUser)
          .subscribe(
            response => {
              console.log(response);
              swal(
                '¡Felicitaciones!',
                'Inscripción exitosa, pronto nos comunicaremos',
                'success'
              );
            },
            error => {
                swal(
                  '¡Atención!',
                  'Ya estas inscrito es esta oferta educativa.',
                  'error',
                );
              console.log(<any>error);
            }
          );
     localStorage.removeItem('CourseToSubscribe');
    }
  }

  getRoles() {
      this.rolUser = this.dataUser.roles;
      // console.log(this.rolUser);

      for (let i = 0 ; i < this.rolUser.length; i++) {
          switch (this.rolUser[i]['id']) {
              case 1:
                  this.nameRoles.push({nombre: 'Administrador', url: '/admin'});
                  break;
              case 2:
                  this.nameRoles.push({nombre: 'Estudiante', url: '/estudiante'});
                  break;
              case 3:
                  this.nameRoles.push({nombre: 'Freelancer', url: '/freelancer'});
                  break;
              case 4:
                  this.nameRoles.push({nombre: 'Publicador', url: '/publicador'});
                  break;
              case 5:
                  this.nameRoles.push({nombre: 'Institución'});
                  break;
          }
      }
       // console.log(this.nameRoles);
  }

}
