import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FinancierasRoutingModule } from './financieras-routing.module';
import { FinancierasComponent } from './financieras.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { PartnersComponent } from './partners/partners.component';

import { FormsModule } from '@angular/forms';



@NgModule({
  imports: [
    CommonModule,
    FinancierasRoutingModule,
    FormsModule
  ],
  declarations: [
    FinancierasComponent,
    HeaderComponent,
    FooterComponent,
    PartnersComponent
  ]
})
export class FinancierasModule {

}
