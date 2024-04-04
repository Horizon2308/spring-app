import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { RegisterDTO } from '../dtos/register.dto';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm!: NgForm; // quản lý form bên html
  phone: string;
  password: string;
  retypePassword: string;
  fullName: string;
  dateOfBirth: Date;
  address: string;
  isAccepted: boolean;

  constructor(private router: Router, private userService: UserService) {
    this.phone = '0356202542';
    this.password = 'Hungpro2308';
    this.retypePassword = 'Hungpro2308';
    this.address = 'Ninh Binh';
    this.fullName = 'Ha Duc Hung';
    this.isAccepted = false;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18);
  }

  onChangePhoneNumber() {
    console.log(`Phone typed: ${this.phone}`);
  }

  register() {
    // alert(`Phone: ${this.phone}, Password: ${this.password},
    // Retype Password: ${this.retypePassword}, Address: ${this.address},
    // Fullname: ${this.fullName}, Is accepted: ${this.isAccepted},
    // Date of birth: ${this.dateOfBirth}`);
    debugger;
    const registerDTO: RegisterDTO = {
      full_name: this.fullName,
      phone_number: this.phone,
      address: this.address,
      password: this.password,
      retype_password: this.retypePassword,
      date_of_birth: this.dateOfBirth,
      facebook_account_id: 0,
      google_account_id: 0,
      role_id: 1,
    };
    this.userService.register(registerDTO).subscribe({
      next: (response: any) => {
        debugger;
        console.log('Register successfully');
      },
      complete: () => {},
      error: (error: any) => {
        debugger;
        alert(`${error.error.status}: Có lỗi xảy ra: ${error.error.message}`);
      },
    });
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
