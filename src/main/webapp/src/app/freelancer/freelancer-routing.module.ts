import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FreelancerComponent } from './freelancer.component';
import { AuthGuard } from "../shared/guard/auth.guard";


const routes: Routes = [
{
  path: '', component: FreelancerComponent,
  children: [
  { 
    path: 'dashboard', 
    loadChildren: './dashboard/dashboardfreelancer.module#DashboardfreelancerModule' ,
    canActivateChild: [AuthGuard]
  },{ 
    path: 'gestion-ofertas', 
    loadChildren: './content/gestion-ofertas/gestion-ofertas.module#GestionOfertasModule' ,
    canActivateChild: [AuthGuard]
  },{ 
    path: 'view-oferta/:id', 
    loadChildren: './content/view-oferta/view-oferta.module#ViewOfertaModule' ,
    canActivateChild: [AuthGuard]
  },{ 
    path: 'gestion-inscripciones', 
    loadChildren: './content/inscripciones/inscripciones.module#IncripcionesModule' ,
    canActivateChild: [AuthGuard]
  },{ 
    path: 'crear-oferta', 
    loadChildren: './content/create-oferta/create-oferta.module#CreateOfertaModule' ,
    canActivateChild: [AuthGuard]
  },{ 
    path: 'perfil-freelancer', 
    loadChildren: './content/perfil-freelancer/perfil-freelancer.module#PerfilFreelancerModule' ,
    canActivateChild: [AuthGuard]
  }
  ]
}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FreelancerRoutingModule { }
