import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EventosRoutingModule } from './eventos-routing.module';
import { EventosComponent } from './eventos.component';

import { FormsModule } from '@angular/forms';
import {PartialsModule} from '../partials/partials.module';

@NgModule({
  imports: [
    CommonModule,
    EventosRoutingModule,
    FormsModule,
    PartialsModule
  ],
  declarations: [
    EventosComponent,
  ]
})
export class EventosModule {

}
