export interface Usersdatatable {
  id: number;
  tipoIdentificacion: TipoIdentificacion;
  identificacion: number;
  nombres: string;
  apellidos: string;
  municipio: Municipio;
  telefono : number;
  genero: Genero;
  email: string;
  password: string;
  estado: Estado;
  roles: string;
  urlImagen: string;
}

export interface TipoIdentificacion{
  id: number;
  nombre: string;
  acronimo: string;
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

export interface Genero {
  id: number;
  nombre: string;
  acronimo: string;
}

export interface Estado {
  id: number;
  nombre: string;
  descripcion: string;
}
