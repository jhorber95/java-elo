import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TestRoutingModule } from './test-routing.module';
import { TestComponent } from './test.component';

import { FormsModule } from '@angular/forms';
import {PartialsModule} from '../partials/partials.module';

@NgModule({
  imports: [
    CommonModule,
    TestRoutingModule,
    FormsModule,
    PartialsModule
  ],
  declarations: [
    TestComponent,
  ]
})
export class TestModule {

}
