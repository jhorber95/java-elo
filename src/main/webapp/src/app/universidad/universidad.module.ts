import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UniversidadRoutingModule } from './universidad-routing.module';
import { UniversidadComponent } from './universidad.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { PartnersComponent } from './partners/partners.component';

import { FormsModule } from '@angular/forms';



@NgModule({
  imports: [
    CommonModule,
    UniversidadRoutingModule,
    FormsModule
  ],
  declarations: [
    UniversidadComponent,
    HeaderComponent,
    FooterComponent,
    PartnersComponent
  ]
})
export class UniversidadModule {

}
