import { NgModule, OnInit, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { HeaderComponent } from './header/header.component';
import { PartnersComponent } from './partners/partners.component';
import { FooterComponent } from './footer/footer.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';

import { NgxCarouselModule } from 'ngx-carousel';
import 'hammerjs';
import { CommonsModule } from '../commons/commons.module';



@NgModule({
  imports: [
    CommonModule,
    HomeRoutingModule,
    FormsModule,
    NgxCarouselModule,
    NgbModule.forRoot()

    // CommonsModule
  ],
  declarations: [
    HomeComponent,
    HeaderComponent,
    PartnersComponent,
    FooterComponent
  ]
})
export class HomeModule implements AfterViewInit, OnInit {

  ngAfterViewInit(): void {
  }

  ngOnInit(): void {
  }

}
