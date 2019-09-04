import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SignupFreelancerRoutingModule } from './signup-freelancer-routing.module';
import { SignupFreelancerComponent } from './signup-freelancer.component';
import { FormsModule } from '@angular/forms';

// shared module
import {SharedModule} from '../shared.module';


@NgModule({
  imports: [
    CommonModule,
    SignupFreelancerRoutingModule,
    FormsModule,
    SharedModule
  ],
  declarations: [SignupFreelancerComponent
    // EqualValidator
  ]
})
export class SignupFreelancerModule { }
