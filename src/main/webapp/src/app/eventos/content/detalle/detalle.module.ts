import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DetalleRoutingModule } from './detalle-routing';
import { DetalleComponent } from './detalle.component';



@NgModule({
  imports: [
    CommonModule,
    DetalleRoutingModule
  ],
  declarations: [
    DetalleComponent
    ]
})

export class DetalleModule {

}
