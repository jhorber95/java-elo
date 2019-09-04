import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SignupFreelancerComponent } from './signup-freelancer.component';

const routes: Routes = [
    { path: '', component: SignupFreelancerComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SignupFreelancerRoutingModule { }
