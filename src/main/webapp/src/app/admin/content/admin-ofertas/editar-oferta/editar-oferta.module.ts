import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { EditarOfertaComponent } from './editar-oferta.component';

const routes: Routes = [{
  path: '',
	data: {
      title: 'Editar Oferta',
      urls: [
        { title: 'Admin', url: '/admin/perfil'},
        { title: 'Gestión ofertas', url: '/admin/admin-ofertas'},
        { title: 'Edición ofertas'}
      ]
    },
	component: EditarOfertaComponent
}];

@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    NgbModule.forRoot(),
    RouterModule.forChild(routes)
  ],
  declarations: [EditarOfertaComponent]
})
export class EditarOfertaModule { }
