import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EstudianteRoutingModule } from './estudiante-routing.module';
import { EstudianteComponent } from './estudiante.component';
import { SIDEBAR_TOGGLE_DIRECTIVES } from './navigation/sidebar.directive';
import { NavigationComponent } from './navigation/header-navigation/navigation.component';
import { SidebarComponent } from './navigation/sidebar/sidebar.component';
import { BreadcrumbComponent } from './navigation/breadcrumb/breadcrumb.component';
import { FormsModule } from '@angular/forms';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// import { ToastrModule } from 'ngx-toastr';
// import {CustomMinDirective} from '../directives/custom-min.directive';
// import {CustomMaxDirective} from '../directives/custom-max.directive';

@NgModule({
    imports: [
        CommonModule,
       // BrowserAnimationsModule,
        EstudianteRoutingModule,
        FormsModule,
       // ToastrModule.forRoot()
    ],
    declarations: [
        EstudianteComponent,
        NavigationComponent,
        BreadcrumbComponent,
        SidebarComponent,
        SIDEBAR_TOGGLE_DIRECTIVES,
    ],
})
export class EstudianteModule { }
