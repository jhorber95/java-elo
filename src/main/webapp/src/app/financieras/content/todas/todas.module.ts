import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TodasRoutingModule } from './todas-routing.module';
import { TodasComponent } from './todas.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    TodasRoutingModule
  ],
  declarations: [
  	TodasComponent
  	]
})

export class TodasModule {

}
