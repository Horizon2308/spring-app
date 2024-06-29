export interface TransactionDocumentDetails {
    id: number;
    raw_product_name: string;
    price: number;
    quantity: number;
    provider_name: string;
    created_at: Date;
}