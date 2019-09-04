import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { InscripcionesInstiComponent } from './inscripciones-insti.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// datatable



const routes: Routes = [{
  path: '',
	data: {
      title: 'Gestión de inscripciones',
      urls: [{title: 'Institucion', url: '/institucion'}, { title: 'Inscripciones'}]
    },
	component: InscripcionesInstiComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [InscripcionesInstiComponent]
})
export class IncripcionesInstitucionModule { }
