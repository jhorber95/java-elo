import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EstudianteComponent } from './estudiante.component';


const routes: Routes = [
    {
        path: '', component: EstudianteComponent,
        children: [
            {
                path: 'dashboard',
                loadChildren: './dashboard/dashboard.module#DashboardModule'
            }, {
                path: 'cursos',
                loadChildren: './content/cursos/cursos.module#CursosModule'
            }, {
                path: 'profile',
                loadChildren: './content/perfil/perfil.module#PerfilModule'
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class EstudianteRoutingModule { }
