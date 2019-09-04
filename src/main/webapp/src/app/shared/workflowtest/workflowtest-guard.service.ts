import { Injectable } from '@angular/core';
import {
  CanActivate, Router, CanActivateChild,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  CanLoad, Route
} from '@angular/router';
import { WorkflowtestService } from './workflowtest.service'

@Injectable()
export class WorkflowtestGuardService implements CanActivate{

  constructor(private router: Router, private workflowService: WorkflowtestService) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    let path: string = '/test/'+ route.routeConfig.path;

    return this.verifyWorkFlow(path);
  }

  verifyWorkFlow(path) : boolean {
    // If any of the previous steps is invalid, go back to the first invalid step
    let firstPath = this.workflowService.getFirstInvalidStep(path);
    if (firstPath.length > 0) {
      let url = `/${firstPath}`;
      this.router.navigate([url]);
      return false;
    };

    return true;
  }

}
