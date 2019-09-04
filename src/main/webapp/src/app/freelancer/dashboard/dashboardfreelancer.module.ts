import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { DashboardfreelancerComponent } from './dashboardfreelancer.component';


const routes: Routes = [{
	path: '',
	data: {
        title: 'Dashboard Freelancer',
        urls: [{title: 'Dashboard',url: '/freelancer/dashboard'},{title: 'Dashboard Freelancer'}]
    },
	component: DashboardfreelancerComponent
}];

@NgModule({
	imports: [
    	FormsModule,
    	CommonModule,
    	RouterModule.forChild(routes)
    ],
	declarations: [DashboardfreelancerComponent]
})
export class DashboardfreelancerModule { }
