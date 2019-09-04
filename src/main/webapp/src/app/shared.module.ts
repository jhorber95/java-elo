import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EqualValidator } from './directives/equal.directive';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    EqualValidator
  ],
  declarations: [
    EqualValidator
  ]
})
export class SharedModule { }
