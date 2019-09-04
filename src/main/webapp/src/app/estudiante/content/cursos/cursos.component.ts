import { Component, OnInit, AfterViewInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import {Router} from '@angular/router';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgForm } from '@angular/forms';
import { CursoService } from '../../../services/estudiante/cursos.service';
import { CalificarCurso } from '../../../models/calificar-curso.interface';
import swal from 'sweetalert';


@Component({
  selector: 'app-cursos',
  templateUrl: './cursos.component.html',
  styleUrls: ['./cursos.component.css'],
  providers: [CursoService]
})
export class CursosComponent implements OnInit, AfterViewInit {

	public cursos: any [] = [];
  public idTipoOfrece: number;
  public responseCalificacion = false;
  public messageCalificacion: string;
  public classMessage: string;
  public iconMessage: string;

  public calificarCurso: any;

  constructor(private http: HttpClient,
              private _cursoService: CursoService,
              private modalService: NgbModal,
              private router: Router
            ) {

    this.calificarCurso = {
      idOferta: 0 ,
      idUsuario: this.getIdUsuario(),
      calificacion: 0,
      comentario:  ''
    };
  }

  ngOnInit() {
  	this.getDataCursos();
  }


  openModal(content, idOferta, idTipoOfrece) {
    this.modalService.open(content, { windowClass: 'dark-modal' });
    this.calificarCurso.idOferta = idOferta;
    this.idTipoOfrece = idTipoOfrece;
    // console.log ('---Inside method \'openModal()\' ');
    // console.log('idOferta: ' + idOferta + ' idTipoOfrece: ' + this.idTipoOfrece);
  }

  eliminarOferta(idInscripcion, idTipoOfrece) {
    // alert('oferta eliminada\n inscripcion: ' + idInscripcion  + '\n idTipoOfrece:' + idTipoOfrece);

    swal({
      title: 'Está seguro que quiere eliminar esta inscripción?',
      text: 'Tenga en cuenta que la información no se podrá recuperar!',
      icon: 'warning',
      buttons: ['Cancelar', 'Confirmar'],
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          this._cursoService.eliminarIncripcion(idInscripcion, idTipoOfrece).subscribe(
            response => {
              swal('¡Ok!', 'Operación exitosa!!', 'success' );
              this.getDataCursos();
            },
            error => {
              swal('¡Upps!', 'Hubo un problema al hacer tu petición. Intentalo más tarde', 'error' );
              console.error(<any>error);
            }
          );
        } else {
          swal('Por el momento todo seguira igual.');
        }
      });

  }


  getDataCursos() {
  	this._cursoService.getCursos().subscribe(
  			response => {
  				this.cursos = response;
          // console.log(response);
  			},
  			error => {
  					console.log(<any>error);
  			});
  }

  getIdUsuario() {
    const dataUser = JSON.parse(localStorage.getItem('user'));
    return dataUser.id;
  }

  onSubmitCalificacion() {

    // this.verificarCalificacion(this.calificarCurso.idOferta, this.idTipoOfrece, this.calificarCurso.idUsuario);

    this._cursoService.calificarCurso(this.calificarCurso, this.idTipoOfrece)
      .subscribe(
        response => {
          this.responseCalificacion = true;
          if (response.exito) {
            swal('¡Bien hecho!', 'Calificación del curso exitosa!!', 'success' );
            this.messageCalificacion = 'Calificación del curso exitosa!!';
            this.responseCalificacion = false;
            this.calificarCurso.calificacion =0;
            this.calificarCurso.comentario = '';
          } else {
            swal('¡Upps!', 'Hubo un problema al hacer la calificación', 'error' );
          }
        },
        error => {
          swal('¡Upss!', ' Ya has calificado oferta', 'error' );
          console.log(<any>error);
          }
       );
     // console.log(this.calificarCurso);

  }
  resetForm(form: NgForm) {
    this.responseCalificacion = false;
    this.calificarCurso.calificacion = 0;
    this.calificarCurso.comentario = '';

    form.resetForm();
  }

  verificarCalificacion(idCurso, idTipoOfrece: number, idUsuario): Observable<boolean> | boolean {
    switch (idTipoOfrece) {
      case 1:
        this._cursoService.checkCalificacionCursoInstitucion(idCurso, idUsuario)
          .subscribe(
            response => {
              console.log('--response check calificacion');
               console.log(response);
            },
            error => {
                console.log(<any>error);
            });
          console.log(idCurso + ' institicion: ' + idTipoOfrece);
         return true;

        // return this._cursoService.verificarCalificacionCursoInstitucion(this.getIdUsuario(), idCurso);
      case 2:
        this._cursoService.checkCalificacionCursoFreelancer(idCurso,idUsuario)
          .subscribe(
            response => {
              console.log('--response check calificacion');
              console.log(response);
            },
            error => {
                console.log(<any>error);
            });
        console.log(idCurso + ' freelancer: ' + idTipoOfrece);
        // return this._cursoService.verificarCalificacionCursoFreelancer(this.getIdUsuario(), idCurso);
        return true;
    }
  }

  navigateToTest(){
    this.router.navigate(['/test/fase1']);

  }

  ngAfterViewInit() {
    // tooltip
    $(function() {
      (<any>$('[data-toggle="tooltip"]')).tooltip();
    });
  }
}
