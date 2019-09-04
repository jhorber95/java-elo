import * as $ from 'jquery';
import {EventosService} from '../../../services/eventos/eventos.service';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';


@Component({
  selector: 'app-detalle',
  templateUrl: './detalle.component.html',
  styleUrls: ['./detalle.component.css'],
  providers: [EventosService]
})
export class DetalleComponent implements OnInit, AfterViewInit {

  public Eventos;
  public id;

  constructor(
    private _eventosService: EventosService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit() {
    this._route.params.forEach((params: Params) => {
      this.id = params['id'];
    });
    this.getDetalleEvento();
  }

  ngAfterViewInit() {
    window.scrollTo(0, 0);

    $(function() {
      $('.preloader').fadeOut();
    });
  }

  getDetalleEvento() {
    this._eventosService.getDetalleEvento(this.id).subscribe(
      response => {
        if (response.codigo !== 200) {
          console.log('Error en la api');
        }else {
          this.Eventos = response.data;
          console.log(this.Eventos);
        }
      },
      error => {
        console.log(<any>error);
      }
    );
  }

}
