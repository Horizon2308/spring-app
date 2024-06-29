import { ExportRawProductDTO } from "./export.raw.product.dto";

export class ExportTransactionDocumentDTO {
  name: string;
  user_id: number;
  raw_products: ExportRawProductDTO[];

  constructor(data: any) {
    this.name = data.name;
    this.user_id = data.user_id;
    this.raw_products = data.raw_products;
  }
}
