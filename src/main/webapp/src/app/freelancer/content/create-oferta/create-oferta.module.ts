import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { CreateOfertaComponent } from './create-oferta.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// datatable
import { DataTablesModule } from 'angular-datatables';



const routes: Routes = [{
  path: '',
	data: {
      title: 'Nueva Oferta',
      urls: [{title: 'Freelancer', url: '/freelancer'}, { title: 'Registro ofertas'}]
    },
	component: CreateOfertaComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      DataTablesModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [CreateOfertaComponent]
})
export class CreateOfertaModule { }
