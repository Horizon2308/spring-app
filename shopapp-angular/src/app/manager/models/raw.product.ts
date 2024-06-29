import { Provider } from 'src/app/admin/models/provider';

export interface RawProduct {
  id: number;
  name: string;
  quantity: number;
  note: string;
  price: number;
  created_at: Date;
  updated_at: Date;
  provider: Provider;
}
