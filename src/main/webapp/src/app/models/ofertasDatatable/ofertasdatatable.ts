export interface Ofertasdatatable {
  id: number;
  titulo: string;
  descripcion: string;
  precio: string;
  telefono: string;
  tipoOfrece: TipoOfrece;
  categoria: Categoria;
  municipio: Municipio;
  tipoOferta: TipoOferta;
  fechaHora: number;
  estado: Estado;
  idOfrece: number;
  calificacion: Calificacion;
  imagenUrl: string;
  destacada: boolean;
  subcategorias: Subcategorias[];
  inteligencias: any;
}

export interface TipoOfrece {
  id: number;
  nombre: string;
  descripcion: string;
}

export interface Categoria {
  id: number;
  nombre: string;
  descripcion: string;
}

export interface Municipio {
  id: number;
  nombre: string;
  departamento: Departamento;
}

export interface Departamento {
  id: number;
  nombre: string;
  pais: Pais;
}

export interface Pais {
  id: number;
  nombre: string;
}

export interface TipoOferta {
  id: number;
  nombre: string;
  descripcion: string;
}

export interface Estado {
  id: number;
  nombre: string;
  descripcion: string;
}

export interface Calificacion {
  puntaje: number;
  porcentaje: number;
}

export interface Subcategorias {
  id: number;
  nombre: string;
}
