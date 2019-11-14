import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';


import { AboutUsComponent } from './about-us.component';
import {PartialsModule} from '../partials/partials.module';


const routes: Routes = [{
  path: '', 	component: AboutUsComponent
}];
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbModule.forRoot(),
    RouterModule.forChild(routes),
    PartialsModule
  ],
  declarations: [
    AboutUsComponent,
  ]
})
export class AboutUsModule { }
