import { Component, NgZone, OnInit, ViewEncapsulation } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { OfertasService } from '../../../services/ofertas/ofertas.service';
import { AdminService } from '../../services/admin.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import 'rxjs/add/operator/toPromise';

//sweat alert
import swal from 'sweetalert';

class DataTablesResponse {
  data: any[];
  draw: number;
  recordsFiltered: number;
  recordsTotal: number;
}

@Component({
  selector: 'app-admin-financiaciones',
  templateUrl: './admin-financiaciones.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./admin-financiaciones.component.css'],
  providers:[OfertasService]
})
export class AdminFinanciacionesComponent implements OnInit {
  //dtOptions: Promise<DataTables.Settings>;
  FinanciacionesDatatable: any[];
  //modal
  private modal: any;
  public financiacionInfo: any;
  public agregarFinanciacion: any;
  public Financieras: any;
  message = '';
  private row: number;

  dtOptions: DataTables.Settings = {};

  private api_url_financiaciones;
  clickSelected: string = '';


  constructor(private adminUsuarioService: AdminService,
              private httpClient: HttpClient,
              private zone: NgZone,
              private modalService: NgbModal,
              private ofertasService: OfertasService) {
    this.api_url_financiaciones = '/api/financiacion/dataTables';

    this.agregarFinanciacion = {
      informacion: '',
      institucion: {
        id:0
      },
      estado: {
        id:8
      }
    };

  }


  someClickHandler(info: any): void {
      this.message = info.id + ' | ' + info.institucion.nombre;
     this.financiacionInfo = info;
  }

  openModal(content) {
    this.modal = this.modalService.open(content, { windowClass: 'animated jello' });
  }


  ngOnInit() {
    this.getDataRow();
    this.getFinancieras();
  }



  getDataRow() {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
      serverSide: true,
      processing: true,
      language: {
        url: "/assets/data/spanish.json"
      },
      ajax: (dataTablesParameters: any, callback) => {
        let headers = new HttpHeaders();
        headers = headers.set('Authorization', 'Bearer ' + localStorage.getItem('token'));

        this.httpClient.post<DataTablesResponse>(this.api_url_financiaciones, dataTablesParameters, {headers}).subscribe(
          resp => {
            this.FinanciacionesDatatable = resp.data;
            console.log(this.FinanciacionesDatatable);
            callback({
              recordsTotal: resp.recordsTotal,
              recordsFiltered: resp.recordsFiltered,
              data: []
            });
          });
      },
      columns: [
        { data: 'id', width: '15%' }, { data: 'nombre', width: '20%' },
        { data: 'nit', width: '15%'}, {data: 'informacion', width: '30%' },
        { data: 'estado', width: '20%'}
      ],
      rowCallback: (row: Node, data: any[] | Object, index: number) => {
        const self = this;
        $('td', row).unbind('click');
        $('td', row).bind('click', () => {
          self.someClickHandler(data);
        });
        return row;
      }
    };
  }

  ClickRow(rowId, element: any) {
    this.row = rowId;
    if (element === this.clickSelected) {
      this.clickSelected = '0';
    } else {
      this.clickSelected = element;
    }
    this.someClickHandler(this.FinanciacionesDatatable[rowId]);
  }

  eliminarFinanciacion() {
    swal({
      title: "Está segudo de ELIMINAR la Financiación "+this.financiacionInfo.id+"?",
      text: "Para ELIMINAR esta financiación, click en Confirmar!",
      icon: "warning",
      buttons: ["Cancelar", "Confirmar"],
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          this.Eliminar();
        } else {
          swal("usted no eliminó la financiación");
          this.modal.close();
        }
      });

  }

  Eliminar(){
    this.financiacionInfo.estado.id = 7;
    this.adminUsuarioService.eliminarFinanciacion(this.financiacionInfo.id).subscribe(
      response => {
        if(response.exito === true){
          swal("Buen trabajo!", "La oferta se ha elimimado con éxito del sistema", "success")
            .then((value) => {
              this.FinanciacionesDatatable.splice(this.row,1)
            });
        } else{
          swal("Algo a salido mal", "Error al intentar eliminar la oferta", "error");
        }
        this.modal.close();
      },
      error => {
        swal("Algo a salido mal", "Error al elminar la oferta", "error");
        console.log(<any>error);
      }
    );
  }

  editarFinan() {
    swal({
      title: "Está segudo de editar la financiación "+this.financiacionInfo.institucion.nombre+"?",
      text: "Para editar esta finananciación, click en Confirmar!",
      icon: "warning",
      buttons: ["Cancelar", "Confirmar"],
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          this.Editar();
        } else {
          swal('usted No edito la financiación, refresque la pagina...')
            .then((value) => {
              location.reload();
            });
          this.modal.close();

        }
      });

  }

  Editar(){
    this.adminUsuarioService.editarFinanciacion(this.financiacionInfo.id, this.financiacionInfo).subscribe(
      response => {
        if(response.exito === true){
          swal('Buen trabajo!', 'Ha editado el usuario con éxito', 'success')
            .then((value) => {
            });
        } else{
          swal('Algo a salido mal', 'Error al editar el usuario', 'error');
        }
        this.modal.close();
      },
      error => {
        swal('Algo a salido mal', 'Error al editar el usuario, llene todos los campos o vuelva a intentarlo', 'error');
        console.log(<any>error);
      }
    );
  }

  disponible(id: number) {
    this.adminUsuarioService.editarFinanciacion(id, this.financiacionInfo).subscribe(
      response => {
        if(response.exito === true){
          swal('Buen trabajo!', 'Se cambio el estado de la financiación', 'success');
          console.log(response);
        }else{
          swal('Algo a salido mal', 'Error al cambiar estado de la financiación', 'error')
            .then((value) => {
              location.reload();
            });
        }
      },
      error => {
        swal('Algo a salido mal', 'Error al cambiar estado de la financiación', 'error')
          .then((value) => {
            location.reload();
          });
        console.log(<any>error);
      }
    );
  }

  getFinancieras() {
    this.ofertasService.getFinancieras().subscribe(
      response => {
        this.Financieras = response.data;
      },
      error => {
        console.log(<any>error);
      }
    );
  }

  agregarFinan() {
    swal({
      title: 'Está segudo de Agregar esta financiación?',
      text: 'Para agragar esta financiación, click en Confirmar!',
      icon: 'warning',
      buttons: ['Cancelar', 'Confirmar'],
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          this.Agregar();
        } else {
          swal('usted No agrego la financiación, refresque la pagina...')
            .then((value) => {
              location.reload();
            });
          this.modal.close();

        }
      });

  }

  Agregar(){
    this.adminUsuarioService.agregarFinanciacion(this.agregarFinanciacion).subscribe(
      response => {
        if(response.exito === true){
          swal('Buen trabajo!', 'Ha Agregado la financiación con éxito', 'success')
            .then((value) => {
              location.reload();
            });
        } else{
          swal('Algo a salido mal', 'Error al agregar financiación', 'error');
        }
        this.modal.close();
      },
      error => {
        swal('Error', 'Error al agregar financiación, llene todos los campos o vuelva a intentarlo', 'error');
        console.log(<any>error);
      }
    );
  }

}

