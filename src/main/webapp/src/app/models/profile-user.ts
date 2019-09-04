export interface ProfileUsers {
  id: string;
  tipoIdentificacion: TipoIdentificacion;
  identificacion: string;
  nombres: string;
  apellidos: string;
  municipio: Municipio;
  telefono : string;
  genero: Genero;
  email: string;
  urlImagen: string;
}

export interface TipoIdentificacion{
  id: string;
}

export interface Municipio {
  id: string;
  departamento: Departamento;
 
}

export interface Departamento {
  id: string;
  pais: Pais;
  
}

export interface Pais {
  id: string;
}

export interface Genero {
  id: string;
 }

