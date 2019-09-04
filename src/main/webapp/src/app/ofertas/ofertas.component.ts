import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-home',
  templateUrl: './ofertas.component.html',
  styleUrls: ['./ofertas.component.css']
})
export class OfertasComponent implements OnInit, AfterViewInit {

  constructor(public router: Router) {}

  ngOnInit() {
  }

  ngAfterViewInit() {
    window.scrollTo(0, 0);

    $(function () {
      $(".preloader").fadeOut();
    });

        $(function() {
            (<any>$('[data-toggle="tooltip"]')).tooltip();
        });

    }

}
