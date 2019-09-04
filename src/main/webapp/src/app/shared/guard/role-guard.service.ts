import { Injectable } from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import { AuthService } from './auth.service';
import * as decode from 'jwt-decode';
import { UserService } from '../../services/user/user.service'
import {Observable} from "rxjs/Observable";

@Injectable()
export class RoleGuardService implements CanActivate {
  RolesUser: any[] = [];
  error = '';
  private vista: boolean;

  constructor(public auth: AuthService,
              public router: Router,
              private userService: UserService) {}

  public canActivate(route: ActivatedRouteSnapshot, state:RouterStateSnapshot):Observable<boolean>|boolean{

    if (localStorage.getItem('token') && this.auth.isAuthenticated()) {
      const Roles = route.data.roles;
      const token = localStorage.getItem('token');
      const tokenPayload: any = decode(token);
      const email = tokenPayload.user_name;
      return this.userService.getUserData(email).map(
        response => {
          if(response){
            console.log("Espere un momento...");
            localStorage.setItem('user', JSON.stringify(response));
            this.RolesUser = response.roles;
            // for para iterar array autoridades del token
            for (let i = 0; i < this.RolesUser.length; i++) {
              for (let j = 0; j < Roles.length; j++) {

                if (this.RolesUser[i].nombre === Roles[j]) {
                  //si retorna false la condicion no se cumple y retorna true dejandolo entrar al panel
                  if (!this.auth.isAuthenticated() /*|| tokenPayload.authorities !== expectedRole*/) {
                    this.router.navigate(['/login']);
                    return false;
                  }
                  return true;
                }
              }

            }
            this.router.navigate(['/login']);
            return false;
          } else {
            this.error= 'No tienes permiso para esta vista';
            console.log(this.error);
            alert('error cargando api');
            this.router.navigate(['/login']);
            return false;
          }
        },
        error => {
          this.error = 'No tienes permiso para esta vista';
          console.log(this.error);
          var errorMessage = <any>error;
          console.log(errorMessage);
          this.router.navigate(['/login']);
          return false;
        }
      );

    } else {
      this.router.navigate(['login']);
      return false;
    }
  }

}
