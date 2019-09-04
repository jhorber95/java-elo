import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { ViewOfertaInstComponent } from './view-oferta-inst.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

const routes: Routes = [{
  path: '',
	data: {
      title: 'Detalle Oferta',
      urls: [{title: 'Institucion', url: '/institucion'}, { title: 'Detalle oferta'}]
    },
	component: ViewOfertaInstComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [ViewOfertaInstComponent]
})
export class ViewOfertaInstitucionModule { }
