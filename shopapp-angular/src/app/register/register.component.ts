import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Route } from '@angular/router';

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

  constructor() {
    this.phone = '';
    this.password = '';
    this.retypePassword = '';
    this.address = '';
    this.fullName = '';
    this.isAccepted = false;
    this.dateOfBirth = new Date();
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear() - 18);
  }

  onChangePhoneNumber() {
    console.log(`Phone typed: ${this.phone}`);
  }

  register() {
    alert(`Phone: ${this.phone}, Password: ${this.password},
    Retype Password: ${this.retypePassword}, Address: ${this.address}, 
    Fullname: ${this.fullName}, Is accepted: ${this.isAccepted}, 
    Date of birth: ${this.dateOfBirth}`);
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
