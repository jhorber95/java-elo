import {Component, OnInit, AfterViewInit, Input} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';

import {Fases, FormDataTest, Items} from '../../../models/test/form-data-test';
import { FormDataTestService } from '../../../services/form-data-test.service';
import {WorkflowtestService} from "../../../shared/workflowtest/workflowtest.service";
import {STEPS} from "../../../shared/workflowtest/workflow.model";
import {ResultData} from "../../../models/test/result-data";

@Component({
  selector: 'app-fase8',
  templateUrl: './fase8.component.html',
  styleUrls: ['./fase8.component.css']
})
export class Fase8Component implements OnInit, AfterViewInit {

  @Input() formDataTest: FormDataTest;
  @Input() resultTest: ResultData;
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
      debugger;
      // Validate Personal Step in Workflow
      this.workFlowTestService.validateStep(STEPS.fase8);
      this.formDataTestService.sendTest(this.formDataTest).subscribe(
        result => {
          if(result.codigo === 200) {
            this.resultTest = result.data[0];
            this.formDataTestService.setTestResult(this.resultTest);
            // redirigir
            this.router.navigate(['/test/resultado']);
          }else{
            console.log('algo ha salido mal');
          }
        },
        error => {
          console.log(<any>error);
        }
      );

    }else{
      this.error = 'Error, seleccione mínimo 2 y máximo 4 imagenes por fase';
    }
  }

  ValidateItems(): boolean {
    if($('input[type=checkbox]:checked').length >=2 && $('input[type=checkbox]:checked').length <=4) {
      return true;
    }else{
      return false;
    }
  }

  restartNotification() {
    this.error = '';
  }
}
