import { Role } from 'src/app/user/models/role';

export interface UserResponse {
  id: number;
  fullname: string;
  address: string;
  avatar: string;
  is_active: boolean;
  date_of_birth: Date;
  facebook_account_id: number;
  google_account_id: number;
  role: Role;
}
