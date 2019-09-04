import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router }    from '@angular/router';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';
import swal from 'sweetalert';
import { OfertasService } from '../../../../services/freelancer/ofertas.service';
import { CategoriaService } from '../../../../services/categoria.service';
import { TipoofertaService } from '../../../../services/tipooferta.service';
import { MunicipioService } from '../../../../services/municipio.service';
import { AuthService } from '../../../../shared/guard/auth.service';
import { DepartamentoService } from '../../../../services/departamento.service';
import { InstitucionService } from '../../../../services/institucion.service';




@Component({
  selector: 'app-nueva-oferta',
  templateUrl: './nueva-oferta.component.html',
  styleUrls: ['./nueva-oferta.component.css'],
  providers: [
  	OfertasService,
  	CategoriaService,
    TipoofertaService,
    DepartamentoService,
    MunicipioService,
    InstitucionService
  ]
})
export class NuevaOfertaComponent implements OnInit {

  public oferta: any;
	public categorias: any;
  public tipoOfertas: any;
	public departamentos: any;
  public municipios: any;

  public instituciones: any;

  public submitForm: boolean;
  public lastInsert: any;

  // upload Image
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(private _route: ActivatedRoute,
  	private router: Router,
  	private ofertaService: OfertasService,
    private categoriaService: CategoriaService,
  	private _departamentoService: DepartamentoService,
    private tipoofertaService: TipoofertaService,
    private municipioService: MunicipioService,
    private authService: AuthService,
    private institucionService: InstitucionService) {

      this.initModelOferta();
   }

  ngOnInit() {
    this.getCategorias();
    this.getTipoOfertas();
    this.getinstituciones();
    this.getDepartamentos();
  }

  initModelOferta() {
    this.oferta = {
      idOfrece : 0,
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
      tipoOfrece:  {
        id: 1
      },
      municipio:  {
        id: 0,
        departamento: {
          id: 0,
          pais: {
            id: ''
          }
        }
      },
      descripcion: ''
    };
    this.submitForm = false;
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

  onSubmitForm() {
    console.log('-- model oferta');

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
            console.log('File is completely uploaded!');
           // console.log(event);
            swal('¡Bien hecho!', 'Operación exitosa', 'success' );
            this.initModelOferta();
          }
        },
        error => {
          swal('Upps!', 'Parece que la imagen es muy pesada. Intentalo de nuevo', 'error' );
          console.log(<any>error);
        }
      );
    this.selectedFiles = undefined;
    // this.currentFileUpload = undefined;
  }

}
