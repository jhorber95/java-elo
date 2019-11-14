import { NgModule, OnInit, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

import { NgxCarouselModule } from 'ngx-carousel';
import 'hammerjs';
import { CommonsModule } from '../commons/commons.module';
import {PartialsModule} from '../partials/partials.module';



@NgModule({
  imports: [
    CommonModule,
    HomeRoutingModule,
    FormsModule,
    NgxCarouselModule,
    NgbModule.forRoot(),
    PartialsModule


    // CommonsModule
  ],
  declarations: [
    HomeComponent,
  ]
})
export class HomeModule implements AfterViewInit, OnInit {

  ngAfterViewInit(): void {
  }

  ngOnInit(): void {
  }

}
