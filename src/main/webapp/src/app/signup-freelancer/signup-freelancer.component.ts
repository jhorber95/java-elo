import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../models/user-singup.interface';
import { SignupService } from '../services/signup/signup-user.service';
import {AuthService} from "../shared/guard/auth.service";

@Component({
  selector: 'app-signup-freelancer',
  templateUrl: './signup-freelancer.component.html',
  styleUrls: ['./signup-freelancer.component.css'],
  providers: [SignupService]
})
export class SignupFreelancerComponent implements OnInit, AfterViewInit {

  public user: User;
  public message_success: string;
  public message_error: string;
  terminos: any = {};

  constructor(private router: Router, private _signupService: SignupService,
              private  authService: AuthService) { }

  ngOnInit() {
	 	// initialize user
    this.user = {
        nombres : '',
        apellidos : '',
        email : '',
        password : ''
    }
  }
  onFormSubmit() {
        // console.log(this.user);
        this.createUser(this.user);
    }

  loading = false;
   createUser(user: User) {
     this.loading = true;
        this._signupService.createFreelancer(user).subscribe(
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
                  console.log(<any>error);
            }
        );
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

  restartNotification() {
	this.message_success = '';
	this.message_error = '';
	this.redirectTo();

    }

   redirectTo() {
        this.router.navigate(['login']);
    }

}
