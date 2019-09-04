import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { PerfilInstiComponent } from './perfil-insti.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// google maps
import { AgmCoreModule } from '@agm/core';

const routes: Routes = [{
  path: '',
	data: {
      title: 'Perfil',
      urls: [{title: 'Institucion', url: '/institucion'}, { title: 'Mi Perfil'}]
    },
	component: PerfilInstiComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes),
      AgmCoreModule.forRoot({apiKey: 'AIzaSyAkvblv6n00cATW9DCy-vfpIl7ZoqfZWl4'})
    ],
	declarations: [PerfilInstiComponent]
})
export class PerfilInstitucionModule { }
