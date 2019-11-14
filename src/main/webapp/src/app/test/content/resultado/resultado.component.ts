import {Component, OnInit, AfterViewInit, Input, OnDestroy} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Params, Router} from '@angular/router';

import {FormDataTestService} from '../../../services/form-data-test.service';
import {WorkflowtestService} from '../../../shared/workflowtest/workflowtest.service';
import {STEPS} from '../../../shared/workflowtest/workflow.model';
import {ResultData} from '../../../models/test/result-data';

// wowngx
import {NgwWowService} from 'ngx-wow';
import 'rxjs/add/operator/filter';
import {Subscription} from 'rxjs/Subscription';
import {AuthService} from '../../../shared/guard/auth.service';

// sweat alert
import swal from 'sweetalert';
import {TestHistoryService} from '../../../admin/content/test-history/test-history.service';

@Component({
  selector: 'app-resultado',
  templateUrl: './resultado.component.html',
  styleUrls: ['./resultado.component.css']
})
export class ResultadoComponent implements OnInit, AfterViewInit, OnDestroy {

  private wowSubscription: Subscription;

  public resultTest: ResultData;
  public error = '';
  public Ad3: any = {
    id: 0,
    imagen: '',
    url: ''
  };

  constructor(private router: Router,
              private wowService: NgwWowService,
              private _route: ActivatedRoute,
              private formDataTestService: FormDataTestService,
              private workFlowTestService: WorkflowtestService,
              private testService: TestHistoryService,
              private authService: AuthService) {
    // wowngx
    this.router.events.filter(event => event instanceof NavigationEnd).subscribe(event => {
      // Reload WoW animations when done navigating to page,
      // but you are free to call it whenever/wherever you like
      this.wowService.init();
    });
    // wowngx
  }


  ngOnInit() {
    // you can subscribe to WOW observable to react when an element is revealed
    this.wowSubscription = this.wowService.itemRevealed$.subscribe(
      (item: HTMLElement) => {
        // do whatever you want with revealed element
      });
    this.listarAds();
    this.resultTest = this.formDataTestService.getTestResult();
    sessionStorage.setItem('resultTest', JSON.stringify(this.resultTest));
    sessionStorage.setItem('stepTest', JSON.stringify(this.formDataTestService.getFormDataTestSubmit()));
  }

  ngOnDestroy() {
    // unsubscribe (if necessary) to WOW observable to prevent memory leaks
    this.wowSubscription.unsubscribe();
  }

  ngAfterViewInit() {
    window.scrollTo(0, 0);

    $(function () {
      $('.preloader').fadeOut();
    });
  }

  NextFase() {
    console.log(this.resultTest);
    this.workFlowTestService.validateStep(STEPS.resultado);
    // this.workFlowTestService.resetSteps();

    console.log('get ofertas guia vocacional');

    this.formDataTestService.getOfertasTest(this.resultTest).subscribe(
      response => {
        if (response.codigo === 200) {
          // agregamos el resultado del test al sessionStore
          sessionStorage.setItem('testVocacional', JSON.stringify(response.data));
          // verificamos si esta logeado
          if (localStorage.getItem('token') && this.authService.isAuthenticated()) {
            // redirigir

            const _test = this.formDataTestService.getFormDataTestSubmit();
            const _user = JSON.parse(localStorage.getItem('user'));

            const entity: any = {
              user: _user,
              test: _test
            };
            // debugger;
            this.testService.sendUserTest(entity).subscribe(
              res => {
                console.log(res)
              }, error1 => console.log(error1)
            );

            this.router.navigate(['/ofertas/guiavocacional']);
          } else {
            // modal para registrar o logear
            // tslint:disable-next-line:max-line-length
            swal('Buen Trabajo', 'Ya casi terminas, solo debes registrarte o iniciar sesión para poder ver el resultado de tu test vocacional.', 'info', {
              buttons: {
                registro: {
                  text: 'Registrarme',
                  value: 'registro',
                },
                login: {
                  text: 'Iniciar Sesión',
                  value: 'login',
                },
              },
            })
              .then((value) => {
                switch (value) {

                  case 'registro':
                    sessionStorage.setItem('redirect-test', 'true');
                    // redirigir
                    this.router.navigate(['/registrate']);
                    break;

                  case 'login':
                    sessionStorage.setItem('redirect-test', 'true');
                    // redirigir
                    this.router.navigate(['/login']);
                    break;

                }
              });
          }

        } else {
          console.log('No se pudieron obtener las ofertas del test vocacional');
        }
      },
      error => {
        console.log(<any>error);
      }
    );


    this.error = 'Error, seleccione mínimo 2 y máximo 4 imagenes por fase';

  }

  listarAds() {
    this.formDataTestService.listarAds().subscribe(
      response => {
        if (response.exito === true) {
          for (let i = 0; i < response.data.length; i++) {
            switch (response.data[i].id) {
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
