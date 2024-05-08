import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm, FormsModule } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { RegisterDTO } from '../../dtos/register.dto';
import { Province } from '../../../responses/ghn/province.interface';
import { District } from '../../../responses/ghn/district.interface';
import { Ward } from '../../../responses/ghn/ward.interface';
import { GHNService } from '../../services/ghn.service';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmComponent } from 'src/app/ultils/dialog-confirm/dialog-confirm.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  @ViewChild('registerForm') registerForm!: NgForm; // quản lý form bên html
  phone: string;
  password: string;
  retypePassword: string;
  fullName: string;
  dateOfBirth: Date;
  address: string;
  isAccepted: boolean;

  provinces: Province[] = [];
  selectedProvinceId: number = 0;
  districts: District[] = [];
  selectedDistrictId: number = 0;
  wards: Ward[] = [];
  selectedWardCode: string = '0';
  showPassword: boolean = false;

  constructor(
    private router: Router,
    private userService: UserService,
    private ghnService: GHNService,
    private dialog: MatDialog
  ) {
    this.phone = '0915144606';
    this.password = 'Hungpro2308';
    this.retypePassword = 'Hungpro2308';
    this.address = 'Ninh Binh';
    this.fullName = 'Ha Duc Hung';
    this.isAccepted = false;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18);
  }
  ngOnInit(): void {
    debugger;
    this.ghnService.getProvince().subscribe({
      next: (response: any) => {
        debugger;
        this.provinces = response.data.map((province: any) => {
          return {
            provinceId: province.ProvinceID,
            provinceName: province.ProvinceName,
          };
        });
        console.log('Get provinces successfully!');
      },
      complete: () => {
        debugger;
        console.log('Completed!');
      },
      error: (error: any) => {
        debugger;
        console.error('Error fetching provinces:', error);
      },
    });
  }

  provinceChange(): void {
    debugger;
    console.log(this.selectedProvinceId);
    this.ghnService.getDistrict(this.selectedProvinceId).subscribe({
      next: (response: any) => {
        this.districts = response.data.map((district: any) => {
          return {
            districtId: district.DistrictID,
            districtName: district.DistrictName,
          };
        });
      },
      complete: () => {
        debugger;
        console.log('Completed!');
      },
      error: (error: any) => {
        debugger;
        console.error('Error fetching districts:', error);
      },
    });
  }

  districtChange(): void {
    this.ghnService.getWard(this.selectedDistrictId).subscribe({
      next: (response: any) => {
        this.wards = response.data.map((ward: any) => {
          return {
            wardCode: ward.WardCode,
            wardName: ward.WardName,
          };
        });
      },
      complete: () => {
        debugger;
        console.log('Completed!');
      },
      error: (error: any) => {
        debugger;
        console.error('Error fetching wards:', error);
      },
    });
  }

  onChangePhoneNumber() {
    console.log(`Phone typed: ${this.phone}`);
  }

  register() {
    debugger;
    // alert(`Phone: ${this.phone}, Password: ${this.password},
    // Retype Password: ${this.retypePassword}, Address: ${this.address},
    // Fullname: ${this.fullName}, Is accepted: ${this.isAccepted},
    // Date of birth: ${this.dateOfBirth}`);
    let provinceName, districtName, wardName;
    this.provinces.forEach((province) => {
      if (province.provinceId == this.selectedProvinceId) {
        provinceName = province.provinceName;
      }
    });
    this.districts.forEach((district) => {
      if (district.districtId == this.selectedDistrictId) {
        districtName = district.districtName;
      }
    });
    this.wards.forEach((ward) => {
      if (ward.wardCode == this.selectedWardCode) {
        wardName = ward.wardName;
      }
    });
    const address = `${wardName}, ${districtName}, ${provinceName}`;
    debugger;
    const registerDTO: RegisterDTO = {
      full_name: this.fullName,
      phone_number: this.phone,
      address: address,
      password: this.password,
      retype_password: this.retypePassword,
      date_of_birth: this.dateOfBirth,
      facebook_account_id: 0,
      google_account_id: 0,
      role_id: 2,
    };
    this.userService.register(registerDTO).subscribe({
      next: (response: any) => {
        debugger;
        let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
          width: '250px',
          data: {
            title: 'Đăng kí',
            message: 'Đăng kí thành công',
          },
        });
        dialogConfirm.afterClosed().subscribe((result) => {
          if (result) {
            this.router.navigate(['/login']);
          }
        });
      },
      complete: () => {},
      error: (error: any) => {
        debugger;
        let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
          width: '250px',
          data: {
            title: 'Đăng kí',
            message: error.error.message,
          },
        });
        dialogConfirm.afterClosed().subscribe((result) => {
          if (result) {
            dialogConfirm.close();
          }
        });
      },
    });
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  checkPasswordMatched() {
    if (this.password !== this.retypePassword) {
      this.registerForm.form.controls['retypePassword'].setErrors({
        passwordMissMatch: true,
      });
    } else {
      this.registerForm.form.controls['retypePassword'].setErrors(null);
    }
  }

  checkAgeIsValid() {
    if (this.dateOfBirth) {
      const today = new Date();
      const birthDate = new Date(this.dateOfBirth);
      let age = today.getFullYear() - birthDate.getFullYear();
      const monthDiff = today.getMonth() - birthDate.getMonth();
      if (
        monthDiff < 0 ||
        (monthDiff == 0 && birthDate.getDay() < today.getDay())
      ) {
        age--;
      }
      if (age < 10) {
        this.registerForm.form.controls['dateOfBirth'].setErrors({
          isAgeInvalid: true,
        });
      } else {
        this.registerForm.form.controls['dateOfBirth'].setErrors(null);
      }
    }
  }
}
