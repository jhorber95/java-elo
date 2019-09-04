import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OfertasComponent } from './ofertas.component';

import { RoleGuardService as RoleGuard } from '../shared/guard/role-guard.service';

const routes: Routes = [
    {
      path: '', component: OfertasComponent,
      children: [
        { path: 'busqueda', loadChildren: './content/busqueda/busqueda.module#BusquedaModule' },
        { path: 'busqueda/:search', loadChildren: './content/busqueda/busqueda.module#BusquedaModule' },
        { path: 'detalle/:id', loadChildren: './content/detalleoferta/detalleoferta.module#DetalleofertaModule' },
        { path: 'guiavocacional', loadChildren: './content/guiavocacional/guiavocacional.module#GuiavocacionalModule',
          canActivate: [RoleGuard], data: { 'roles': ['ROLE_ADMINISTRADOR', 'ROLE_ESTUDIANTE']} }
      ]
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OfertasRoutingModule {

}
