import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-eventos',
  templateUrl: './eventos.component.html',
  styleUrls: ['./eventos.component.css']
})
export class EventosComponent implements OnInit, AfterViewInit {

  constructor(public router: Router) {}

  ngOnInit() {
  }

  ngAfterViewInit() {
    window.scrollTo(0, 0);
        $(function() {
            (<any>$('[data-toggle="tooltip"]')).tooltip();
        });

    }

}
