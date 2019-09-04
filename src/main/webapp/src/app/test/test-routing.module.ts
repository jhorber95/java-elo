import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TestComponent } from './test.component';
//importat activadores de vista test
import { WorkflowtestGuardService } from '../shared/workflowtest/workflowtest-guard.service';
import { WorkflowtestService }      from '../shared/workflowtest/workflowtest.service';

const routes: Routes = [
    {
      path: '', component: TestComponent,
      children: [
        { path: 'fase1', loadChildren: './content/fase1/fase1.module#Fase1Module' },
        { path: 'fase2', loadChildren: './content/fase2/fase2.module#Fase2Module', canActivate: [WorkflowtestGuardService] },
        { path: 'fase3', loadChildren: './content/fase3/fase3.module#Fase3Module', canActivate: [WorkflowtestGuardService] },
        { path: 'fase4', loadChildren: './content/fase4/fase4.module#Fase4Module', canActivate: [WorkflowtestGuardService] },
        { path: 'fase5', loadChildren: './content/fase5/fase5.module#Fase5Module', canActivate: [WorkflowtestGuardService] },
        { path: 'fase6', loadChildren: './content/fase6/fase6.module#Fase6Module', canActivate: [WorkflowtestGuardService] },
        { path: 'fase7', loadChildren: './content/fase7/fase7.module#Fase7Module', canActivate: [WorkflowtestGuardService] },
        { path: 'fase8', loadChildren: './content/fase8/fase8.module#Fase8Module', canActivate: [WorkflowtestGuardService] },
        { path: 'resultado', loadChildren: './content/resultado/resultado.module#ResultadoModule', canActivate: [WorkflowtestGuardService] }
      ]
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [WorkflowtestGuardService]
})
export class TestRoutingModule {

}
