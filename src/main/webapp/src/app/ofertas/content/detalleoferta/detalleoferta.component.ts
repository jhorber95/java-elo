import { Component, OnInit, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import swal from 'sweetalert';

import { DetalleofertaService } from '../../../services/detalleoferta/detalleoferta.service';
import { UserService } from '../../../services/user/user.service';
import { AuthService } from '../../../shared/guard/auth.service';

@Component({
  selector: 'app-detalleoferta',
  templateUrl: './detalleoferta.component.html',
  styleUrls: ['./detalleoferta.component.css'],
  providers: [DetalleofertaService, UserService]
})
export class DetalleofertaComponent implements OnInit, AfterViewInit {

  private id: number;
  private user: any;
  public detalle: any[] = [];
  public Ofrece: any[] = [];
  public array;

  public isLoged: boolean;


  constructor(private _route: ActivatedRoute,
    private _detalleOfertaService: DetalleofertaService,
    private _userService: UserService,
    private router: Router,
    private authService: AuthService
  ) {
    this.id = 0;

    this.user = JSON.parse(localStorage.getItem('user'));


    this.isLoged = localStorage.getItem('token') && this.authService.isAuthenticated();

  }

  ngOnInit() {
    this._route.params.forEach((params: Params) => {
      this.id = params['id'];
    });
    this.getDetalleOferta();
  }

  getDetalleOferta() {
    this._detalleOfertaService.getDetalleOferta(this.id).subscribe(
      response => {
          this.detalle = response.data;
          this.getNombreTipoOfrece(this.detalle[0].tipoOfrece.id);
      },
      error => {
        var errorMessage = <any>error;
        console.log(errorMessage);
      }
      );
  }

  getNombreTipoOfrece(id) {

    if (id === 2) {
      this._detalleOfertaService.getNombreFrelancer(this.detalle[0].idOfrece).subscribe(
        response => {
          this.Ofrece = response.data;
        },
        error => {
          var errorMessage = <any>error;
          console.log(errorMessage);
        }
      );
    } else {
      this._detalleOfertaService.getNombreInstitucion(this.detalle[0].idOfrece).subscribe(
        response => {
          this.Ofrece = response.data;
        },
        error => {
          var errorMessage = <any>error;
          console.log(errorMessage);
        }
      );
    }
  }

  incribirCurso(idCurso){

    let idTipoOfrece = this.detalle[0].tipoOfrece.id;

    if (localStorage.getItem('token') && this.authService.isAuthenticated()) {
      if (this.checkRolEstudiante()) {
        this._detalleOfertaService.subscribeToCourse(idTipoOfrece, idCurso, this.user.id)
          .subscribe(
            response =>{
              // console.log(response);
              swal('¡Felicitaciones!','Inscripción exitosa, pronto nos comunicaremos','success'
              );
            },
            error => {
              swal('¡Atención!','Ya estas inscrito es esta oferta educativa.','error',
              );
              console.log(<any>error);
            }
          );
        localStorage.removeItem('CourseToSubscribe');
      }else{
        swal('¡Error!', 'No tienes el rol de estudiante', 'error');
      }

    }else{
      this.redirecToLoginAndSaveCourse(idCurso, idTipoOfrece);
    }
    }
    //console.log('idCurso: ' + idCurso); console.log(this.user)


  redirecToLoginAndSaveCourse(idCurso, idTipoOfrece): void{
    localStorage.setItem('CourseToSubscribe', '{"idCourse":' + idCurso + ',"idTipoOfrece":' +idTipoOfrece+'}');
    this.router.navigate(['/login']);
  }

  checkRolEstudiante(): boolean{
    let rolUser:any []= [];
    rolUser = this.user.roles;

    for (let i = 0 ; i < rolUser.length; i++) {
      if (rolUser[i]['nombre'] == 'ROLE_ESTUDIANTE' ) {
        return true;
      }
    }
    return false;
  }

  ngAfterViewInit() {
    window.scrollTo(0, 0);
        $(function () {
            $(".preloader").fadeOut();
        });
  }

}
