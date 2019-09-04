import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GuiavocacionalComponent } from './guiavocacional.component';

const routes: Routes = [
    { path: '', component: GuiavocacionalComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GuiavocacionalRoutingModule {

}
