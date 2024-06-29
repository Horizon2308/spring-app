export interface TransactionDocument {
  id: number;
  name: string;
  username: string;
  total_products: number;
  type: string;
  created_at: Date;
}
