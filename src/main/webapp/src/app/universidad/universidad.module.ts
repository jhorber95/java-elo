import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {UniversidadRoutingModule} from './universidad-routing.module';
import {UniversidadComponent} from './universidad.component';
import {FormsModule} from '@angular/forms';
import {PartialsModule} from '../partials/partials.module';


@NgModule({
  imports: [
    CommonModule,
    UniversidadRoutingModule,
    FormsModule,
    PartialsModule
  ],
  declarations: [
    UniversidadComponent,
  ]
})
export class UniversidadModule {

}
