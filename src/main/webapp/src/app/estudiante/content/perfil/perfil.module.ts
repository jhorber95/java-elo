import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { PerfilComponent } from './perfil.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// import { DataTablesModule } from 'angular-datatables';
// import { CustomMinDirective } from '../../../directives/custom-min.directive';
//import { CustomMaxDirective } from '../../../directives/custom-max.directive';


const routes: Routes = [{
	path: '',
	data: {
      title: 'Mi perfil',
      urls: [{title: 'Estudiante', url: '/estudiante'}, {title: 'Mi perfil'}]
    },
	component: PerfilComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [
      PerfilComponent

    ]
})
export class PerfilModule { }
