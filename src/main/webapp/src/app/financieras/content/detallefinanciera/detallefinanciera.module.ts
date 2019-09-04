import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DetallefinancieraRoutingModule } from './detallefinanciera-routing';
import { DetallefinancieraComponent } from './detallefinanciera.component';
// google maps
import { AgmCoreModule } from '@agm/core';


@NgModule({
  imports: [
    CommonModule,
    DetallefinancieraRoutingModule,
    AgmCoreModule.forRoot({apiKey: 'AIzaSyAkvblv6n00cATW9DCy-vfpIl7ZoqfZWl4'})
  ],
  declarations: [
    DetallefinancieraComponent
    ]
})

export class DetallefinancieraModule {

}
