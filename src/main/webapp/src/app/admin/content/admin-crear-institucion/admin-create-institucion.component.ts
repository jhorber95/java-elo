import { Component, OnInit } from '@angular/core';

import { HttpClient, HttpResponse, HttpEventType } from '@angular/common/http';

import swal from 'sweetalert';
import {AdminService} from "../../services/admin.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-admin-create-institucion',
  templateUrl: './admin-create-institucion.component.html',
  styleUrls: ['./admin-create-institucion.component.css'],
  providers: []
})
export class AdminCreateInstitucionComponent implements OnInit {

	public institucion: any;

  constructor(private adminServices : AdminService,
              private router: Router) {

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
  }

  onSubmitForm() {
    this.adminServices.crearInstitucion(this.institucion).subscribe(
        response => {
          if(response.exito === true) {
            swal("Buen trabajo!", "Institución agregada con éxito", "success")
              .then((value) => {
                this.router.navigate(['/admin/admin-instituciones']);
              });
          }else {
            swal("Algo a salido mal", "Error al agregar Institución", "error");
          }
       },
       error =>  {
         swal("Error", "Error al agregar Institución", "error");
        console.log(<any>error)
      }
     );
  }

}
