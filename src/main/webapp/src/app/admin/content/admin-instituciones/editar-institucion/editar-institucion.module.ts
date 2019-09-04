import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AgmCoreModule } from '@agm/core';


import { EditarInstitucionComponent } from './editar-institucion.component';

const routes: Routes = [{
  path: '',
	data: {
      title: 'Editar Institución',
      urls: [
        { title: 'admin', url: '/admin/perfil'},
        { title: 'Instituciones', url: '/admin/admin-instituciones'},
        { title: 'Editar institución'}]
    },
	component: EditarInstitucionComponent
}];


@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    NgbModule.forRoot(),
    RouterModule.forChild(routes),
    AgmCoreModule.forRoot({apiKey: 'AIzaSyAkvblv6n00cATW9DCy-vfpIl7ZoqfZWl4'})
  ],
  declarations: [ EditarInstitucionComponent ]
})
export class EditarInstitucionModule { }
