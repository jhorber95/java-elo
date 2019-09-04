import * as $ from 'jquery';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router, Params }   from '@angular/router';

import { FinancierasService } from '../../../services/financieras/financieras.service';
import { Search }             from '../../../models/search';

@Component({
  selector: 'app-todas',
  templateUrl: './todas.component.html',
  styleUrls: ['./todas.component.css'],
  providers: [FinancierasService]
})
export class TodasComponent implements OnInit, AfterViewInit  {

  public FinancierasAll;

  constructor(
    private _financierasService: FinancierasService,
    private _route: ActivatedRoute,
    private _router: Router
  ) {

   }

  ngOnInit() {
    this.getFinancieras();
  }


  ngAfterViewInit() {
    window.scrollTo(0, 0);

        $(function() {
            $('.preloader').fadeOut();
        });
  }

  getFinancieras() {
    this._financierasService.getFinancieras().subscribe(
      response => {
          this.FinancierasAll = response.data;
      },
      error => {
        const errorMessage = <any>error;
        console.log(errorMessage);
      }
     );
  }

}
