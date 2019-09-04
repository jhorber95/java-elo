import { Component, OnInit, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Params, Router }    from '@angular/router';
import swal from 'sweetalert';


import { AuthService } from '../../shared/guard/auth.service';
import { UserService } from '../../services/user/user.service';


@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css'],
  providers: [AuthService, UserService]
})
export class ResetPasswordComponent implements OnInit, AfterViewInit {

  private idUser: number;
  private token: string;
  private existToken: boolean;

  public passRecovery: any;
  public modelAux: any = {} ;

  constructor(private _route: ActivatedRoute,
              private router: Router,
              private authService: AuthService,
             private userService: UserService) {

    this._route.params.forEach((params: Params) => {
          this.idUser = params['id'];
          this.token = params['token'];
    });

    this.passRecovery = {
      idUser: this.idUser,
      token: this.token,
      password: ''
    };
  }

  ngOnInit() {
    this.checkUserToken();
  }

  checkUserToken() {
    this.authService.checkToken(this.idUser, this.token).subscribe(
      response => {
        this.existToken = response.exito;
      },
      error => {
        swal('Error!', 'No existe un codigo de recuperación de contraseña para este usuario.', 'error' )
        .then((willDelete) => {
          this.router.navigate(['login']);
           console.log(<any>error);
        });
      }
    );
  }

  onSubmitFormResetPass() {
    if (this.existToken) {
      this.userService.resetPassword(this.passRecovery).subscribe(
        response => {
          swal('Bien hecho!', 'Tu contraseña se ha restablecido. Ahora inicia sesión', 'success' )
          .then((willDelete) => {
            this.router.navigate(['login']);
          });
        },
        error => {
          swal('Upps!', 'Hubo un problema con su transación. Intentelo más tarde.', 'error' )
          .then((willDelete) => {
            this.router.navigate(['login']);
             console.log(<any>error);
          });
        }
      );
    }else {
      this.router.navigate(['login']);
    }
  }

  ngAfterViewInit() {
    window.scrollTo(0, 0);
    $(function () {
      $('.preloader').fadeOut();
    });
    // tooltip
    $(function() {
      (<any>$('[data-toggle="tooltip"]')).tooltip();
    });

    // popover

    $(function() {
        (<any>$('[data-toggle="popover"]')).popover();
    });

    $('.multi-item-carousel .carousel-item').each(function(){
      let next = $(this).next();
      if (!next.length) {
        next = $(this).siblings(':first');
      }
      next.children(':first-child').clone().appendTo($(this));

      if (next.next().length > 0) {
        next.next().children(':first-child').clone().appendTo($(this));
      }
      // tslint:disable-next-line:one-line
      else {
        $(this).siblings(':first').children(':first-child').clone().appendTo($(this));
      }
    });

    }


}
