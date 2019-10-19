import { Component, OnInit, AfterViewInit, OnDestroy, ViewChild, TemplateRef } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { HomecursosService } from '../services/homecursos.service';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';

import { NgbModal} from '@ng-bootstrap/ng-bootstrap';


import { Search } from '../models/search';

import { NgxCarousel } from 'ngx-carousel';
// wowngx
import { NgwWowService } from 'ngx-wow';
import 'rxjs/add/operator/filter';
import { Subscription }   from 'rxjs/Subscription';
import { AuthService } from '../shared/guard/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [HomecursosService, NgbCarouselConfig],
})
export class HomeComponent implements OnInit, AfterViewInit, OnDestroy {
  lat = 51.678418;
  lng = 7.809007;

  private wowSubscription: Subscription;

  public carouselTileItems: Array<any>;
  public carouselTile: NgxCarousel;
  public carousetTileEventos: NgxCarousel;
  public cursos;
  public Eventos;
  public ObjetoEventos;
  public Financieras;
  public ObjetoFinancieras;
  public isLoged: boolean;

  public inputbuscador: Search;

  closeResult: string;

  @ViewChild('modalEstudialo') modalContent: TemplateRef<any>;

  constructor(private router: Router,
      private _homecursosService: HomecursosService,
      private modalService: NgbModal,
    private authService: AuthService,
    private wowService: NgwWowService) {



    this.inputbuscador = new Search('');
    // wowngx
    this.router.events.filter(event => event instanceof NavigationEnd).subscribe(event => {
      // Reload WoW animations when done navigating to page,
      // but you are free to call it whenever/wherever you like
      this.wowService.init();
    });
    // wowngx
    this.isLoged = false;
    if (localStorage.getItem('token') && this.authService.isAuthenticated()) {
        this.isLoged = true;
    }
    // setTimeout(() => {
    //   this.showModal();
    // }, 1000);

  }

  // showModal() {
  //   this.modalService.open(this.modalContent,  { windowClass: 'animated pulse'});
  // }

  ngOnInit() {
    // you can subscribe to WOW observable to react when an element is revealed
    this.wowSubscription = this.wowService.itemRevealed$.subscribe(
      (item: HTMLElement) => {
        // do whatever you want with revealed element
      });
    this._homecursosService.getCursosHome().subscribe(
      result => {
        this.cursos = result.data;
        this.wowService.init();
      },
      error => {
        console.log(<any>error);
      }
    );

    this._homecursosService.getEventosHome().subscribe(
      result => {
        this.Eventos = result.data;
      },
      error => {
        console.log(<any>error);
      }
    );

    this.getFinancierasLimit(1, 6);
    this.getEventosLimit(1, 6);
    // carousel slider column
    this.carousetTileEventos = {
      grid: {xs: 1, sm: 3, md: 3, lg: 4, all: 0},
      slide: 2,
      speed: 400,
      animation: 'lazy',
      point: {
        visible: true
      },
      load: 2,
      touch: true,
      easing: 'ease'
    };

    this.carouselTile = {
      grid: {xs: 2, sm: 3, md: 3, lg: 4, all: 0},
      slide: 2,
      speed: 400,
      animation: 'lazy',
      point: {
        visible: true
      },
      load: 2,
      touch: true,
      easing: 'ease'
    };

  }

  public carouselTileLoadEventos(evt: any) {

    const len = this.Eventos.length;

    this._homecursosService.getEventosLimit(len + 1, 2).subscribe(
      response => {
        this.ObjetoEventos = response.data;
        for (let i = 0; i < this.ObjetoEventos.length; i++) {
          this.Eventos.push(this.ObjetoEventos[i]);
        }
      },
      error => {
        console.log(<any>error);
      }
    );

  }

  public carouselTileLoad(evt: any) {

    const len = this.Financieras.length;

    this._homecursosService.getFinancierasLimit(len + 1, 2).subscribe(
      response => {
        this.ObjetoFinancieras = response.data;
        for (let i = 0; i < this.ObjetoFinancieras.length; i++) {
          this.Financieras.push(this.ObjetoFinancieras[i]);
        }
      },
      error => {
        console.log(<any>error);
      }
    );

  }

  getEventosLimit(start: number, length: number) {
    this._homecursosService.getEventosLimit(start, length).subscribe(
      response => {
        this.Eventos = response.data;
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  getFinancierasLimit(start: number, length: number) {
    this._homecursosService.getFinancierasLimit(start, length).subscribe(
      response => {
        this.Financieras = response.data;
      },
      error => {
        console.log(<any>error);
      }
    );
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

  // metodo click boton buscador

  clickBuscador() {
   this.router.navigate(['/ofertas/busqueda', this.inputbuscador.search]);
  }

  ngOnDestroy() {
    // unsubscribe (if necessary) to WOW observable to prevent memory leaks
    this.wowSubscription.unsubscribe();
  }

  testVocacional() {
    this.router.navigate(['/test/fase1']);
  }

}
