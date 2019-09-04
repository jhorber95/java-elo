import { Router } from '@angular/router';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { User } from '../models/user-singup.interface';
import { SignupService } from '../services/signup/signup-user.service';
import {AuthService} from "../shared/guard/auth.service";

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.css'],
    providers: [SignupService]
})
export class SignupComponent implements OnInit, AfterViewInit {

    public user: User;
    public message_success: string;
    public message_error: string;
    terminos: any = {};

    constructor(private router: Router, private _signUpService: SignupService,
                private authService: AuthService) {}

    ngOnInit() {
        // initialize user
        this.user = {
            nombres : '',
            apellidos : '',
            email : '',
            password : ''
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

    onFormSubmit() {
        //console.log(this.user);
        this.createUser(this.user);
    }

    loading = false;
    createUser(user: User) {
      this.loading = true;
        this._signUpService.createUser(user).subscribe(
            response => {
                if(response.exito) {
                   this.message_success = 'Registro exitoso';
                  this.authService.Login(this.user.email, this.user.password).subscribe(
                    response => {
                      this.loading = false;
                      if(response){
                        localStorage.setItem('token', response.access_token);
                        this.router.navigate(['/login']);
                      } else {
                      }
                    },
                    error => {
                      console.log(error.error.message);
                      switch (error.error.message){
                        case 'usuario_no_existe': {
                          console.log('Este usuario no existe');
                          break;
                        }
                        case 'credenciales_incorrectas': {
                          console.log('Usuario o contraseña incorrectas');
                          break;
                        }
                        case 'usuario_no_activo': {
                          console.log('Usuario inactivado por el administrador');
                          break;
                        }
                      }
                      this.loading = false;
                    }
                  );
                }else {
                    this.message_error = 'Lo siento, algo a salido mal. Intenlo más tarde';
                   // console.log(response);
                }
            },
            error => {
                this.loading = false;
                 this.message_error = 'Ya existe un usuario con el correo: ';
                // console.log(<any>error);
            }
        );
    }

    restartNotification() {
        this.message_success = '';
        this.message_error = '';
        this.redirectTo();

    }

    redirectTo() {
        this.router.navigate(['login']);
    }

}
