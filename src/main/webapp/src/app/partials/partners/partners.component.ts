import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-partners',
  templateUrl: './partners.component.html',
  styleUrls: ['./partners.component.css']
})
export class PartnersComponent implements OnInit {

  public partners: any = [];

  constructor() {

    this.partners = [
      {
        title: 'Ir a Mintic',
        url: 'http://www.mintic.gov.co/',
        image: 'assets/images/sponsors/mintic_Blanco.png',
        target: '_blank'
      },
      {
        title: 'Ir a Codaltec',
        url: 'https://www.codaltec.com/',
        image: 'assets/images/sponsors/codaltec.png',
        target: '_blank'
      },
      {
        title: 'Ir a Apps.co',
        url: 'https://www.apps.co/',
        image: 'assets/images/sponsors/appsco_Blanco.png',
        target: '_blank'
      }
    ]
    ;
   }

  ngOnInit() {
  }

}
