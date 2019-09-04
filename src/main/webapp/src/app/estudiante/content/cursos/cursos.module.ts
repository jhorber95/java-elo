import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { CursosComponent } from './cursos.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DataTablesModule } from 'angular-datatables';
import { CustomMinDirective } from '../../../directives/custom-min.directive';
import { CustomMaxDirective } from '../../../directives/custom-max.directive';


const routes: Routes = [{
	path: '',
	data: {
      title: 'Mis cursos',
      urls: [{title: 'Estudiante', url: '/estudiante'}, {title: 'Cursos'}]
    },
	component: CursosComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      DataTablesModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [
      CursosComponent,
      CustomMinDirective,
      CustomMaxDirective
    ]
})
export class CursosModule { }
