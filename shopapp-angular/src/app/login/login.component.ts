import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserService } from '../services/user.service';
import { LoginDTO } from '../dtos/login.dto';
import { Router } from '@angular/router';
import { LoginInterface } from '../responses/user/login.interface';
import { TokenService } from '../services/token.service';
import { Role } from '../models/role';
import { RoleService } from '../services/role.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  @ViewChild('loginForm') loginForm!: NgForm;
  phoneNumber: string = '';
  password: string = '';
  selectedRole: Role | undefined;
  roles: Role[] = [];
  
  constructor(private userService: UserService,
    private router: Router,
    private tokenService: TokenService,
    private roleService: RoleService) { }
  
  ngOnInit() {
    debugger
    this.roleService.getRoles().subscribe({
      next: (roles: Role[]) => {
        debugger
        this.roles = roles;
        this.selectedRole = roles.length > 0 ? roles[0] : undefined;  
      },
      error: (error: any) => {
        debugger
        console.log(error.error.message);
      }
    });
  }

  login() {
    debugger;
    const loginDTO: LoginDTO = {
      phone_number: this.phoneNumber,
      password: this.password,
      role_id: this.selectedRole?.id ?? 1,
    };
    this.userService.login(loginDTO).subscribe({
      next: (response: LoginInterface) => {
        debugger;
        const { token } = response;
        this.tokenService.setToken(token);
        console.log('Login successfully!');
      },
      complete: () => {},
      error: (error: any) => {
        debugger;
        alert(`${error.error.status}: Có lỗi xảy ra: ${error.error.message}`);
      },
    });
  }
}
