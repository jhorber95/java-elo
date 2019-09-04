import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// import {NgxPaginationModule} from 'ngx-pagination';

import { BusquedaRoutingModule } from './busqueda-routing.module';
import { BusquedaComponent } from './busqueda.component';

import { FormsModule } from '@angular/forms';
import { CommonsModule } from '../../../commons/commons.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    BusquedaRoutingModule,
   // NgxPaginationModule,
    CommonsModule
  ],
  declarations: [
  	BusquedaComponent
  	]
})

export class BusquedaModule {

}
