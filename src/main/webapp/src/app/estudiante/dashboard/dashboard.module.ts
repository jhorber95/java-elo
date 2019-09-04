import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { DashboardComponent } from './dashboard.component';


const routes: Routes = [{
	path: '',
	data: {
        title: 'Dashboard Estudiante',
        urls: [{title: 'Dashboard',url: '/estudiante/dashboard'},{title: 'Dashboard Estudiante'}]
    },
	component: DashboardComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule, 
    	RouterModule.forChild(routes)
    ],
	declarations: [DashboardComponent]
})
export class DashboardModule { }