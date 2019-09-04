import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Fase4RoutingModule } from './fase4-routing';
import { Fase4Component } from './fase4.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    Fase4RoutingModule,
    FormsModule
  ],
  declarations: [
    Fase4Component
    ]
})

export class Fase4Module {

}
