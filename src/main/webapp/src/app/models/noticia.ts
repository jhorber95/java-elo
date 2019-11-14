export interface INoticia {
  id?: number;
  titulo?: string;
  descripcion?: string;
  imagenPrincipal?: string;
  contenido?: string;
  createdAt?: number;
  autor?: number;
}

export class Noticia implements INoticia {
  constructor(
    public id?: number,
    public titulo?: string,
    public descripcion?: string,
    public imagenPrincipal?: string,
    public contenido?: string,
    public createdAt?: number,
    public autor?: number) {
  }
}