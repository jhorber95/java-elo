import { Injectable } from '@angular/core';
import {Router, CanActivateChild, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import { AuthService } from './auth.service';
import {Observable} from "rxjs/Observable";

@Injectable()
export class AuthGuard implements CanActivateChild {

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
    if (this.auth.isAuthenticated()) {
      return true;
    }
    this.router.navigate(['login']);
    return false;
  }

  constructor(private router: Router,
              public auth: AuthService) { }


}
