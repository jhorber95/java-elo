export interface OfertaFreelancer {
	id: string;
	idOfrece: number;
	titulo: string;
	categoria: Categoria;
	municipio: Municipio;
	tipoOferta: TipoOferta;
	descripcion: string;
	destacada: boolean;
	precio: number;
	subcategorias: SubCategoria [];
	estado: Estado;
	telefono: string;
	tipoOfrece: TipoOfrece;
	fechaHora: number;
	calificacion: Calififacion;
	imagenUrl: string;
}

export interface Categoria {
	id: string;
	nombre: string;
	descripcion: string;
}

export interface TipoOferta {
	id: string;
	nombre: string;
	descripcion: string;
}

export interface Calififacion {
	puntaje: number;
	porcentaje: number;
}

export interface SubCategoria {
	id: string;
	nombre: string;
}

export interface Estado {
	id: string;
	nombre: string;
	descripcion: string;
}

export interface Municipio {
  id: string;
  nombre: string;
  departamento: Departamento;
}

export interface Departamento {
  id: string;
  nombre: string;
  pais: Pais;
}

export interface Pais {
  id: string;
  nombre: string;
}

export interface TipoOfrece {
	id: number;
	nombre: string;
	descripcion: string;
}

