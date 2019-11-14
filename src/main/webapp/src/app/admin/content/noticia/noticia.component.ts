import { Component, OnInit } from '@angular/core';
import {NoticiaService} from './noticia.service';
import {INoticia} from '../../../models/noticia';
import {Router} from '@angular/router';
import swal from 'sweetalert';

@Component({
  selector: 'app-noticia',
  templateUrl: './noticia.component.html',
  styles: []
})
export class NoticiaComponent implements OnInit {

  noticias: INoticia [];
  totalNews: number = 0;
  itemsPerPage = 10;
  page = 1;
  previousPage: any;


  constructor(private noticiaService: NoticiaService, private router: Router) { }

  ngOnInit() {
    this.getAllNoticias();
  }

  getAllNoticias() {
    this.noticiaService.getAll(this.page -1, this.itemsPerPage).subscribe(
      res => {
        this.totalNews = res.totalElements;
        this.noticias = res.content;
      }
    );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.getAllNoticias();
    }
  }

  createNews() {
    this.router.navigate(['/admin/lista-noticias/create']);
  }

  deleteNews(id: number) {
    swal({
      title: 'Está seguro que quiere eliminar este noticia?',
      text: 'Tenga en cuenta que la información no se podrá recuperar!',
      icon: 'warning',
      buttons: ['Cancelar', 'Aceptar'],
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          this.noticiaService.delete(id).subscribe(
            res => {
              this.getAllNoticias();
              swal('¡Bien hecho!', 'Operación exitosa', 'success' );
            }, error => {
              swal('Error!', 'Ocurrrió algo inesperado, por favor intente más tarde', 'error' );
            }
          );
        } else {
          swal('Por el momento todo seguira igual.');
        }
      });

  }
}
