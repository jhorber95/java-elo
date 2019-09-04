import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router }    from '@angular/router';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';

import { Subject } from 'rxjs/Subject';

import swal from 'sweetalert';


import { OfertasService }   from '../../../services/freelancer/ofertas.service';
import { CategoriaService } from '../../../services/categoria.service';
import { TipoofertaService }from '../../../services/tipooferta.service';
import { MunicipioService } from '../../../services/municipio.service';
import { AuthService }      from '../../../shared/guard/auth.service';
import { DepartamentoService } from '../../../services/departamento.service';


@Component({
  selector: 'ins-create-oferta-inst',
  templateUrl: './create-oferta-inst.component.html',
  styleUrls: ['./create-oferta-inst.component.css'],
  providers: [
  	OfertasService,
  	CategoriaService,
  	TipoofertaService
  ]
})
export class CreateOfertaInstComponent implements OnInit {

  public oferta: any;
	public categorias: any;
  public tipoOfertas: any;
	public departamentos: any;
  public municipios: any;
  public submitForm: boolean;
  public lastInsert: any;

  public ofertas: any;
  public ofertaFromSearch: any;

  // upload Image
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };


  constructor(
  	private _route: ActivatedRoute,
  	private router: Router,
  	private ofertaService: OfertasService,
    private categoriaService: CategoriaService,
    private tipoofertaService: TipoofertaService,
  	private _departamentoService: DepartamentoService,
    private municipioService: MunicipioService,
    private authService: AuthService) {

      this.initializeOferta();

  }

  ngOnInit() {
  	this.getCategorias();
	  this.getTipoOfertas();
    this.getDepartamentos();
   // this.getListOfertas();
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

  getlistOferts(event) {

   // setTimeout(() => {
      // console.log(event);
      this.ofertaService.getOfertaByItemSearch(event).subscribe(
        response => {
          this.ofertas = response.data;
          // console.log(response);
        },
        error => {
          console.log(<any>error);
        }
      );
    // }, 5000);
  }

  clickLiveSearch(index) {
   // console.log(this.ofertas[index]);

    this.oferta.titulo = this.ofertas[index].titulo;
    this.oferta.categoria.id = this.ofertas[index].categoria.id;
    this.oferta.tipoOferta.id = this.ofertas[index].tipoOferta.id;

    this.ofertas = null;

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


  initializeOferta() {
    this.oferta = {
      idOfrece : this.getIdUser(),
      titulo: '',
      precio: '',
      telefono: '',
      categoria: {
        id: 0
      },
      tipoOferta:  {
        id: 0
      },
      jornada: {
        id: 4
      },
      modalidad: {
        id: 4
      },
      /*
		Id del tipo de institucion
      */
      tipoOfrece:  {
        id: 1
      },
      municipio:  {
        id: '',
        departamento: {
          id: 0
        }
      },
      descripcion: ''
    };

    this.submitForm = false;
  }

  getIdUser() {
    const dataUser = this.authService.getAllDataUser();
    return dataUser.id;
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
  getMunicipios() {
     this.municipioService.getMunicipio()
         .subscribe(
           response => {
             this.municipios = response;
            // console.log(response);
           },
           error =>  {
            console.log(<any>error);
          }
         );
  }

  onSubmitForm() {
    // console.log('-- model oferta');
    this.submitForm = true;
    this.ofertaService.createOferta(this.oferta)
      .subscribe(
        response => {
         // this.tipoOfertas = response;
         // console.log(response);
         // console.log(this.oferta);

         this.lastInsert = response.body.id;
         // console.log('--id last insert' + this.lastInsert );
       },
       error =>  {
          swal('¡Error!', 'Ocurrió algo inesperado, por favor revise de nuevo el formulario y cerciórese que este correcto', 'error' );
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
    this.ofertaService.uploadImage(this.currentFileUpload, this.lastInsert )
      .subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            swal('¡Bien hecho!', 'Operación exitosa', 'success' );
            this.initializeOferta();

          }
          // console.log(event);
        },
        error => {
          console.log(<any>error);
          swal('¡Error!', 'Imagen muy pesada', 'error' );
        }
      );
    this.selectedFiles = undefined;
    // this.currentFileUpload = undefined;
  }
}
