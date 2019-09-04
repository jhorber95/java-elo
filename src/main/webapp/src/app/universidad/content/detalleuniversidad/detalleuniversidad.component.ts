import { Component, OnInit, AfterViewInit } from '@angular/core';

import {ActivatedRoute, Params, Router} from '@angular/router';
import { DetalleuniversidadService } from '../../../services/universidad/detalleuniversidad.service'


@Component({
  selector: 'app-detalleuniversidad',
  templateUrl: './detalleuniversidad.component.html',
  styleUrls: ['./detalleuniversidad.component.css'],
  providers: [DetalleuniversidadService]
})
export class DetalleuniversidadComponent implements OnInit, AfterViewInit {
  lat: number;
  lng: number;
  zoom: number;

  private id: number;
  public detalleUniversidad: any[] = [];

  constructor(private _route: ActivatedRoute,
              private detalleUniversidadService: DetalleuniversidadService,
              private router: Router
  ) {
    this.id = 0;
  }

  ngOnInit() {
    this._route.params.forEach((params: Params) => {
      this.id = params['id'];
    });
    this.getDetalleUniversidad();
  }

  getDetalleUniversidad() {

    this.detalleUniversidadService.getDetalleUniversidad(this.id).subscribe(
      response => {
        if(response.codigo === 200){
          if(response.data[0].tipoInstitucion.id === 4){
            this.router.navigate(['/']);
          }else{
            this.detalleUniversidad = response.data;
            this.zoom = 15;
            this.lat = Number(response.data[0].latitud);
            this.lng = Number(response.data[0].longitud);
          }

        }else{
          console.log('Error al cargar el detalle de la universidad')
        }

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
