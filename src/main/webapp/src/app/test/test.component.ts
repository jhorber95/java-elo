import {Component, OnInit, AfterViewInit, OnDestroy, Input} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
//wowngx
import { NgwWowService } from 'ngx-wow';
import 'rxjs/add/operator/filter';
import { Subscription }   from 'rxjs/Subscription';
//test service
import { FormDataTestService } from '../services/form-data-test.service'
import {Fases, FormDataTest} from "../models/test/form-data-test";
import {ResultData} from "../models/test/result-data";

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css'],
  providers: [FormDataTestService]
})
export class TestComponent implements OnInit, AfterViewInit, OnDestroy {

  private formDataTest: FormDataTest = new FormDataTest();
  public resultTest: ResultData = new ResultData();

  private wowSubscription: Subscription;

  constructor(public router: Router,
              private wowService: NgwWowService,
              private formDataService: FormDataTestService) {
    //wowngx
    this.router.events.filter(event => event instanceof NavigationEnd).subscribe(event => {
      // Reload WoW animations when done navigating to page,
      // but you are free to call it whenever/wherever you like
      this.wowService.init();
    });
    //wowngx
  }

  ngOnInit() {
    // you can subscribe to WOW observable to react when an element is revealed
    this.wowSubscription = this.wowService.itemRevealed$.subscribe(
      (item:HTMLElement) => {
        // do whatever you want with revealed element
      });

    //get test vocacional
    this.getFormDataTest();
    this.formDataService.setFormDataTest(this.formDataTest);
  }

  ngAfterViewInit() {
    window.scrollTo(0, 0);

        $(function() {
            (<any>$('[data-toggle="tooltip"]')).tooltip();
        });

    }

  ngOnDestroy() {
    // unsubscribe (if necessary) to WOW observable to prevent memory leaks
    this.wowSubscription.unsubscribe();
  }

  getFormDataTest() {
    this.formDataService.getFormDataTest().subscribe(
      response => {
        if(response.codigo === 200) {
          this.formDataTest.id = response.data[0].id;
          this.formDataTest.nombre = response.data[0].nombre;
          for(let i = 0; i< response.data[0].fases.length; i++) {
            this.formDataTest.fases[i] = response.data[0].fases[i];
          }
        }else {
          console.log(response);
        }
      },
      error => {
        console.log(<any>error);
      }
    );
  }

}
