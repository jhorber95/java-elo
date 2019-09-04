import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Fase5RoutingModule } from './fase5-routing';
import { Fase5Component } from './fase5.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    Fase5RoutingModule,
    FormsModule
  ],
  declarations: [
    Fase5Component
    ]
})

export class Fase5Module {

}
