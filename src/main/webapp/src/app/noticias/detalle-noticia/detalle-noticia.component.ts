import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {INoticia} from '../../models/noticia';
import {NoticiaService} from '../../admin/content/noticia/noticia.service';

@Component({
  selector: 'app-detalle-noticia',
  templateUrl: './detalle-noticia.component.html',
  styles: [],
  providers: [NoticiaService]
})
export class DetalleNoticiaComponent implements OnInit, AfterViewInit {

  noticia: INoticia;
  idNoticia: number;

  constructor(private activeRoute: ActivatedRoute, private noticiaService: NoticiaService, private router: Router) {
  }

  ngOnInit() {
    this.activeRoute.params.forEach((params: Params) => {
        this.idNoticia = params['idNoticia']
      }
    );
    if (this.idNoticia) {
      this.getNoticia(this.idNoticia)
    } else {
      this.router.navigate(['/']);
    }
  }

  getNoticia(idNoticia) {
    this.noticiaService.getOne(idNoticia).subscribe(
      res => {
        this.noticia = res
        console.log(res)
      }, error => console.log(error)
    );
  }

  ngAfterViewInit() {
    window.scrollTo(0, 0);
    $(function () {
      $('.preloader').fadeOut();
    });
    // tooltip
    $(function () {
      (<any>$('[data-toggle="tooltip"]')).tooltip();
    });

    // popover

    $(function () {
      (<any>$('[data-toggle="popover"]')).popover();
    });

    $('.multi-item-carousel .carousel-item').each(function () {
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
