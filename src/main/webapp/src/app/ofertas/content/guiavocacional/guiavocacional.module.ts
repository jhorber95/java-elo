import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GuiavocacionalRoutingModule } from './guiavocacional-routing.module';
import { GuiavocacionalComponent } from './guiavocacional.component';

import { FormsModule } from '@angular/forms';
import { CommonsModule } from '../../../commons/commons.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    GuiavocacionalRoutingModule,
    CommonsModule
  ],
  declarations: [
    GuiavocacionalComponent
  	]
})

export class GuiavocacionalModule {

}
