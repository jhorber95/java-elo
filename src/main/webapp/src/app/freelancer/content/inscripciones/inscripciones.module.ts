import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { InscripcionesComponent } from './inscripciones.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// datatable
import { DataTablesModule } from 'angular-datatables';



const routes: Routes = [{
  path: '',
	data: {
      title: 'Gestion de Inscripciones',
      urls: [{title: 'Freelancer', url: '/freelancer'}, { title: 'Inscripciones'}]
    },
	component: InscripcionesComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      DataTablesModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [InscripcionesComponent]
})
export class IncripcionesModule { }
