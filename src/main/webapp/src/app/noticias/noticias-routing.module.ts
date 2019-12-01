import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NoticiasComponent} from './noticias.component';
import {DetalleNoticiaComponent} from './detalle-noticia/detalle-noticia.component';


const routes: Routes = [
    { path: '', component: NoticiasComponent },
  { path: 'detalle-noticia/:idNoticia', component: DetalleNoticiaComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NoticiasRoutingModule {

}
