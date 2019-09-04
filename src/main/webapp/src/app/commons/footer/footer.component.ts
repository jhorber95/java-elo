import { Component, OnInit } from '@angular/core';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {ContactoService} from "../../services/contacto.service";

//sweat alert
import swal from 'sweetalert';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css'],
  providers: [ContactoService]
})
export class FooterComponent implements OnInit {

  private modal: any;
  closeResult: string;

  model: any = {};

  constructor(private modalService: NgbModal,
              private contactoService: ContactoService) {
    this.model = {
      nombres: "",
      email: "",
      celular: "",
      asunto:"",
      mensaje:""
    }
  }

  ngOnInit() {
  }

  open(content) {
    this.modal = this.modalService.open(content, { windowClass: 'animated pulse' });
    this.modal.result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return  `with: ${reason}`;
    }
  }

  contactenos() {
    this.contactoService.contacto(this.model.nombres, this.model.email, this.model.celular, this.model.asunto, this.model.mensaje).subscribe(
      response => {
        this.modal.close();
        if(response.exito === true) {
          swal("Buen trabajo!", "El email a sido enviado con éxito. Pronto nos comunicaremos.", "success");
        }else{
          swal("Ups!", "Algo a salido mal, intentalo de nuevo más tarde", "error");
        }
      },
      error => {
        console.log(<any>error);
        swal("Ups!", "Algo a salido mal, intentalo de nuevo más tarde", "error");
      }
    );
  }

}
