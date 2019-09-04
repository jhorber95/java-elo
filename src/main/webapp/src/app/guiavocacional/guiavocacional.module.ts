import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GuiavocacionalRoutingModule } from './guiavocacional-routing.module';
import { GuiavocacionalComponent } from './guiavocacional.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { PartnersComponent } from './partners/partners.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    GuiavocacionalRoutingModule,
    FormsModule
  ],
  declarations: [
    GuiavocacionalComponent,
    HeaderComponent,
    FooterComponent,
    PartnersComponent
  ]
})
export class GuiavocacionalModule {

}
