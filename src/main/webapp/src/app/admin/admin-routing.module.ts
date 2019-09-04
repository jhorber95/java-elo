import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminComponent } from './admin.component';
import {AuthGuard} from '../shared/guard/auth.guard';
import {AdminCreateInstitucionModule} from './content/admin-crear-institucion/admin-create-institucion.module';

const routes: Routes = [
    {
        path: '', component: AdminComponent,
        children: [
          { path: 'admin-usuarios',
            loadChildren: './content/admin-usuarios/admin-usuarios.module#AdminUsuariosModule',
            canActivateChild: [AuthGuard]
          },
          { path: 'perfil',
            loadChildren: './starter/starter.module#StarterModule',
            canActivateChild: [AuthGuard]
          },
          { path: 'admin-ofertas',
            loadChildren: './content/admin-ofertas/admin-oferta.module#AdminOfertaModule',
            canActivateChild: [AuthGuard]
          },
          { path: 'admin-nueva-oferta',
            loadChildren: './content/admin-ofertas/nueva-oferta/nueva-oferta.module#NuevaOfertaModule',
            canActivateChild: [AuthGuard]
          },
          { path: 'admin-editar-oferta/:id',
          loadChildren: './content/admin-ofertas/editar-oferta/editar-oferta.module#EditarOfertaModule',
          canActivateChild: [AuthGuard]
          },

          // INSTITUCIONES

          { path: 'admin-instituciones',
            loadChildren: './content/admin-instituciones/admin-instituciones.module#AdminInstitucionesModule',
            canActivateChild: [AuthGuard]
          },
          { path: 'admin-agregar-institucion',
            loadChildren: './content/admin-crear-institucion/admin-create-institucion.module#AdminCreateInstitucionModule',
            canActivateChild: [AuthGuard]
          },

          { path: 'admin-editar-institucion/:idInstitucion',
            loadChildren: './content/admin-instituciones/editar-institucion/editar-institucion.module#EditarInstitucionModule',
            canActivateChild: [AuthGuard]
          },
          { path: 'estudiantes-institucion/:idInstitucion/:nombreInstitucion',
            // tslint:disable-next-line:max-line-length
            loadChildren: './content/admin-instituciones/estudiantes-institucion/estudiantes-institucion.module#EstudiantesInstitucionModule',
            canActivateChild: [AuthGuard]
          },
          // FIN RUTAS INSTITUCIONES


          { path: 'admin-financiaciones',
            loadChildren: './content/admin-financiaciones/admin-financiaciones.module#AdminFinanciacionesModule',
            canActivateChild: [AuthGuard]
          },
          { path: 'admin-publicidad',
            loadChildren: './content/admin-publicidad/admin-publicidad.module#AdminPublicidadModule',
            canActivateChild: [AuthGuard]
          },
          { path: 'admin-pqrs',
            loadChildren: './content/admin-pqrs/admin-pqrs.module#AdminPqrsModule',
            canActivateChild: [AuthGuard]
          },
          { path: 'admin-eventos',
            loadChildren: './content/admin-eventos/lista-eventos/lista-eventos.module#ListaEventosModule',
            canActivateChild: [AuthGuard]
          }
          ,
          { path: 'crear-evento',
            loadChildren: './content/admin-eventos/crear-evento/crear-evento.module#CrearEventoModule',
            canActivateChild: [AuthGuard]
          }, {
            path: 'editar-evento/:id',
            loadChildren: './content/admin-eventos/editar-evento/editar-evento.module#EditarEventoModule' ,
            canActivateChild: [AuthGuard]
        }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule { }
