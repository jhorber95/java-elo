import {AfterViewInit, Component, OnInit} from '@angular/core';
import {INoticia} from '../models/noticia';
import {NoticiaService} from '../admin/content/noticia/noticia.service';

@Component({
  selector: 'app-noticias',
  templateUrl: './noticias.component.html',
  styleUrls: ['./noticias.component.css'],
  providers: [NoticiaService]
})
export class NoticiasComponent implements OnInit, AfterViewInit {

  noticias: INoticia[];
  constructor(private  noticiaService: NoticiaService) { }

  page = 0;
  totalPerPage = 12;

  ngOnInit() {
    this.getAllNoticias();
  }

  getAllNoticias() {
    this.noticiaService.getAll(this.page, this.totalPerPage).subscribe(
      res => {
        this.noticias = res.content;
      }, error => console.log(error)
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
}
