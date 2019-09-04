import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FiltrarComponent } from './filtrar.component';


const routes: Routes = [
    { path: '', component: FiltrarComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FiltrarRoutingModule {

}
