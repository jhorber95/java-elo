import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { AdminPublicidadComponent } from './admin-publicidad.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// datatable
import { DataTablesModule } from 'angular-datatables';



const routes: Routes = [{
  path: '',
	data: {
      title: 'Gestionar Publicidad',
      urls: [{title: 'Admin', url: '/admin'},{ title: 'Publicidad'}, { title: 'Test'}]
    },
	component: AdminPublicidadComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      DataTablesModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [AdminPublicidadComponent]
})
export class AdminPublicidadModule { }
