export class RawProductDTO {
  name: string;
  price: number;
  quantity: number;
  note: string;
  provider_id: number;

  constructor(data: any) {
    this.name = data.name;
    this.price = data.price;
    this.quantity = data.quantity;
    this.note = data.note;
    this.provider_id = data.provider_id;
  }
}
