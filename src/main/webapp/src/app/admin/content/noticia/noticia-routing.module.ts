import {Injectable, NgModule} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterModule, RouterStateSnapshot, Routes} from '@angular/router';
import {NoticiaComponent} from './noticia.component';
import {NoticiaUpdateComponent} from './noticia-update.component';
import {Observable} from 'rxjs';
import {NoticiaService} from './noticia.service';
import {Noticia} from '../../../models/noticia';
import {of} from 'rxjs/observable/of';


@Injectable()
export class NoticiaResolve implements Resolve<any> {
  constructor(private service: NoticiaService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    const id = route.params['id'] ? route.params['id'] : null;
    // debugger;

    if (id) {
      return this.service.getOne(id);
    }
    return of(new Noticia());
  }

}

const routes: Routes = [
  {
    path: '', component: NoticiaComponent
  },
  {
    path: 'create',
    component: NoticiaUpdateComponent,
    resolve: {
      noticia: NoticiaResolve
    }
  },
  {
    path: 'update/:id',
    component: NoticiaUpdateComponent,
    resolve: {
      noticia: NoticiaResolve
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class NoticiaRoutingModule {
}
