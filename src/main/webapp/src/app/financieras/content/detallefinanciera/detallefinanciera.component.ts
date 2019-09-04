import { Component, OnInit, AfterViewInit } from '@angular/core';

import { ActivatedRoute, Params } from '@angular/router';
import { DetallefinancieraService } from '../../../services/financieras/detallefinanciera.service';

@Component({
  selector: 'app-detalleoferta',
  templateUrl: './detallefinanciera.component.html',
  styleUrls: ['./detallefinanciera.component.css'],
  providers: [DetallefinancieraService]
})
export class DetallefinancieraComponent implements OnInit, AfterViewInit {
  lat: number;
  lng: number;
  zoom: number;

  private id: number;
  public detalleFinanciera: any[] = [];

  constructor(private _route: ActivatedRoute,
    private _detalleFinancieraService: DetallefinancieraService
  ) {
    this.id = 0;
  }

  ngOnInit() {
    this._route.params.forEach((params: Params) => {
      this.id = params['id'];
    });
    this.getDetalleFinanciera();
  }

  getDetalleFinanciera() {
    this._detalleFinancieraService.getDetalleFinanciera(this.id).subscribe(
      response => {
          this.detalleFinanciera = response.data;
          this.zoom = 15;
          this.lat = Number(response.data[0].institucion.latitud);
          this.lng = Number(response.data[0].institucion.longitud);
      },
      error => {
        var errorMessage = <any>error;
        console.log(errorMessage);
      }
      );
  }


  ngAfterViewInit() {
    window.scrollTo(0, 0);

        $(function () {
            $(".preloader").fadeOut();
        });
  }

}
