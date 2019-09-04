export class FormDataTest {
  id: number = 0;
  nombre: string = '';
  fases: Fases[] = [];
}

export class Fases {
  id: number = 0;
  nombre: string = '';
  numero: number = 0;
  items: Items[] = [];
}

export class Items {
  id: number = 0;
  imagen: string = '';
  inteligencia: Inteligencia;
  seleccionado: boolean;
}

export class Inteligencia {
  id: number = 0;
  nombre: string = '';
  descripcion: string = '';
}
