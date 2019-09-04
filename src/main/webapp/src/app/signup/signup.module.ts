import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SignupRoutingModule } from './signup-routing.module';
import { SignupComponent } from './signup.component';
import { FormsModule } from '@angular/forms';

//shared module
import {SharedModule} from "../shared.module";

@NgModule({
  imports: [
    CommonModule,
    SignupRoutingModule,
    FormsModule,
    SharedModule
  ],
  declarations: [SignupComponent
    //EqualValidator
  ]
})
export class SignupModule { }
