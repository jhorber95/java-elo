import { Component, OnInit, Input, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../../services/user/user.service';
import { AuthService } from '../../../shared/guard/auth.service';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import swal from 'sweetalert';

@Component({
  selector: 'ap-navigation-admin',
  templateUrl: './navigation.component.html',
  providers: [UserService]
})
export class NavigationComponent implements OnInit, AfterViewInit {
	  name:string;
  	showHide:boolean;
    @Input()
    public dataUser: any = {} ;


      private modal   : any;
  public userPass : any;

  	constructor(
              private router: Router,
              private authService: AuthService,
              private modalService : NgbModal,
                private userService  : UserService) {
    	this.showHide = true;

        this.dataUser = JSON.parse(localStorage.getItem('user'));

    this.userPass = {
       idUsuario: this.dataUser.id,
       oldPassword: '',
       newPassword: ''
     }
  	}

    ngOnInit(){
    }

  	changeShowStatus(){
    	this.showHide = !this.showHide;
  	}

    ngAfterViewInit() {

        $(function () {
            $(".preloader").fadeOut();
        });

        var set = function () {
            var width = (window.innerWidth > 0) ? window.innerWidth : this.screen.width;
            var topOffset = 0;
            if (width < 1170) {
                $("body").addClass("mini-sidebar");
                $('.navbar-brand span').hide();
                $(".sidebartoggler i").addClass("ti-menu");
            } else {
                $("body").removeClass("mini-sidebar");
                $('.navbar-brand span').show();
            }

            var height = ((window.innerHeight > 0) ? window.innerHeight : this.screen.height) - 1;
            height = height - topOffset;
            if (height < 1) height = 1;
            if (height > topOffset) {
                $(".page-wrapper").css("min-height", (height) + "px");
            }

        };
        $(window).ready(set);
        $(window).on("resize", set);

        $(document).on('click', '.mega-dropdown', function (e) {
            e.stopPropagation();
        });

        $(".search-box a, .search-box .app-search .srh-btn").on('click', function () {
            $(".app-search").toggle(200);
        });

        (<any>$('.scroll-sidebar, .right-sidebar, .message-center')).perfectScrollbar();

        $("body").trigger("resize");
    }



  openModal(content) {
    this.modal = this.modalService.open(content, { windowClass: 'animated jello' });
  }

  onFormSubmit(){
    // console.log(this.userPass);
    this.userService.updatePassword(this.userPass)
      .subscribe(
        response => {
          swal('¡Bien hecho!','Operación exitosa','success');
        },
        error => {
          swal('¡Error!','Lo sentimos, halgo salio mal. Intentalo nuevamente.','error');
           console.log(<any>error)
        }
      );

  }

  salir(){
    this.authService.Logout();
    this.router.navigate(['/']);
  }
}
