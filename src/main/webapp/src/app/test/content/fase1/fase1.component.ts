import {Component, OnInit, AfterViewInit, Input} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';

import {FormDataTest} from '../../../models/test/form-data-test';
import {FormDataTestService} from '../../../services/form-data-test.service';
import {STEPS} from '../../../shared/workflowtest/workflow.model';
import {WorkflowtestService} from '../../../shared/workflowtest/workflowtest.service';

@Component({
  selector: 'app-fase1',
  templateUrl: './fase1.component.html',
  styleUrls: ['./fase1.component.css']
})
export class Fase1Component implements OnInit, AfterViewInit {

  @Input() formDataTest: FormDataTest;
  public error = '';

  constructor(
    private router: Router,
    private _route: ActivatedRoute,
    private formDataTestService: FormDataTestService,
    private workFlowTestService: WorkflowtestService) {
  }


  ngOnInit() {
    this.formDataTest = this.formDataTestService.getFormDataTestSubmit();
  }

  ngAfterViewInit() {
    window.scrollTo(0, 0);

    $(function () {
      $('.preloader').fadeOut();
    });
  }

  NextFase() {
    if (this.ValidateItems()) {
      // Validate Personal Step in Workflow
      this.workFlowTestService.validateStep(STEPS.fase1);
      // redirigir
      this.router.navigate(['/test/fase2']);
    } else {
      this.error = 'Error, seleccione mínimo 2 y máximo 4 imagenes por fase';
    }
  }

  ValidateItems(): boolean {
    if ($('input[type=checkbox]:checked').length >= 2 && $('input[type=checkbox]:checked').length <= 4) {
      return true;
    } else {
      return false;
    }
  }

  restartNotification() {
    this.error = '';
  }
}
