import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../shared/guard/auth.service';


@Component({
    selector: 'app-layout',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {
	public dataUser;

    constructor(public router: Router, private _authService: AuthService) {
    	this.dataUser = this._authService.getAllDataUser();
    }

    ngOnInit() {  }

}
