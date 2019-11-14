import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GuiavocacionalRoutingModule } from './guiavocacional-routing.module';
import { GuiavocacionalComponent } from './guiavocacional.component';

import { FormsModule } from '@angular/forms';
import {PartialsModule} from '../partials/partials.module';

@NgModule({
  imports: [
    CommonModule,
    GuiavocacionalRoutingModule,
    FormsModule,
    PartialsModule
  ],
  declarations: [
    GuiavocacionalComponent,
  ]
})
export class GuiavocacionalModule {

}
