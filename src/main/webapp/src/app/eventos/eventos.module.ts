import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EventosRoutingModule } from './eventos-routing.module';
import { EventosComponent } from './eventos.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { PartnersComponent } from './partners/partners.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    EventosRoutingModule,
    FormsModule
  ],
  declarations: [
    EventosComponent,
    HeaderComponent,
    FooterComponent,
    PartnersComponent
  ]
})
export class EventosModule {

}
