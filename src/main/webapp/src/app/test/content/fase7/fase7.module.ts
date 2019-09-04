import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Fase7RoutingModule } from './fase7-routing';
import { Fase7Component } from './fase7.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    Fase7RoutingModule,
    FormsModule
  ],
  declarations: [
    Fase7Component
    ]
})

export class Fase7Module {

}
