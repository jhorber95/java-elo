import { Component, OnInit, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-about-us',
  templateUrl: './about-us.component.html',
  styleUrls: ['./about-us.component.css']
})
export class AboutUsComponent implements OnInit, AfterViewInit {

  constructor() { }

  ngOnInit() {
  }


  ngAfterViewInit() {
    window.scrollTo(0, 0);
    $(function () {
      $('.preloader').fadeOut();
    });
    // tooltip
    $(function() {
      (<any>$('[data-toggle="tooltip"]')).tooltip();
    });

    // popover

    $(function() {
        (<any>$('[data-toggle="popover"]')).popover();
    });

    $('.multi-item-carousel .carousel-item').each(function(){
      let next = $(this).next();
      if (!next.length) {
        next = $(this).siblings(':first');
      }
      next.children(':first-child').clone().appendTo($(this));

      if (next.next().length > 0) {
        next.next().children(':first-child').clone().appendTo($(this));
      } else {
        $(this).siblings(':first').children(':first-child').clone().appendTo($(this));
      }
    });

    }
}
