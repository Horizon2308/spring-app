import { Category } from 'src/app/user/models/category';
import { ProductImage } from './product.image';

export interface Product {
  id: number;
  name: string;
  description: string;
  url: string;
  created_at: Date;
  updated_at: Date;
  thumbnail: string;
  price: number;
  quantity: number;
  is_active: number;
  product_status: number;
  category: Category;
  product_images: ProductImage[];
}
