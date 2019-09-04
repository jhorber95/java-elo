import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InstitucionComponent } from './institucion.component';
import { AuthGuard } from "../shared/guard/auth.guard";


const routes: Routes = [
{
  path: '', component: InstitucionComponent,
  children: [
    {
    	path: 'dashboard',
    	loadChildren: './dashboard/dashboardinstitucion.module#DashboardinstitucionModule' ,
      canActivateChild: [AuthGuard]
    }, {
        path: 'ins-ofertas',
        loadChildren: './content/ofertas/ofertas-institucion.module#OfertasInstitucionModule' ,
        canActivateChild: [AuthGuard]
    }, {
        path: 'ins-nueva-oferta',
        loadChildren: './content/create-oferta-inst/create-oferta-inst.module#CreateOfertasInstitucionModule' ,
        canActivateChild: [AuthGuard]
    }, {
        path: 'ins-inscripciones',
        loadChildren: './content/inscripciones-insti/inscripciones-insti.module#IncripcionesInstitucionModule' ,
        canActivateChild: [AuthGuard]
    }, {
        path: 'detalle-oferta-ins/:id',
        loadChildren: './content/view-oferta-inst/view-oferta-inst.module#ViewOfertaInstitucionModule' ,
        canActivateChild: [AuthGuard]
    }, {
        path: 'perfil-ins',
        loadChildren: './content/perfil-insti/perfil-insti.module#PerfilInstitucionModule' ,
        canActivateChild: [AuthGuard]
    }/*, {
        path: 'crear-evento',
        loadChildren: './content/eventos/crear-evento/crear-evento.module#CrearEventoModule' ,
        canActivateChild: [AuthGuard]
    }, {
        path: 'lista-eventos',
        loadChildren: './content/eventos/lista-eventos/lista-eventos.module#ListaEventosModule' ,
        canActivateChild: [AuthGuard]
    }, {
        path: 'editar-evento/:id',
        loadChildren: './content/eventos/editar-evento/editar-evento.module#EditarEventoModule' ,
        canActivateChild: [AuthGuard]
    }*/

  ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class InstitucionRoutingModule { }
