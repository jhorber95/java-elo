import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AuthGuard } from './shared/guard/auth.guard';
import { RoleGuardService as RoleGuard } from './shared/guard/role-guard.service';

const routes: Routes = [
    {
      path: 'admin',
      loadChildren: './admin/admin.module#PagesModule',
      canActivate: [RoleGuard],
      data: { 'roles': ['ROLE_ADMINISTRADOR'] }
    },
    { path: '', loadChildren: './home/home.module#HomeModule'},
    { path: 'estudiante',
      loadChildren: './estudiante/estudiante.module#EstudianteModule',
      canActivate: [RoleGuard],
      data: { 'roles': ['ROLE_ADMINISTRADOR', 'ROLE_ESTUDIANTE'] }
    },
    { path: 'institucion',
      loadChildren: './institucion/institucion.module#InstitucionModule',
      canActivate: [RoleGuard],
      data: { 'roles': ['ROLE_INSTITUCION']}
      },
    { path: 'freelancer',
      loadChildren: './freelancer/freelancer.module#FreelancerModule',
      canActivate: [RoleGuard],
      data: { 'roles': ['ROLE_FREELANCER']}
    },
    { path: 'ofertas', loadChildren: './ofertas/ofertas.module#OfertasModule'},
    { path: 'eventos', loadChildren: './eventos/eventos.module#EventosModule'},
    { path: 'financieras', loadChildren: './financieras/financieras.module#FinancierasModule'},
    { path: 'universidad', loadChildren: './universidad/universidad.module#UniversidadModule'},
    // tslint:disable-next-line:max-line-length
    {
      path: 'filtrar/:iddep/:idmun/:idcat/:idInteligencia/:idoferta/:idofrece/:preciomin/:preciomax/:stringCarrera',
      loadChildren: './filtrar/filtrar.module#FiltrarModule'
    },
    {
      path: 'guiavocacional',
      loadChildren: './guiavocacional/guiavocacional.module#GuiavocacionalModule'
    },
    {
      path: 'test',
      loadChildren: './test/test.module#TestModule'
    },
    {
      path: 'login',
      loadChildren: './login/login.module#LoginModule'
    },
    {
      path: 'registrate',
      loadChildren: './signup/signup.module#SignupModule'
    },
    {
      path: 'registrar-freelancer',
      loadChildren: './signup-freelancer/signup-freelancer.module#SignupFreelancerModule'
    },
    {
      path: '404',
      loadChildren: './404/not-found.module#NotFoundModule'
    },
    {
      path: 'acerca',
      loadChildren: './about-us/about-us.module#AboutUsModule'
    },
    {
      path: '**',
      redirectTo: '404'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes), NgbModule.forRoot()],
    exports: [RouterModule]
})
export class AppRoutingModule { }
