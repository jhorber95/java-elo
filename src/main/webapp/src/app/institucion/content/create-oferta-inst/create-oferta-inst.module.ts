import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { CreateOfertaInstComponent } from './create-oferta-inst.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// datatable



const routes: Routes = [{
  path: '',
	data: {
      title: 'Registro Oferta',
      urls: [{title: 'Institucion', url: '/institucion'}, { title: 'Nueva oferta'}]
    },
	component: CreateOfertaInstComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [CreateOfertaInstComponent]
})
export class CreateOfertasInstitucionModule { }
