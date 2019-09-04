import { Component, OnInit, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-financieras',
  templateUrl: './financieras.component.html',
  styleUrls: ['./financieras.component.css']
})
export class FinancierasComponent implements OnInit, AfterViewInit {
  ngAfterViewInit(): void {
    window.scrollTo(0, 0);
  }

  constructor() { }

  ngOnInit() {
  }

}
