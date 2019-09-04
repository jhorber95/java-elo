import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router }    from '@angular/router';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';
import swal from 'sweetalert';

import { OfertaFreelancer } from '../../../../models/freelancer/OfertaFreelancer.interface';

import { CategoriaService } from '../../../../services/categoria.service';
import { TipoofertaService } from '../../../../services/tipooferta.service';
import { DepartamentoService } from '../../../../services/departamento.service';
import { MunicipioService } from '../../../../services/municipio.service';
import { InstitucionService } from '../../../../services/institucion.service';
import { OfertasService } from '../../../services/oferta.service';

@Component({
  selector: 'app-editar-oferta',
  templateUrl: './editar-oferta.component.html',
  styleUrls: ['./editar-oferta.component.css'],
  providers: [
    OfertasService,
    CategoriaService,
    TipoofertaService,
    DepartamentoService,
    MunicipioService,
    InstitucionService
 ]
})
export class EditarOfertaComponent implements OnInit {

  public id;
	public detailOferta: any;
	public students: any;
  public categorias: any;
  public tipoOfertas: any;
	public departamentos: any;
  public municipios: any;

  public instituciones: any;

  public ofertaFreelancer: OfertaFreelancer;

 // upload Image
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(
		private _route: ActivatedRoute,
		private router: Router,
		private ofertasService: OfertasService,
    private categoriaService: CategoriaService,
    private tipoofertaService: TipoofertaService,
    private _departamentoService: DepartamentoService,
    private municipioService: MunicipioService,
    private institucionService: InstitucionService
  ) {
    this._route.params.forEach((params: Params) => {
      this.id = params['id'];
    });

    this.getDetailOferta(this.id);
  }

  ngOnInit() {
    this.getCategorias();
    this.getTipoOfertas();
    this.getinstituciones();
    this.getDepartamentos();
  }

  getDetailOferta(id) {
    // console.log('---cod:' + this.id);
  	this.ofertasService.getDetailOferta(id)
  			.subscribe(
  				response => {
  					this.detailOferta =  response.data;
            this.ofertaFreelancer = this.detailOferta[0];
  					// console.log(response);
            // console.log('---model oferta');
           //  console.log(this.ofertaFreelancer);
  				},
  				error => {
  					console.log(<any>error);
  				}
  			);
  }

  getinstituciones() {
    this.institucionService.getAllIntitciones().subscribe(
      response => {
          this.instituciones = response.data;
      },
      error =>  {
         //  swal('Upps!', 'Hubo un error. Intentalo de nuevo', 'error' );¿
        console.log(<any>error);
      }
    );
  }

  getDepartamentos() {
  	this._departamentoService.getDepartamentos()
  		.subscribe(
  			response => {
  				this.departamentos = response;
  				// console.log(this.departamentos);
  			},
  			error => {
  				console.log(<any>error);
  			}
  		);
  }

  onSelectDepartamento(event) {
    // console.log(event);
    this.municipios = null;
  	this.municipioService.getMunicipioByIdDepartamento(event)
  				.subscribe(
  					response => {
		  				this.municipios = response;
		  				// console.log(this.municipios);
		  			},
		  			error => {
		  				console.log(<any>error);
		  			}
  				);
  	// console.log(this.allDataUser.municipio.departamento.id);
  }

  getCategorias() {
    this.categoriaService.getCategorias()
        .subscribe(
          response => {
             this.categorias = response;
             // console.log(response);
          },
          error =>  {
            console.log(<any>error);
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
          console.log(<any>error);
        }
       );
  }

  onSubmitFormOfertas() {
    // console.log('--- model finished');
   // console.log(this.ofertaFreelancer);
    this.ofertasService.updateOferta(this.ofertaFreelancer)
      .subscribe(
        response => {
          swal('¡Bien hecho!', 'Operación exitosa', 'success' );
         // console.log(response);
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
    this.ofertasService.uploadImage(this.currentFileUpload, this.id )
      .subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            console.log('File is completely uploaded!');
            // this.getDetailOferta();
            // console.log(event);
            swal('¡Bien hecho!', 'Operación exitosa', 'success' );
          }
        },
        error => {
          console.log(<any>error);
        }
      );
    this.selectedFiles = undefined;
    // this.currentFileUpload = undefined;
  }

}
