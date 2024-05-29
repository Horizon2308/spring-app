import { Role } from 'src/app/user/models/role';

export interface Staff {
  id: number;
  fullname: string;
  phone_number: number;
  address: string;
  is_active: number;
  date_of_birth: string;
  avatar: string;
  role: Role;
}
