import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../shared/guard/auth.service';


@Component({
  selector: 'ap-sidebar',
  templateUrl: './sidebar.component.html'
})
export class SidebarComponent {
    //this is for the open close
    isActive: boolean = true;
    showMenu: string = '';
    showSubMenu: string = '';
    @Input() public dataUser;
    @Input() public nameRoles:any [];

    constructor(private router: Router, private authService: AuthService ){   
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

