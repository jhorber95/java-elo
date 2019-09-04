import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OfertasRoutingModule } from './ofertas-routing.module';
import { OfertasComponent } from './ofertas.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { PartnersComponent } from './partners/partners.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    OfertasRoutingModule,
    FormsModule
  ],
  declarations: [
    OfertasComponent,
    HeaderComponent,
    FooterComponent,
    PartnersComponent
  ]
})
export class OfertasModule {

}
