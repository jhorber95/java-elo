import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {NoticiaResolve, NoticiaRoutingModule} from './noticia-routing.module';
import { NoticiaComponent } from './noticia.component';
import { NoticiaUpdateComponent } from './noticia-update.component';
import {NoticiaService} from './noticia.service';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {CKEditorModule} from 'ckeditor4-angular';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    NoticiaRoutingModule,
    NgbModule,
    CKEditorModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [NoticiaComponent, NoticiaUpdateComponent],
  providers: [
    NoticiaService,
    NoticiaResolve
  ]
})
export class NoticiaModule { }
