import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Fase3Component } from './fase3.component';

const routes: Routes = [
    { path: '', component: Fase3Component }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class Fase3RoutingModule {

}
