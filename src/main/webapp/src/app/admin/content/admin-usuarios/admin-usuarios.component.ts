import { Component, NgZone, OnInit, ViewEncapsulation } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { AdminService } from '../../services/admin.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Usersdatatable } from '../../../models/usersDatatable/usersdatatable';
import 'rxjs/add/operator/toPromise';

//sweat alert
import swal from 'sweetalert';

import {UserInfo} from "../../../models/admin/userinfo";
import {DepartamentoService} from "../../../services/departamento.service";
import {MunicipioService} from "../../../services/municipio.service";

class DataTablesResponse {
  data: any[];
  draw: number;
  recordsFiltered: number;
  recordsTotal: number;
}

@Component({
  selector: 'app-admin-usuarios',
  templateUrl: './admin-usuarios.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./admin-usuarios.component.css'],
})
export class AdminUsuariosComponent implements OnInit {
  //dtOptions: Promise<DataTables.Settings>;
  usuariosDatatable: any[];
  //modal
  private modal: any;
  public userInfo: UserInfo;
  public departamentos: any;
  public municipios: any;
  message = '';
  clickSelected: string = '';
  public info: any;
  private row;

  // private usuariosDatatable: any[] = [];
  dtOptions: DataTables.Settings = {};

  private api_url;

  // api_url = 'https://angular-datatables-demo-server.herokuapp.com/';

  constructor(private adminUsuarioService: AdminService,
              private httpClient: HttpClient,
              private zone: NgZone,
              private modalService: NgbModal,
              private _departamentoService: DepartamentoService,
              private _municipioService: MunicipioService) {
    this.api_url = '/api/usuario/dataTables';
    this.usuariosDatatable = [];
  }


  someClickHandler(info: any): void {
    this.message = info.id + ' | ' + info.email + ' | ' + info.nombres;
    this.info = info;
  }

  openModal(content) {
    this.modal = this.modalService.open(content, { windowClass: 'animated jello' });
  }


  ngOnInit() {
    this.getDataRow();
    this.getDepartamentos();
    this.getMunicipios();
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

        this.httpClient.post<DataTablesResponse>(this.api_url, dataTablesParameters, {headers}).subscribe(
          response => {
            this.usuariosDatatable = [];
            for(let i = 0; i < response.data.length; i++){
              if (response.data[i].identificacion === null || response.data[i].tipoIdentificacion === null || response.data[i].municipio === null || response.data[i].genero === null) {
                this.userInfo = {
                  id: response.data[i].id,
                  tipoIdentificacion: {
                    id: 1
                  },
                  identificacion: response.data[i].identificacion,
                  nombres: response.data[i].nombres,
                  apellidos: response.data[i].apellidos,
                  municipio: {
                    id: 0,
                    departamento: {
                      id: 0,
                      pais: {
                        id: 1
                      }
                    }
                  },
                  telefono: response.data[i].telefono,
                  genero: {id: 0},
                  email: response.data[i].email,
                  urlImagen: response.data[i].urlImagen,
                  estado: {
                    id: response.data[i].estado.id,
                    nombre: response.data[i].estado.nombre
                  },
                  password: response.data[i].password,
                  roles: response.data[i].roles
                };
                this.usuariosDatatable.push(this.userInfo);
              } else{
                this.usuariosDatatable.push(response.data[i]);
              }
            }

            callback({
              recordsTotal: response.recordsTotal,
              recordsFiltered: response.recordsFiltered,
              data: []
            });
          });
      },
      columns: [
        { data: 'id' }, { data: 'nombres' },
        { data: 'apellidos'}, { data: 'email' },
        { data: 'identificacion'}, { data: 'estado.nombre' }
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
    this.someClickHandler(this.usuariosDatatable[rowId]);
  }

  editarUsuario() {
    swal({
      title: "Está seguro de editar el usuario "+this.info.email+"?",
      text: "Para editar este usuario, click en Confirmar!",
      icon: "warning",
      buttons: ["Cancelar", "Confirmar"],
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          this.Editar();
        } else {
          swal("usted No edito el usuario, refresque la pagina...")
            .then((value) => {
              location.reload();
            });
          this.modal.close();

        }
      });

  }

  eliminarUsuario() {
    swal({
      title: "Está seguro de ELIMINAR el usuario "+this.info.email+"?",
      text: "Para ELIMINAR este usuario, click en Confirmar!",
      icon: "warning",
      buttons: ["Cancelar", "Confirmar"],
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          this.Eliminar();
        } else {
          swal("usted no eliminó el usuario");
          this.modal.close();
        }
      });

  }

  Editar(){
    this.adminUsuarioService.editUser(this.info.id, this.info).subscribe(
      response => {
        if(response.exito === true){
          swal("Buen trabajo!", "Ha editado el usuario con éxito", "success")
            .then((value) => {
            });
        } else{
          swal("Algo a salido mal", "Error al editar el usuario", "error");
        }
        this.modal.close();
      },
      error => {
        swal("Algo a salido mal", "Error al editar el usuario, llene todos los campos o vuelva a intentarlo", "error");
        console.log(<any>error);
      }
    );
  }

  Eliminar(){
    this.adminUsuarioService.eliminarUsuario(this.info.id).subscribe(
      response => {
        if(response.exito === true){
          swal("Buen trabajo!", "El usuario se ha elimimado con éxito del sistema", "success")
            .then((value) => {
              this.usuariosDatatable.splice(this.row,1)
            });
        } else{
          swal("Algo a salido mal", "Error al intentar eliminar el usuario", "error");
        }
        this.modal.close();
      },
      error => {
        swal("Algo a salido mal", "Error al elminar el usuario", "error");
        console.log(<any>error);
      }
    );
  }

  getDepartamentos(){
    this._departamentoService.getDepartamentos()
      .subscribe(
        response =>{
          this.departamentos = response;
        },
        error => {
          console.log(<any>error);
        }
      ) ;
  }

  getMunicipios(){
    this._municipioService.getMunicipio()
      .subscribe(
        response =>{
          this.municipios = response;
        },
        error => {
          console.log(<any>error);
        }
      ) ;
  }

  selectEstado(valor:any) {
    switch (valor) {
      case "1": {
        this.info.estado.nombre = 'ACTIVO';
        break;
      }
      case "2": {
        this.info.estado.nombre = 'INACTIVO';
        break;
      }
    }
  }

}

