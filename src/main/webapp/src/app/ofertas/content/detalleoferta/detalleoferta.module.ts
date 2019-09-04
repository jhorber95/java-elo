import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DetalleofertaRoutingModule } from './detalleoferta-routing';
import { DetalleofertaComponent } from './detalleoferta.component';



@NgModule({
  imports: [
    CommonModule,
    DetalleofertaRoutingModule
  ],
  declarations: [
    DetalleofertaComponent
    ]
})

export class DetalleofertaModule {

}
