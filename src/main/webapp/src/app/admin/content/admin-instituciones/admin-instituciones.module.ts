import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { AdminInstitucionesComponent } from './admin-instituciones.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// datatable
import { DataTablesModule } from 'angular-datatables';



const routes: Routes = [{
  path: '',
	data: {
      title: 'Gestion de Instituciones',
      urls: [{title: 'admin', url: '/'}, {titulo: 'Instituciones'}, { title: 'Gestion de instituciones'}]
    },
	component: AdminInstitucionesComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      DataTablesModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [AdminInstitucionesComponent],
  providers: []
})
export class AdminInstitucionesModule { }
