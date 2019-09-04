import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EventosComponent } from './eventos.component';

const routes: Routes = [
    {
      path: '', component: EventosComponent,
      children: [
        { path: 'todos', loadChildren: './content/todos/todos.module#TodosModule' },
        { path: 'detalle/:id', loadChildren: './content/detalle/detalle.module#DetalleModule' }
      ]
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EventosRoutingModule {

}
