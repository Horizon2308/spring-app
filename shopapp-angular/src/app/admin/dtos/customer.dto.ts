export class CustomerDTO {
  fullname: string;
  phone_number: string;
  email: string;
  address: string;

  constructor(data: any) {
    this.fullname = data.fullnam;
    this.address = data.address;
    this.phone_number = data.phone_number;
    this.email = data.email;
  }
}
