export interface Evento {
	id: string;
	titulo: string;
	descripcion: string;
	institucion: Institucion;
	tipoEvento: TipoEvento;
	estado: Estado;
	fecha: string;

}

export interface Institucion {
	id: string;
	nombre: string;
	nit: string;
	latitud: string;
	longitud: string;
	direccion: string;
	telefono: string;
	url: string;
	descripcion: string;
	email: string;
	tipoInstitucion: TipoInstitucion;
	fecha: string;
	estado: Estado;
	imagenUrl: string;

}

export interface TipoInstitucion {
	id: string;
	nombre: string;
	descripcion: string;
}

export interface Estado {
	id: string;
	nombre: string;
	descripcion: string;
}

export interface TipoEvento {
	id: number;
	nombre: string;
	descripcion: string;
}
