import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DetallefinancieraComponent } from './detallefinanciera.component';

const routes: Routes = [
    { path: '', component: DetallefinancieraComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DetallefinancieraRoutingModule {

}
