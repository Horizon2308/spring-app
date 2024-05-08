import { IsNotEmpty, IsString } from 'class-validator';
import { Role } from '../models/role';

export class LoginDTO {
  @IsString()
  @IsNotEmpty()
  phone_number: string;

  @IsNotEmpty()
  @IsString()
  password: string;

  role_id: number = 0;

  constructor(data: any) {
    this.phone_number = data.phone_number;
    this.password = data.password;
    this.role_id = data.role;
  }
}
