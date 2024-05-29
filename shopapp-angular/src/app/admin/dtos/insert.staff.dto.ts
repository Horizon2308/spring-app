export class InsertStaffDTO {
  full_name: string;
  phone_number: string;
  password: string;
  retype_password: string;
  address: string;
  is_active: number;
  date_of_birth: Date;
  avatar: File | undefined;
  sex: number;
  cic: string;
  email: string;
  role_id: number;

  constructor(data: any) {
    this.full_name = data.fullname;
    this.phone_number = data.phone_number;
    this.address = data.address;
    this.password = data.password;
    this.retype_password = data.retype_password;
    this.is_active = data.is_active;
    this.date_of_birth = data.date_of_birth;
    this.avatar = data.avatar;
    this.role_id = data.role_id;
    this.sex = data.sex;
    this.cic = data.cic;
    this.email = data.email;
  }
}
