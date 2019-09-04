import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Fase4Component } from './fase4.component';

const routes: Routes = [
    { path: '', component: Fase4Component }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class Fase4RoutingModule {

}
