import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../shared/guard/auth.service';
import { DenunciaService } from '../../../services/denuncia.service';
import { NgbModal }    from '@ng-bootstrap/ng-bootstrap';
import swal            from 'sweetalert';

@Component({
  selector: 'ap-sidebar',
  templateUrl: './sidebar.component.html',
  providers: [DenunciaService]
})
export class SidebarComponent {
    // this is for the open close
    isActive: boolean = true;
    showMenu: string = '';
    showSubMenu: string = '';
    @Input() public dataUser;
    @Input() public nameRoles;
    public modal: any;

    public pqr: any;

    constructor(private router: Router, 
                private authService: AuthService,
                private modalService: NgbModal,
                private denunciaService: DenunciaService ){
        this.dataUser = JSON.parse(localStorage.getItem('user'));

        this.pqr = {
            mensaje: '',
            usuario: {
                id: Number(this.dataUser.id)
            }
        };
    }
    
    addExpandClass(element: any) {
        if (element === this.showMenu) {
            this.showMenu = '0';
        } else {
            this.showMenu = element; 
        }
    }
    addActiveClass(element: any) {
        if (element === this.showSubMenu) {
            this.showSubMenu = '0';
        } else {
            this.showSubMenu = element;
        }
    }
    eventCalled() {
        this.isActive = !this.isActive;
    }

    openModal(content) {
        this.modal = this.modalService.open(content, { windowClass: 'animated jello' });
    }

    onFormSubmit(){
        console.log(this.pqr);
        this.denunciaService.sendDenuncia(this.pqr)
            .subscribe(
                response => {
                    swal('¡Bien hecho!','Operación exitosa','success');
                },
                error => {
                    swal('¡Error!','Lo sentimos, halgo salio mal. Intentalo nuevamente.','error');
                    console.log(<any>error);
                }
            );
    }
    salir(){
    this.authService.Logout();
    this.router.navigate(['/']);
  }

}

