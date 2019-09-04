import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Fase3RoutingModule } from './fase3-routing';
import { Fase3Component } from './fase3.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    Fase3RoutingModule,
    FormsModule
  ],
  declarations: [
    Fase3Component
    ]
})

export class Fase3Module {

}
