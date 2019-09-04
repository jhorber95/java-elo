import { Component, OnInit } from '@angular/core';
import { AuthService } from '../shared/guard/auth.service';
import swal from "sweetalert";
import {Router} from "@angular/router";

@Component({
    selector: 'app-freelancer',
    templateUrl: './freelancer.component.html',
    styleUrls: ['./freelancer.component.scss'],
    providers: [AuthService]
})
export class FreelancerComponent implements OnInit {
	public dataUser;
	public rolUser: any [];
	public nameRoles: any [] = [];
  constructor(private router: Router, private _authService: AuthService) {
    this.dataUser = this._authService.getAllDataUser();

  }

  ngOnInit() {
  	this.getRoles();

  }
   getRoles(){
      this.rolUser = this.dataUser.roles;
      // console.log(this.rolUser);

      for (var i = 0 ; i < this.rolUser.length; i++) {
          switch (this.rolUser[i]['id']) {
              case 1:
                  this.nameRoles.push({nombre: "Administrador",url:"/admin"});
                  break;
              case 2:
                  this.nameRoles.push({nombre: "Estudiante",url:"/estudiante"});
                  break;
              case 3:
                  this.nameRoles.push({nombre: "Freelancer",url:"/freelancer"});
                  break;
              case 4:
                  this.nameRoles.push({nombre: "Publicador",url:"/publicador"});
                  break;
              case 5:
                  this.nameRoles.push({nombre: "Institución"});
                  break;
          }
      }
       //console.log(this.nameRoles);
  }


}
