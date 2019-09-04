import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { NuevaOfertaComponent } from './nueva-oferta.component';


const routes: Routes = [{
  path: '',
	data: {
      title: 'Nueva Oferta',
      urls: [
        {title: 'Admin', url: '/admin/perfil'},
        { title: 'Gestión ofertas', url: '/admin/admin-ofertas'},
        { title: 'Registro ofertas'}
      ]
    },
	component: NuevaOfertaComponent
}];
@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    NgbModule.forRoot(),
    RouterModule.forChild(routes)
  ],
  declarations: [NuevaOfertaComponent]
})
export class NuevaOfertaModule { }
