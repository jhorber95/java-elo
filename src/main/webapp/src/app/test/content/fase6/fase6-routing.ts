import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Fase6Component } from './fase6.component';

const routes: Routes = [
    { path: '', component: Fase6Component }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class Fase6RoutingModule {

}
