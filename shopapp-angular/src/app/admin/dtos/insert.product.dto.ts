import { IsString, IsNotEmpty, IsPhoneNumber } from 'class-validator';

export class InsertProductDTO {
  @IsPhoneNumber()
  name: string;

  quantity: number;

  product_status: number;

  provider_id: number;

  price: number;

  @IsString()
  @IsNotEmpty()
  description: string;

  category_id: number;
  images: File[] = [];

  constructor(data: any) {
    this.name = data.name;
    this.price = data.price;
    this.description = data.description;
    this.category_id = data.category_id;
    this.quantity = data.quantity;
    this.product_status = data.product_status;
    this.provider_id = data.provider_id;
  }
}
