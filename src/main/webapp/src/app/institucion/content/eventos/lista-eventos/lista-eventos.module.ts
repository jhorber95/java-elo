import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListaEventosComponent } from './lista-eventos.component';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DataTablesModule } from 'angular-datatables';

const routes: Routes = [{
  path: '',
	data: {
      title: 'Lista de Eventos',
      urls: [{title: 'Institucion', url: '/institucion'}, { title: 'Eventos'}]
    },
	component: ListaEventosComponent
}];


@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    DataTablesModule,
    NgbModule.forRoot(),
    RouterModule.forChild(routes)
  ],
  declarations: [ListaEventosComponent]
})
export class ListaEventosModule { }
