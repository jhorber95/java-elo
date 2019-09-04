import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CrearEventoComponent} from './crear-evento.component'
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// datatable



const routes: Routes = [{
  path: '',
	data: {
      title: 'Registro evento',
      urls: [
	      { title: 'Institucion', url: '/institucion' },{ title: 'Eventos'}
      ]
    },
	component: CrearEventoComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
  declarations: [CrearEventoComponent]
})
export class CrearEventoModule { }
