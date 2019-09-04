import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../shared/guard/auth.service';

@Component({
    selector: 'app-institucipon',
    templateUrl: './institucion.component.html',
    styleUrls: ['./institucion.component.scss']
})
export class InstitucionComponent implements OnInit {

	public dataUser;
	public rolUser: any [];
	public nameRoles: any [] = [];

    constructor(public router: Router, private _authService: AuthService) { 
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
