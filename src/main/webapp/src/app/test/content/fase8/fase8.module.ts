import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Fase8RoutingModule } from './fase8-routing';
import { Fase8Component } from './fase8.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    Fase8RoutingModule,
    FormsModule
  ],
  declarations: [
    Fase8Component
    ]
})

export class Fase8Module {

}
