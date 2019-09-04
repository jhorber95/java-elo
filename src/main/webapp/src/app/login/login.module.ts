import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';

import {SharedModule} from '../shared.module';

@NgModule({
    imports: [
      CommonModule,
      LoginRoutingModule,
      FormsModule,
      SharedModule
    ],
    declarations: [LoginComponent, ResetPasswordComponent]
})
export class LoginModule {
}
