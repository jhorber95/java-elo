import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { DashboardinstitucionComponent } from './dashboardinstitucion.component';


const routes: Routes = [{
	path: '',
	data: {
        title: 'Dashboard Institucion',
        urls: [{title: 'Dashboard',url: '/institucion/dashboard'},{title: 'Dashboard Institucion'}]
    },
	component: DashboardinstitucionComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
    	RouterModule.forChild(routes)
    ],
	declarations: [DashboardinstitucionComponent]
})
export class DashboardinstitucionModule { }
