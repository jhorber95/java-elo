import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Fase6RoutingModule } from './fase6-routing';
import { Fase6Component } from './fase6.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    Fase6RoutingModule,
    FormsModule
  ],
  declarations: [
    Fase6Component
    ]
})

export class Fase6Module {

}
