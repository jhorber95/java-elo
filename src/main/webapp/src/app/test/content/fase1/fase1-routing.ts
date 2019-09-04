import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Fase1Component } from './fase1.component';

const routes: Routes = [
    { path: '', component: Fase1Component }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class Fase1RoutingModule {

}
