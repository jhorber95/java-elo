export class Persona {
  constructor(
    public id: string = '',
    public nombres: string = '',
    public tipoIdentificacion: TipoIdentificacion,
	public identificacion: string = '',
	public apellidos: string = '',
	public municipio:Municipio ,
	public telefono: string = '',
	public genero: Genero ,
	public email:string = '' ,
	public urlImagen: string = '',
	public nivelEducativo: NivelEducativo,
	public roles: Roles[],
	public intereses: Intereses[]
  ) {}
}

export class TipoIdentificacion{
	constructor(public id: number){}
}

export class Municipio  {
	constructor(
		public id: string = '',
		public departamento: Departamento,
	)
	{}
}

export class NivelEducativo  {
	constructor(
		public id: number
	)
	{}
}

export class Departamento  {
	constructor(
		public id: string = '',
		public pais: Pais

	) {}
}
export class Pais {
	constructor(
		public id: string = ''
		) {}
}

export class Genero {
	constructor(
		public id: string = ''
		) {}
}
export class Roles {
	constructor(
		public id: string = '',
		public nombre: string = '' ) {}
}

export class Intereses {
	constructor(
		public id: number,
		public nombre: string)
	{}
}