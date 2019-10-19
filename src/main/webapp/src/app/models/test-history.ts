export interface ITestHistory {
  id?: number;
  idFaseItem?: number;
  idUser?: number;
  created_at?: number;
}

export class TestHistory implements ITestHistory {

  constructor(
    public id?: number,
    public idFaseItem?: number,
    public idUser?: number,
    public created_at?: number,
  ) {}
}