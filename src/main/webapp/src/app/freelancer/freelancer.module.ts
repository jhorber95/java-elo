import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {FreelancerRoutingModule} from './freelancer-routing.module';
import { FreelancerComponent } from './freelancer.component';
import { SIDEBAR_TOGGLE_DIRECTIVES } from './navigation/sidebar.directive';
import { NavigationComponent } from './navigation/header-navigation/navigation.component';
import { SidebarComponent } from './navigation/sidebar/sidebar.component';
import { BreadcrumbComponent } from './navigation/breadcrumb/breadcrumb.component';
import { FormsModule } from '@angular/forms';



@NgModule({
    imports: [
        CommonModule,
        FreelancerRoutingModule,
        FormsModule
    ],
    declarations: [
        FreelancerComponent,
        NavigationComponent,
        BreadcrumbComponent,
        SidebarComponent,
        SIDEBAR_TOGGLE_DIRECTIVES
    ],
})
export class FreelancerModule { }
