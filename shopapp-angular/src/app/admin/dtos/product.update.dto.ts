import { Category } from "../models/category";

export class ProductUpdateDTO {
  id: number;
  name: string;
  price: number;
  thumbnail: string;
  quantity: number;
  description: string;
  product_status: number;
  category_id: number;

  constructor(data: any) {
    this.id = data.id;
    this.name = data.name;
    this.price = data.price;
    this.thumbnail = data.thumbnail;
    this.quantity = data.quantity;
    this.description = data.description;
    this.product_status = data.product_status;
    this.category_id = data.category_id;
  }
}
