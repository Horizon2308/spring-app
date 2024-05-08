import { IsString } from "class-validator";

export class RegisterDTO {
  phone_number: string;
  password: string;
  retype_password: string;
  full_name: string;
  date_of_birth: Date;
  address: string;
  facebook_account_id: number;
  google_account_id: number;
  role_id: number;

  constructor(data: any) {
    this.phone_number = data.phone_number;
    this.password = data.password;
    this.retype_password = data.retype_password;
    this.full_name = data.full_name;
    this.date_of_birth = data.date_of_birth;
    this.address = data.address;
    this.facebook_account_id = data.facebook_account_id || 0;
    this.google_account_id = data.google_account_id || 0;
    this.role_id = data.role_id || 1;
  }
}
