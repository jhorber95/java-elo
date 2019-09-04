import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { AdminFinanciacionesComponent } from './admin-financiaciones.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// datatable
import { DataTablesModule } from 'angular-datatables';



const routes: Routes = [{
  path: '',
	data: {
      title: 'Gestion de Financiamiento',
      urls: [
        { title: 'admin', url: '/admin/perfil'},
        { title: 'Gestion de financiaciones'}
      ]
    },
	component: AdminFinanciacionesComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      DataTablesModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [AdminFinanciacionesComponent],
  providers: []
})
export class AdminFinanciacionesModule { }
