import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import { AdminOfertaComponent } from './admin-oferta.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
// datatable
import { DataTablesModule } from 'angular-datatables';




const routes: Routes = [{
  path: '',
	data: {
      title: 'Gestion de ofertas',
      urls: [{title: 'admin', url: '/'}, { title: 'Gestion de ofertas'}]
    },
	component: AdminOfertaComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
      DataTablesModule,
      NgbModule.forRoot(),
    	RouterModule.forChild(routes)
    ],
	declarations: [AdminOfertaComponent],
  providers: []
})
export class AdminOfertaModule { }
