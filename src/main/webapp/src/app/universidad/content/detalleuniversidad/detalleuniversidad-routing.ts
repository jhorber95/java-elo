import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DetalleuniversidadComponent } from './detalleuniversidad.component';

const routes: Routes = [
    { path: '', component: DetalleuniversidadComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DetalleuniversidadRoutingModule {

}
