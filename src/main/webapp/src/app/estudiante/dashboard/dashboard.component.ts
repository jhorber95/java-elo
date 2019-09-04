import { Component, AfterViewInit } from '@angular/core';
@Component({
	templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements AfterViewInit {
	subtitle: string;
	constructor() {
		this.subtitle = 'Bienvenido a su panel de Estúdialo, nos alegramos de tenerte de vuelta';
	}

	ngAfterViewInit() { }
}
