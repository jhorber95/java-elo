import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {InstitucionRoutingModule} from './institucion-routing.module';
import { InstitucionComponent } from './institucion.component';
import { SIDEBAR_TOGGLE_DIRECTIVES } from './navigation/sidebar.directive';
import { NavigationComponent } from './navigation/header-navigation/navigation.component';
import { SidebarComponent } from './navigation/sidebar/sidebar.component';
import { BreadcrumbComponent } from './navigation/breadcrumb/breadcrumb.component';
import { FormsModule } from '@angular/forms';


@NgModule({
    imports: [
        CommonModule,
        InstitucionRoutingModule,
        FormsModule
    ],
    declarations: [
        InstitucionComponent,
        NavigationComponent,
        BreadcrumbComponent,
        SidebarComponent,
        SIDEBAR_TOGGLE_DIRECTIVES
    ],
})
export class InstitucionModule { }
