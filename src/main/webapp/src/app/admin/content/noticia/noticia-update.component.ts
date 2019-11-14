import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {NoticiaService} from './noticia.service';
import {HttpEventType, HttpResponse} from '@angular/common/http';
import swal from 'sweetalert';
import {INoticia, Noticia} from '../../../models/noticia';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-noticia-update',
  templateUrl: './noticia-update.component.html',
  styles: []
})
export class NoticiaUpdateComponent implements OnInit {

  newsContent: any = {
    editorData: '<p>Hello, world!</p>'
  };

  noticia: INoticia;

  currentUser: any = JSON.parse(localStorage.getItem('user'));

  // upload Image
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = {percentage: 0};

  formEdit = this.fb.group({
    id: [],
    titulo: ['', [Validators.required]],
    descripcion: ['', [Validators.required]],
    imagenPrincipal: ['', [Validators.required]],
    contenido: ['', [Validators.required]],
  });

  constructor(
    private fb: FormBuilder,
    private newsService: NoticiaService,
    protected activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({noticia}) => {
      this.updateForm(noticia);
      this.noticia = noticia;
      console.log('new', noticia);
    });
  }

  async save() {
    const entity = this.createEntity();

    if (this.selectedFiles != undefined ) {
      entity.imagenPrincipal = <any>await this.upload();
    }

    if (entity.id !== undefined) {
      entity.createdAt = new Date().getTime();
      this.newsService.update(entity).subscribe(
        res => {
          console.log(res);
          swal('¡Bien hecho!', 'Operación exitosa', 'success');

          this.previousState();
        }, error => {
          swal('Error!', 'Lo sientimos, algo inesperado paso. Intente de nuevo', 'Error');
          console.log(error)
        }
      );
    } else {
      this.newsService.create(entity).subscribe(
        res => {
          console.log(res);
          swal('¡Bien hecho!', 'Operación exitosa', 'success');
          this.previousState();
        }, error => {
          swal('Error!', 'Lo sientimos, algo inesperado paso. Intente de nuevo', 'Error');

        }
      );
    }
  }

  private createEntity(): INoticia {
    return {
      ...new Noticia(),
      id: this.formEdit.get(['id']).value,
      titulo: this.formEdit.get(['titulo']).value,
      descripcion: this.formEdit.get(['descripcion']).value,
      contenido: this.newsContent.editorData,
      autor: this.currentUser.id,
      imagenPrincipal: this.noticia.imagenPrincipal
    }
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  private updateForm(news: INoticia) {
    this.formEdit.patchValue({
      id: news.id,
      titulo: news.titulo,
      descripcion: news.descripcion,
    });
    this.newsContent.editorData = news.contenido;
  }

  upload() {
    this.progress.percentage = 0;
    this.currentFileUpload = this.selectedFiles.item(0);

    return new Promise((resolve, reject) => {
      this.newsService.uploadImage(this.currentFileUpload)
        .subscribe(
          (event: any) => {
            if (event.type === HttpEventType.UploadProgress) {
              this.progress.percentage = Math.round(100 * event.loaded / event.total);
            } else if (event instanceof HttpResponse) {
              console.log('File is completely uploaded!');
              const response: any = JSON.parse(event.body);

              console.log(response);
              resolve(response.body);
              // swal('¡Bien hecho!', 'Operación exitosa', 'success' );
              // this.initModelOferta();
            }
          },
          error => {
            swal('Upps!', 'Parece que la imagen es muy pesada. Intentalo de nuevo', 'error');
            console.log(<any>error);
            reject(error);
          }
        );
      this.selectedFiles = undefined;
      this.currentFileUpload = undefined;
    });
  }


  previousState() {
    window.history.back();
  }
}
