import {Component, OnInit, AfterViewInit, OnDestroy} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
// wowngx
import { NgwWowService } from 'ngx-wow';
import 'rxjs/add/operator/filter';
import { Subscription }   from 'rxjs/Subscription';
import {FormDataTestService} from '../services/form-data-test.service';

@Component({
  selector: 'app-guiavocacional',
  templateUrl: './guiavocacional.component.html',
  styleUrls: ['./guiavocacional.component.css'],
  providers: [FormDataTestService]
})
export class GuiavocacionalComponent implements OnInit, AfterViewInit, OnDestroy {

  private wowSubscription: Subscription;
  public Ad1: any = {
    id: 0,
    imagen: '',
    url: ''
  };
  public Ad2: any = {
    id: 0,
    imagen: '',
    url: ''
  };
  public Ad3: any = {
    id: 0,
    imagen: '',
    url: ''
  };

  constructor(private router: Router,
              private wowService: NgwWowService,
              private formDataTestServices: FormDataTestService) {
    // wowngx
    this.router.events.filter(event => event instanceof NavigationEnd).subscribe(event => {
      // Reload WoW animations when done navigating to page,
      // but you are free to call it whenever/wherever you like
      this.wowService.init();
    });
    // wowngx
  }

  ngOnInit() {
    this.listarAds();
    // you can subscribe to WOW observable to react when an element is revealed
    this.wowSubscription = this.wowService.itemRevealed$.subscribe(
      (item:HTMLElement) => {
        // do whatever you want with revealed element
      });
  }

  ngAfterViewInit() {
    window.scrollTo(0, 0);

      $(function () {
        $('.preloader').fadeOut();
      });

        $(function() {
            (<any>$('[data-toggle="tooltip"]')).tooltip();
        });

    }

  ngOnDestroy() {
    // unsubscribe (if necessary) to WOW observable to prevent memory leaks
    this.wowSubscription.unsubscribe();
  }

  listarAds() {
    this.formDataTestServices.listarAds().subscribe(
      response => {
        console.log(response);
        if (response.exito === true) {
          for (let i = 0; i < response.data.length; i++) {
            switch (response.data[i].id) {
              case 1: {
                this.Ad1.id = response.data[i].id;
                this.Ad1.imagen = '/api/publicidad/banner/' + response.data[i].imagen;
                this.Ad1.url = response.data[i].url;
                break;
              }
              case 2: {
                this.Ad2.id = response.data[i].id;
                this.Ad2.imagen = '/api/publicidad/banner/' + response.data[i].imagen;
                this.Ad2.url = response.data[i].url;
                break;
              }
              case 3: {
                this.Ad3.id = response.data[i].id;
                this.Ad3.imagen = '/api/publicidad/banner/' + response.data[i].imagen;
                this.Ad3.url = response.data[i].url;
                break;
              }
            }
          }

        }
      },
      error => {
        console.log(<any>error);
      }
    );
  }

}
