import { Component, AfterViewInit } from '@angular/core';
@Component({
	templateUrl: './dashboardfreelancer.component.html'
})
export class DashboardfreelancerComponent implements AfterViewInit {
	subtitle:string;
	constructor() {
		this.subtitle = "Bienvenido a su panel de Estúdialo, nos alegramos de tenerte de vuelta "
	}

	ngAfterViewInit(){ }
}
