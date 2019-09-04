import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router }    from '@angular/router';
import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';

import swal from 'sweetalert';


import { OfertasService }   from '../../../services/freelancer/ofertas.service';
import { CategoriaService } from '../../../services/categoria.service';
import { TipoofertaService }from '../../../services/tipooferta.service';
import { MunicipioService } from '../../../services/municipio.service';
import { AuthService }      from '../../../shared/guard/auth.service';

@Component({
  selector: 'app-create-oferta',
  templateUrl: './create-oferta.component.html',
  styleUrls: ['./create-oferta.component.css'],
  providers: [
  	OfertasService,
  	CategoriaService,
  	TipoofertaService
  ]
})
export class CreateOfertaComponent implements OnInit {

	public oferta: any;
	public categorias: any;
  public tipoOfertas: any;
  public municipios: any;
  public submitForm: boolean;
  public lastInsert: any;

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
    private municipioService: MunicipioService,
    private authService: AuthService) {

    this.oferta = {
      idOfrece : this.getDataUser(),
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
        id: 0
      },
      modalidad: {
        id: 0
      },
      tipoOfrece:  {
        id: 2
      },
      municipio:  {
        id: 0
      },
      descripcion: ''
    };
    this.submitForm = false;
  }

  ngOnInit() {
  	this.getCategorias();
	  this.getTipoOfertas();
    this.getMunicipios();
  }

  getDataUser() {
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
              console.log(<any>error)
            }
          );
  }
  getTipoOfertas(){
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
  getMunicipios(){
     this.municipioService.getMunicipio()
         .subscribe(
           response => {
             this.municipios = response;
            // console.log(response);
           },
           error =>  {
            console.log(<any>error)
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
         swal('¡Error!','Ocurrió algo inesperado, por favor revise de nuevo el formulario y cerciórese que este correcto', 'error' );

        console.log(<any>error)
      }
     );
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }
 
  upload() {
    this.progress.percentage = 0;
 
    this.currentFileUpload = this.selectedFiles.item(0);
    this.ofertaService.uploadImage(this.currentFileUpload,this.lastInsert )
      .subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
          } else if (event instanceof HttpResponse) {
            console.log('File is completely uploaded!');
           // console.log(event);
            swal('¡Bien hecho!','Operación exitosa', 'success' );
          }
          

        },
        error => {
          swal('Upps!','Parece que la imagen es muy pesada. Intentalo de nuevo', 'error' );
          console.log(<any>error);
        }
      );
 
    this.selectedFiles = undefined;
    // this.currentFileUpload = undefined;
  }
}
