export class ExportRawProductDTO {
  id: number;
  name: string;
  price: number;
  quantity: number;
  store_id: number;

    constructor(data: any) {
        this.id = data.id;
    this.name = data.name;
    this.price = data.price;
    this.quantity = data.quantity;
    this.store_id = data.store_id;
  }
}