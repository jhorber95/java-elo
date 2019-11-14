import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoticiasComponent } from './noticias.component';
import {PartialsModule} from '../partials/partials.module';
import { LoadNoticiasComponent } from './load-noticias/load-noticias.component';
import {NoticiasRoutingModule} from './noticias-routing.module';

@NgModule({
  imports: [
    CommonModule,
    PartialsModule,
    NoticiasRoutingModule
  ],
  declarations: [NoticiasComponent, LoadNoticiasComponent]
})
export class NoticiasModule { }
