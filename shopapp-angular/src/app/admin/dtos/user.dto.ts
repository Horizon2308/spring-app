export class UserDTO {
  id: number;
  fullname: string;
  phone_number: string;
  address: string;
  is_active: number;
  date_of_birth: Date;
  avatar: string;
  sex: string;
  cic: string;
  email: string;
  role_id: number;

  constructor(data: any) {
    this.id = data.id;
    this.fullname = data.fullname;
    this.phone_number = data.phone_number;
    this.address = data.address;
    this.is_active = data.is_active;
    this.date_of_birth = data.date_of_birth;
    this.avatar = data.avatar;
    this.role_id = data.role_id;
    this.sex = data.sex;
    this.cic = data.cic;
    this.email = data.email;
  }
}
