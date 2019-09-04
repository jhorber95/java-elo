import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DetalleuniversidadRoutingModule } from './detalleuniversidad-routing';
import { DetalleuniversidadComponent } from './detalleuniversidad.component';
//google maps
import { AgmCoreModule } from '@agm/core';


@NgModule({
  imports: [
    CommonModule,
    DetalleuniversidadRoutingModule,
    AgmCoreModule.forRoot({apiKey: 'AIzaSyAkvblv6n00cATW9DCy-vfpIl7ZoqfZWl4'})
  ],
  declarations: [
    DetalleuniversidadComponent
    ]
})

export class DetalleuniversidadModule {

}
