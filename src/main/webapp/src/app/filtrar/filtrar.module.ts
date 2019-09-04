import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FiltrarRoutingModule } from './filtrar-routing.module';
import { FiltrarComponent } from './filtrar.component';
import { PartnersComponent } from './partners/partners.component';
import { FooterComponent } from './footer/footer.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { FormsModule } from '@angular/forms';
import { CommonsModule } from '../commons/commons.module';

@NgModule({
  imports: [
    CommonModule,
    FiltrarRoutingModule,
    FormsModule,
    CommonsModule,
    NgbModule.forRoot()
  ],
  declarations: [
    FiltrarComponent,
    PartnersComponent,
    FooterComponent
  ]
})
export class FiltrarModule {

}
