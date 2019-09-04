import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { AdminCreateInstitucionComponent } from './admin-create-institucion.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// datatable
import { DataTablesModule } from 'angular-datatables';



const routes: Routes = [{
  path: '',
	data: {
      title: 'Agregar Nueva Institución',
      urls: [{title: 'Admin', url: '/admin'},{ title: 'Instituciones'}, { title: 'Agregar Instituciones'}]
    },
	component: AdminCreateInstitucionComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      DataTablesModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [AdminCreateInstitucionComponent]
})
export class AdminCreateInstitucionModule { }
