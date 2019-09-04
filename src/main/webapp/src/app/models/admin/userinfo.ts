export interface UserInfo {
  id: number;
  tipoIdentificacion: TipoIdentificacion;
  identificacion: string;
  nombres: string;
  apellidos: string;
  municipio: Municipio;
  telefono : string;
  genero: Genero;
  email: string;
  urlImagen: string;
  estado: Estado;
  password: string;
  roles: string;
}

export interface Estado {
  id: number;
  nombre: string;
}

export interface TipoIdentificacion{
  id: number;
}

export interface Municipio {
  id: number;
  departamento: Departamento;
}

export interface Departamento {
  id: number;
  pais: Pais;

}

export interface Pais {
  id: number;
}

export interface Genero {
  id: number;
}
