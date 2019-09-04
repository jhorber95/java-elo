import { Component, OnInit } from '@angular/core';

import swal from 'sweetalert';
import {AdminService} from "../../services/admin.service";
import {HttpEventType, HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-admin-publicidad',
  templateUrl: './admin-publicidad.component.html',
  styleUrls: ['./admin-publicidad.component.css'],
  providers: []
})
export class AdminPublicidadComponent implements OnInit {

	public institucion: any;
	public Adform1: any = {};
  public Adform2: any = {};
  public Adform3: any = {};
	public Ad1: any = {
	  id: 0,
	  imagen: '',
    url: ''
  };
	public Ad2: any = {
    id: 0,
    imagen: '',
    url: ''
  };
	public Ad3: any = {
    id: 0,
    imagen: '',
    url: ''
  };

  // upload Image
  selectedFiles: FileList;
  currentFileUpload: File;
  progress: { percentage: number } = { percentage: 0 };

  constructor(private adminServices : AdminService) {

    this.institucion = {
      nombre : '',
      nit: '',
      direccion: '',
      latitud: '',
      longitud: '',
      telefono: '',
      url: '',
      descripción: '',
      tipoInstitucion: {
        id: 1
      },
      email: '',
      password: ''
    };

  }

  ngOnInit() {
    this.listarAds();
  }

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }

  upload1() {
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFiles.item(0);
    this.adminServices.uploadImageAd(this.currentFileUpload, this.Adform1.url, 1 )
      .subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
            this.listarAds();
          } else if (event instanceof HttpResponse) {
            console.log('File is completely uploaded!');
            this.listarAds();
            swal('¡Bien hecho!','Operación exitosa', 'success' )
              .then((value) => {
                location.reload();
              });
          }
        },
        error => {
          this.listarAds();
          swal('¡Error!','Intentelo de nuevo', 'error' );
          console.log(<any>error);
        }
      );

    this.selectedFiles = undefined;
    // this.currentFileUpload = undefined;
  }

  upload2() {
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFiles.item(0);
    this.adminServices.uploadImageAd(this.currentFileUpload, this.Adform2.url, 2 )
      .subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
            this.listarAds();
          } else if (event instanceof HttpResponse) {
            console.log('File is completely uploaded!');
            this.listarAds();
            swal('¡Bien hecho!','Operación exitosa', 'success' )
              .then((value) => {
                location.reload();
              });
          }
        },
        error => {
          this.listarAds();
          swal('¡Error!','Intentelo de nuevo', 'error' );
          console.log(<any>error);
        }
      );

    this.selectedFiles = undefined;
    // this.currentFileUpload = undefined;
  }

  upload3() {
    this.progress.percentage = 0;

    this.currentFileUpload = this.selectedFiles.item(0);
    this.adminServices.uploadImageAd(this.currentFileUpload, this.Adform3.url, 3 )
      .subscribe(
        event => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progress.percentage = Math.round(100 * event.loaded / event.total);
            this.listarAds();
          } else if (event instanceof HttpResponse) {
            console.log('File is completely uploaded!');
            this.listarAds();
            swal('¡Bien hecho!','Operación exitosa', 'success' )
              .then((value) => {
                location.reload();
              });
          }
        },
        error => {
          this.listarAds();
          swal('¡Error!','Intentelo de nuevo', 'error' );
          console.log(<any>error);
        }
      );

    this.selectedFiles = undefined;
    // this.currentFileUpload = undefined;
  }

  listarAds(){
    this.adminServices.listarAds().subscribe(
      response => {
        if(response.exito === true) {
          for(let i = 0; i<response.data.length; i++) {
            switch (response.data[i].id){
              case 1: {
                this.Ad1.id = response.data[i].id;
                this.Ad1.imagen = "/api/publicidad/banner/"+response.data[i].imagen;
                this.Ad1.url = response.data[i].url;
                break;
              }
              case 2: {
                this.Ad2.id = response.data[i].id;
                this.Ad2.imagen = "/api/publicidad/banner/"+response.data[i].imagen;
                this.Ad2.url = response.data[i].url;
                break;
              }
              case 3: {
                this.Ad3.id = response.data[i].id;
                this.Ad3.imagen = "/api/publicidad/banner/"+response.data[i].imagen;
                this.Ad3.url = response.data[i].url;
                break;
              }
            }
          }
          console.log(this.Ad1);

        }
      },
      error => {
        console.log(<any>error);
      }
    );
  }


}
