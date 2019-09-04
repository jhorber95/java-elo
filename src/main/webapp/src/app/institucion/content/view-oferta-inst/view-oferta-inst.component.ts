import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router }    from '@angular/router';
import { OfertasService } from '../../../services/freelancer/ofertas.service';
import { CategoriaService }         from '../../../services/categoria.service';
import { TipoofertaService }        from '../../../services/tipooferta.service';
import { HttpResponse, HttpEventType } from '@angular/common/http';


import swal from 'sweetalert';

import { OfertaFreelancer, SubCategoria } from '../../../models/freelancer/OfertaFreelancer.interface';

@Component({
  selector: 'ins-view-oferta-inst',
  templateUrl: './view-oferta-inst.component.html',
  styleUrls: ['./view-oferta-inst.component.css'],
  providers: [
      OfertasService, 
      CategoriaService,
      TipoofertaService
   ]
})
export class ViewOfertaInstComponent implements OnInit {

  
	public id;
	public detailOferta: any;
	public students: any;
  public categorias: any;
  public tipoOfertas: any;

 public ofertaFreelancer: OfertaFreelancer;

 // upload Image
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(
    private _route: ActivatedRoute,
		private router: Router,
		private ofertasFreelancerService: OfertasService,
    private categoriaService: CategoriaService,
    private tipoofertaService: TipoofertaService
    ) {

    this._route.params.forEach((params: Params) => {
      this.id = params['id'];
    });

    this.getDetailOferta();
  }

  ngOnInit() {
    this.getCategorias();
    this.getTipoOfertas();
  }

  getCategorias() {
    this.categoriaService.getCategorias()
        .subscribe(
          response => {
             this.categorias = response;
             // console.log(response);
          },
          error =>  {
            console.log(<any>error)
          }
        );
  }
  getTipoOfertas() {
     this.tipoofertaService.getTipoOferta()
         .subscribe(
           response => {
             this.tipoOfertas = response;
            // console.log(response);
           },
           error =>  {
            console.log(<any>error)
          }
         );
  }

  getDetailOferta() {
    // console.log('---cod:' +this.id);
  	this.ofertasFreelancerService.getDetailOferta(this.id)
  			.subscribe(
  				response => {
  					this.detailOferta =  response.data;
            this.ofertaFreelancer = this.detailOferta[0];
  					// console.log(response);
            // console.log('---model oferta');
            // console.log(this.ofertaFreelancer);
  				},
  				error => {
  					console.log(<any>error);
  				}
  			);
  }

  onSubmitFormOfertas() {
    // console.log('--- model finished');
     console.log(this.ofertaFreelancer);
    this.ofertasFreelancerService.updateOferta(this.ofertaFreelancer)
      .subscribe(
        response => {
          swal('¡Bien hecho!', 'Operación exitosa', 'success' );
          // console.log(response);
          this.getDetailOferta();
        },
        error => {
          swal('¡Error!', 'Lo sentimos, halgo salio mal. Intentalo nuevamente.', 'error');
          console.log(<any>error);
        }
      );
  }

   selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload() {
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFiles.item(0);
    this.ofertasFreelancerService.uploadImage(this.currentFileUpload, this.id )
      .subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            console.log('File is completely uploaded!');
            this.getDetailOferta();
						swal('¡Bien hecho!', 'Operación exitosa', 'success' );
          }

          console.log(event);

        },
        error => {
          console.log(<any>error);
        }
      );
    this.selectedFiles = undefined;
    // this.currentFileUpload = undefined;
  }

}
