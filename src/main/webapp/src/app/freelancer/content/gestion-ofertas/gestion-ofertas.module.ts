import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { GestionOfertasComponent } from './gestion-ofertas.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// datatable
import { DataTablesModule } from 'angular-datatables';



const routes: Routes = [{
  path: '',
	data: {
      title: 'Gestion de cursos',
      urls: [{title: 'Freelancer', url: '/freelancer'}, { title: 'Gestion de cursos'}]
    },
	component: GestionOfertasComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      DataTablesModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [GestionOfertasComponent]
})
export class GestionOfertasModule { }
