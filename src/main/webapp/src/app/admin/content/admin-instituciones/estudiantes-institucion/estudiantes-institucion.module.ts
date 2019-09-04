import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { EstudiantesInstitucionComponent } from './estudiantes-institucion.component';

const routes: Routes = [{
  path: '',
	data: {
      title: 'Estudiantes Institución',
      // tslint:disable-next-line:max-line-length
      urls: [
        { title: 'admin', url: '/admin/perfil'},
        { title: 'Instituciones', url: '/admin/admin-instituciones'},
        { title: 'Estudiantes institución'}
      ]
    },
	component: EstudiantesInstitucionComponent
}];


@NgModule({
  imports: [
    FormsModule,
    CommonModule,
    NgbModule.forRoot(),
    RouterModule.forChild(routes),
  ],
  declarations: [EstudiantesInstitucionComponent]
})
export class EstudiantesInstitucionModule { }
