import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DetalleofertaComponent } from './detalleoferta.component';

const routes: Routes = [
    { path: '', component: DetalleofertaComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DetalleofertaRoutingModule {

}
