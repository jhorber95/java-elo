import {Component, OnInit, AfterViewInit, Input} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';

import {Fases, FormDataTest, Items} from '../../../models/test/form-data-test';
import { FormDataTestService } from '../../../services/form-data-test.service'
import {STEPS} from "../../../shared/workflowtest/workflow.model";
import {WorkflowtestService} from "../../../shared/workflowtest/workflowtest.service";

@Component({
  selector: 'app-fase2',
  templateUrl: './fase2.component.html',
  styleUrls: ['./fase2.component.css']
})
export class Fase2Component implements OnInit, AfterViewInit {

  @Input() formDataTest: FormDataTest;
  public error = '';

  constructor(private router: Router,
              private _route: ActivatedRoute,
              private formDataTestService: FormDataTestService,
              private workFlowTestService: WorkflowtestService) {  }



  ngOnInit() {
    this.formDataTest = this.formDataTestService.getFormDataTestSubmit();
  }



  ngAfterViewInit() {
    window.scrollTo(0, 0);

        $(function () {
            $(".preloader").fadeOut();
        });
  }

  NextFase() {
    if(this.ValidateItems()){
      // Validate Personal Step in Workflow
      this.workFlowTestService.validateStep(STEPS.fase2);
      // redirigir
      this.router.navigate(['/test/fase3']);
    }else{
      this.error = 'Error, seleccione mínimo 2 y máximo 4 imagenes por fase';
    }
  }

  ValidateItems(): boolean {
    const selector = $('input[type=checkbox]:checked');
    return selector.length >= 2 && selector.length <= 4;
  }

  restartNotification() {
    this.error = '';
  }
}
