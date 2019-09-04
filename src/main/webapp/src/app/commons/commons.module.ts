import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { FitroBusquedaComponent } from './fitro-busqueda/fitro-busqueda.component';
import { PartialComponent }       from './fitro-busqueda/partial/partial.component';
import { HeaderComponent }        from './header/header.component';
import { PartnersComponent }      from './partners/partners.component';
import { FooterComponent }        from './footer/footer.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule
  ],
  declarations: [
    FitroBusquedaComponent,
    PartialComponent,
    HeaderComponent,
    PartnersComponent,
    FooterComponent
  ],
  exports: [
    FitroBusquedaComponent,
    PartialComponent,
    // HeaderComponent,
    // PartnersComponent,
    // FooterComponent
  ]
})
export class CommonsModule { }
