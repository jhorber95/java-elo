import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { PerfilFreelancerComponent } from './perfil-freelancer.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';



const routes: Routes = [{
  path: '',
	data: {
      title: 'Mi perfil',
      urls: [{title: 'Inicio', url: '/freelancer'}, { title: 'Mi perfil'}]
    },
	component: PerfilFreelancerComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [PerfilFreelancerComponent]
})
export class PerfilFreelancerModule { }
