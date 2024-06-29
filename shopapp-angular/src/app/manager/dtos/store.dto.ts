export class StoreDTO {
  name: string;
  phone_number: string;
  address: string;

  constructor(data: any) {
    this.name = data.name;
    this.phone_number = data.phone_number;
    this.address = data.phone_number;
  }
}
