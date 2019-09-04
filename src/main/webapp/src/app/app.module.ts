import * as $ from 'jquery';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// import { ToastrModule } from 'ngx-toastr';

import { NgModule}       from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './shared/guard/auth.guard';
import { RoleGuardService } from './shared/guard/role-guard.service';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
// Importar HttpClientModule
import { HttpClientModule } from '@angular/common/http';
import {AuthService} from './shared/guard/auth.service';
// user sevices
import { UserService } from './services/user/user.service';
// wow ngx
import { NgwWowModule } from 'ngx-wow';
/* Shared Service */
import { WorkflowtestService } from './shared/workflowtest/workflowtest.service';

import { MunicipioService } from './services/municipio.service';
import { DepartamentoService } from './services/departamento.service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    // ToastrModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    HttpClientModule,
    AppRoutingModule,
    NgwWowModule.forRoot()
  ],
  providers: [
    AuthService,
    RoleGuardService,
    AuthGuard,
    UserService,
    WorkflowtestService,
    UserService,
    DepartamentoService,
    MunicipioService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
