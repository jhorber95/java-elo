import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UniversidadComponent } from './universidad.component';

const routes: Routes = [
    {
      path: '', component: UniversidadComponent,
      children: [
        { path: 'detalle/:id', loadChildren: './content/detalleuniversidad/detalleuniversidad.module#DetalleuniversidadModule' }
      ]
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UniversidadRoutingModule {

}
