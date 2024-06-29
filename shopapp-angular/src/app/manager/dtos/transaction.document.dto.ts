import { RawProductDTO } from './raw.product.dto';

export class TransactionDocumentDTO {
  name: string;
  user_id: number;
  raw_products: RawProductDTO[];

  constructor(data: any) {
    this.name = data.name;
    this.user_id = data.user_id;
    this.raw_products = data.raw_products;
  }
}
