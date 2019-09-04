import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { OfertasComponent } from './ofertas.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// datatable



const routes: Routes = [{
  path: '',
	data: {
      title: 'Gestion de cursos',
      urls: [{title: 'Institucion', url: '/institucion'}, { title: 'Gestion de cursos'}]
    },
	component: OfertasComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [OfertasComponent]
})
export class OfertasInstitucionModule { }
