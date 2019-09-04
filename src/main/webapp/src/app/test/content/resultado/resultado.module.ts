import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ResultadoRoutingModule } from './resultado-routing';
import { ResultadoComponent } from './resultado.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    ResultadoRoutingModule,
    FormsModule
  ],
  declarations: [
    ResultadoComponent
    ]
})

export class ResultadoModule {

}
