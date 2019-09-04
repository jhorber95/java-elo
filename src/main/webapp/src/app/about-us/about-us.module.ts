import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';


import { AboutUsComponent } from './about-us.component';
import { HeaderComponent } from './header/header.component';
import { PartnersComponent } from './partners/partners.component';
import { FooterComponent } from './footer/footer.component';


const routes: Routes = [{
  path: '', 	component: AboutUsComponent
}];
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgbModule.forRoot(),
    RouterModule.forChild(routes)
  ],
  declarations: [
    AboutUsComponent,
    HeaderComponent,
    PartnersComponent,
    FooterComponent]
})
export class AboutUsModule { }
