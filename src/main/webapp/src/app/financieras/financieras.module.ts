import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FinancierasRoutingModule } from './financieras-routing.module';
import { FinancierasComponent } from './financieras.component';

import { FormsModule } from '@angular/forms';
import {PartialsModule} from '../partials/partials.module';



@NgModule({
  imports: [
    CommonModule,
    FinancierasRoutingModule,
    FormsModule,
    PartialsModule
  ],
  declarations: [
    FinancierasComponent,
  ]
})
export class FinancierasModule {

}
