import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TestRoutingModule } from './test-routing.module';
import { TestComponent } from './test.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { PartnersComponent } from './partners/partners.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    TestRoutingModule,
    FormsModule
  ],
  declarations: [
    TestComponent,
    HeaderComponent,
    FooterComponent,
    PartnersComponent
  ]
})
export class TestModule {

}
