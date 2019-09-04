import { Component, OnInit } from '@angular/core';
import { OfertasService } from '../../../services/freelancer/ofertas.service';
import { AuthService } from '../../../shared/guard/auth.service';
import swal from 'sweetalert';


@Component({
  selector: 'ins-ofertas',
  templateUrl: './ofertas.component.html',
  styleUrls: ['./ofertas.component.css'],
  providers: [
  		OfertasService
  ]
})
export class OfertasComponent implements OnInit {

	public dataUser: any;
	public ofertas: any;

  constructor(
  			private authService: AuthService, 
  			private ofertaService: OfertasService) 
  {
  	this.dataUser = this.authService.getAllDataUser();
  }

  ngOnInit() {
    this.getOfertas();
    // console.log(this.dataUser);
  }

  getOfertas() {
    this.ofertaService.getAllOfertasIntitucion(this.dataUser.id)
        .subscribe(
          response => {
            this.ofertas = response.data;
             // console.log(response);
          },
          error => {
            console.log(<any>error);
          }
        );
  }

  eliminar(idOferta: number) {
    swal({
      title: 'Está seguro que quiere eliminar esta oferta?',
      text: 'Tenga en cuenta que la información no se podrá recuperar!',
      icon: 'warning',
      buttons: ['Cancelar', 'Confirmar'],
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          this.eliminarOferta(idOferta);
        } else {
          swal('Por el momento todo seguira igual.');
        }
      });
  }


  eliminarOferta(idOferta: number) {
    // console.log('-- idOferta a Eliminar: ' +idOferta);

    this.ofertaService.deleteOfertaInstitucion(idOferta, this.dataUser.id)
      .subscribe(response => {
          swal('¡Bien hecho!','Operación exitosa', 'success' );
          this.getOfertas();
          // console.log(response);
        },
        error => {
          swal('¡Error!','Algo salio mal. Intentelo más tarde', 'error' );
          console.log(<any>error);
        }
      );

  }

}
