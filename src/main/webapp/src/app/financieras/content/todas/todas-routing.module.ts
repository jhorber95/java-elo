import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TodasComponent } from './todas.component';


const routes: Routes = [
    { path: '', component: TodasComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
}) 
export class TodasRoutingModule {

}
