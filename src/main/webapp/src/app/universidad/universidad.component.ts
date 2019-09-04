import { Component, OnInit, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-universidad',
  templateUrl: './universidad.component.html',
  styleUrls: ['./universidad.component.css']
})
export class UniversidadComponent implements OnInit, AfterViewInit {
  ngAfterViewInit(): void {
    window.scrollTo(0, 0);
  }

  constructor() { }

  ngOnInit() {
  }

}
