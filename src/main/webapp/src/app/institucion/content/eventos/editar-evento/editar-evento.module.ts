import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditarEventoComponent } from './editar-evento.component';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

const routes: Routes = [{
  path: '',
	data: {
      title: 'Detalle Evento',
      urls: [
	      {
	      	title: 'Institucion', url: '/institucion'
	      },{
	      	title: 'Eventos'
	      }
      ]
    },
	component: EditarEventoComponent
}];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbModule.forRoot(),
    RouterModule.forChild(routes)

  ],
  declarations: [EditarEventoComponent]
})
export class EditarEventoModule { }
