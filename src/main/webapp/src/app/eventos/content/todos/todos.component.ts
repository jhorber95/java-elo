import * as $ from 'jquery';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { EventosService } from '../../../services/eventos/eventos.service';


@Component({
  selector: 'app-todos',
  templateUrl: './todos.component.html',
  styleUrls: ['./todos.component.css'],
  providers: [EventosService]
})
export class TodosComponent implements OnInit, AfterViewInit  {


  public Eventos;

  constructor(
    private _eventosService: EventosService
  ) {

   }

  ngOnInit() {
    this.getEventos();
  }

  ngAfterViewInit() {
    window.scrollTo(0, 0);
    
        $(function() {
            $('.preloader').fadeOut();
        });
  }

  getEventos() {
    this._eventosService.getEventos().subscribe(
      response => {
        this.Eventos = response.data;
      },
      error => {
        var errorMessage = <any>error;
        console.log(errorMessage);
      }
    );
  }

}
