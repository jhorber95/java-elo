import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Fase2RoutingModule } from './fase2-routing';
import { Fase2Component } from './fase2.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    Fase2RoutingModule,
    FormsModule
  ],
  declarations: [
    Fase2Component
    ]
})

export class Fase2Module {

}
