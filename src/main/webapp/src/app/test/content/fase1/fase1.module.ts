import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Fase1RoutingModule } from './fase1-routing';
import { Fase1Component } from './fase1.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    Fase1RoutingModule,
    FormsModule
  ],
  declarations: [
    Fase1Component
    ]
})

export class Fase1Module {

}
