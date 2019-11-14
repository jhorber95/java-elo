import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OfertasRoutingModule } from './ofertas-routing.module';
import { OfertasComponent } from './ofertas.component';

import { FormsModule } from '@angular/forms';
import {PartialsModule} from '../partials/partials.module';

@NgModule({
  imports: [
    CommonModule,
    OfertasRoutingModule,
    FormsModule,
    PartialsModule
  ],
  declarations: [
    OfertasComponent,
  ]
})
export class OfertasModule {

}
