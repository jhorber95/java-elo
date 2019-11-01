import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Router} from '@angular/router';
import { AuthService } from '../shared/guard/auth.service';
import * as decode from 'jwt-decode';
import swal from 'sweetalert';
import {TestHistoryService} from '../admin/content/test-history/test-history.service';
import {UserService} from '../services/user/user.service';


@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
    providers: [AuthService]
})
export class LoginComponent implements OnInit, AfterViewInit {

  model: any = {};
  loading = false;
  error = '';
  exito = '';

  public emailToResetPassword: string;


    constructor(private router: Router,
                private authService: AuthService,
                private testService: TestHistoryService,
                private userService: UserService
                ){}

    ngOnInit() {
      if (localStorage.getItem('token') && this.authService.isAuthenticated()) {
        if (sessionStorage.getItem('redirect-test') == 'true') {
          sessionStorage.removeItem('redirect-test');
          this.router.navigate(['/ofertas/guiavocacional']);
        }else {
          this.RedirectRole();
        }
      }
    }

    ngAfterViewInit() {
        $(function() {
            $('.preloader').fadeOut();
        });
        $(function() {
            (<any>$('[data-toggle="tooltip"]')).tooltip();
        });
        $('#to-recover').on('click', function() {
            $('#loginform').slideUp();
            $('#recoverform').fadeIn();
        });
    }

    login() {
      this.loading = true;

      this.authService.Login(this.model.email, this.model.password).subscribe(
        response => {
          this.loading = false;
          if (response) {
            localStorage.setItem('token', response.access_token);
            this.exito = 'Bienvenido, por favor espere...';
            debugger;
            this.saveResultTest();
            if (sessionStorage.getItem('redirect-test') == 'true') {
              sessionStorage.removeItem('redirect-test');
              this.router.navigate(['/ofertas/guiavocacional']);
            }else {
              this.RedirectRole();
            }
            // this.router.navigate(['admin']);
          } else {
            this.error = 'Usuario o contraseña incorrectas';
          }
        },
        error => {
          console.log(error.error.message);
          switch (error.error.message) {
            case 'usuario_no_existe': {
              this.error = 'Este usuario no existe';
              break;
            }
            case 'credenciales_incorrectas': {
              this.error = 'Usuario o contraseña incorrectas';
              break;
            }
            case 'usuario_no_activo': {
              this.error = 'Usuario inactivado por el administrador';
              break;
            }
          }
          this.loading = false;
        }
      );
    }

    async saveResultTest() {
      const _test = JSON.parse(sessionStorage.getItem('stepTest'));
      const _user = await this.saveUserInLocalstorage();

      const entity: any = {
        user: _user,
        test: _test
      };
      debugger;
      this.testService.sendUserTest(entity).subscribe(
        res => {
          console.log(res)
        }, error1 => console.log(error1)
      );
    }

    restartNotification() {
        this.error = '';
    }

    RedirectRole() {

      const RoleAdmin = 'ROLE_ADMINISTRADOR';
      const RoleEstudiante = 'ROLE_ESTUDIANTE';
      const RoleInstitucion = 'ROLE_INSTITUCION';
      const RoleFreelancer = 'ROLE_FREELANCER';
      const token = localStorage.getItem('token');
      const tokenPayload: any = decode(token);
      // for para iterar array autoridades del token
      for (let i = 0; i < tokenPayload.authorities.length; i++) {
        if (tokenPayload.authorities[i] === RoleAdmin) {
          if (this.authService.isAuthenticated() /*|| tokenPayload.authorities !== expectedRole*/) {
            this.router.navigate(['admin/perfil']);
          }

        }

        if (tokenPayload.authorities[i] === RoleFreelancer) {
          if (this.authService.isAuthenticated() /*|| tokenPayload.authorities !== expectedRole*/) {
            this.router.navigate(['freelancer/perfil-freelancer']);
          }

        }

        if (tokenPayload.authorities[i] === RoleInstitucion) {
          if (this.authService.isAuthenticated() /*|| tokenPayload.authorities !== expectedRole*/) {
            this.router.navigate(['institucion/perfil-ins']);
          }

        }

        if (tokenPayload.authorities[i] === RoleEstudiante) {
          if (this.authService.isAuthenticated() /*|| tokenPayload.authorities !== expectedRole*/) {
            this.router.navigate(['estudiante/profile']);
          }

        }

      }

    }

    resetPassword() {
      console.log(this.emailToResetPassword);
      this.authService.resetPassword(this.emailToResetPassword).subscribe(
        response => {
         // console.log(response);
          swal('¡Bien hecho!', 'Revisa tu bandeja de entrada y sigue las instrucciones.', 'success' )
          .then((willDelete) => {
            if (willDelete) {
                $('#recoverform').slideUp();
                $('#loginform').fadeIn();
            }
          });
        },
        error => {
          swal('upss!', 'No existe un usuario registrado con el E-mail ingresado', 'error' );
          console.log(<any>error);
        }
      );

    }

    saveUserInLocalstorage() {
      const token = localStorage.getItem('token');
      const tokenPayload: any = decode(token);
      const email = tokenPayload.user_name;
      return new Promise((resolve, reject) => {
        this.userService.getUserData(email).subscribe(
          response => {
            if (response) {
              localStorage.setItem('user', JSON.stringify(response));
              resolve(response);
            } else {
              this.router.navigate(['/login']);
            }
          },
          error => {
            resolve(error);
            console.log(<any>error);
          }
        );
      });

    }

}
