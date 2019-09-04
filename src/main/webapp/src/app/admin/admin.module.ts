import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { SIDEBAR_TOGGLE_DIRECTIVES } from './navigation/sidebar.directive';
import { NavigationComponent } from './navigation/header-navigation/navigation.component';
import { SidebarComponent } from './navigation/sidebar/sidebar.component';
import { BreadcrumbComponent } from './navigation/breadcrumb/breadcrumb.component';
//import admin-usuarios services
import { AdminService } from './services/admin.service';
import { FormsModule } from '@angular/forms';

@NgModule({
    imports: [
      CommonModule,
      AdminRoutingModule,
      FormsModule
    ],
    declarations: [
        AdminComponent,
        NavigationComponent,
        BreadcrumbComponent,
        SidebarComponent,
        SIDEBAR_TOGGLE_DIRECTIVES
    ],
    providers: [AdminService]
})
export class PagesModule { }
