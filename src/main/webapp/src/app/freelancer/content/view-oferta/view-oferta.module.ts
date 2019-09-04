import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { ViewOfertaComponent } from './view-oferta.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';



const routes: Routes = [{
  path: '',
	data: {
      title: 'Detalle oferta',
      urls: [{title: 'Inicio', url: '/freelancer'}, { title: 'Detalle oferta'}]
    },
	component: ViewOfertaComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [ViewOfertaComponent]
})
export class ViewOfertaModule { }
