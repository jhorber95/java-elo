import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Fase5Component } from './fase5.component';

const routes: Routes = [
    { path: '', component: Fase5Component }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class Fase5RoutingModule {

}
