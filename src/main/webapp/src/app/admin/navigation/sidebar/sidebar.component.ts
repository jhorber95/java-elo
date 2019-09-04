import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../../shared/guard/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'ap-sidebar',
  templateUrl: './sidebar.component.html'
})
export class SidebarComponent implements OnInit {
    //this is for the open close
    isActive: boolean = true;
    showMenu: string = '';
    showSubMenu: string = '';
  public dataUser;

  constructor(private authService: AuthService,
              private router: Router){}

  ngOnInit(){
    this.getDataUser();
  }

  //Obtener datos del usuario de la sessionStorage
  getDataUser(){
    this.dataUser = JSON.parse(localStorage.getItem('user'));
  }

    addExpandClass(element: any) {
        if (element === this.showMenu) {
            this.showMenu = '0';
        } else {
            this.showMenu = element;
        }
    }
    addActiveClass(element: any) {
        if (element === this.showSubMenu) {
            this.showSubMenu = '0';
        } else {
            this.showSubMenu = element;
        }
    }
    eventCalled() {
        this.isActive = !this.isActive;

    }

  salir(){
    this.authService.Logout();
    this.router.navigate(['/']);
  }

}

