import { Component, AfterViewInit } from '@angular/core';
@Component({
	templateUrl: './dashboardinstitucion.component.html'
})
export class DashboardinstitucionComponent implements AfterViewInit {
	subtitle:string;
	constructor() {
		this.subtitle = "\"Bienvenido a su panel de Estúdialo, nos alegramos de tenerte de vuelta"
	}

	ngAfterViewInit(){ }
}
