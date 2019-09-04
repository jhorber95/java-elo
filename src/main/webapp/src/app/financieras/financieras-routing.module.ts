import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FinancierasComponent } from './financieras.component';

const routes: Routes = [
    {
      path: '', component: FinancierasComponent,
      children: [
        { path: 'todas', loadChildren: './content/todas/todas.module#TodasModule' },
        { path: 'detalle/:id', loadChildren: './content/detallefinanciera/detallefinanciera.module#DetallefinancieraModule' }
      ]
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FinancierasRoutingModule {

}
