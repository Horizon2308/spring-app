import { Component } from '@angular/core';
import { UserResponse } from '../responses/user/user.response';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { TokenService } from '../services/token.service';
import { UpdateUserDTO } from '../dtos/update.user.dto';
import { Province } from '../responses/ghn/province.interface';
import { District } from '../responses/ghn/district.interface';
import { Ward } from '../responses/ghn/ward.interface';
import { GHNService } from '../services/ghn.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent {
  userResponse?: UserResponse;
  userProfileForm: FormGroup;
  token: string | null = '';

  provinces: Province[] = [];
  selectedProvinceId: number = 0;
  districts: District[] = [];
  selectedDistrictId: number = 0;
  wards: Ward[] = [];
  selectedWardCode: string = '';

  isFirstDistrict: boolean;
  isFirstWard: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private router: Router,
    private tokenService: TokenService,
    private ghnService: GHNService
  ) {
    this.isFirstDistrict = true;
    this.isFirstWard = true;
    this.userProfileForm = this.formBuilder.group(
      {
        fullname: [''],
        address: ['', [Validators.minLength(3)]],
        province: [],
        district: [],
        ward: [],
        password: ['', [Validators.minLength(3)]],
        retype_password: ['', [Validators.minLength(3)]],
        date_of_birth: [Date.now()],
      },
      {
        validators: this.passwordMatchValidator, // Custom validator function for password match
      }
    );
  }

  async ngOnInit(): Promise<void> {
    debugger;
    await this.getProvince();
    this.token = this.tokenService.getToken();
    this.userService.getUserDetail(this.token!).subscribe({
      next: (response: any) => {
        debugger;
        this.userResponse = {
          ...response.data,
          date_of_birth: new Date(response.data.date_of_birth),
        };
        debugger;
        let address = this.userResponse!.address.split(',');
        this.provinces.forEach((province) => {
          debugger;
          if (province.provinceName === address[2].trim()) {
            debugger;
            this.selectedProvinceId = province.provinceId;
            this.userProfileForm
              .get('province')
              ?.setValue(this.selectedProvinceId);
            this.getDistrict();
            return;
          }
        });
        this.districts.forEach((district) => {
          debugger;
          if (district.districtName === address[1].trim()) {
            debugger;
            this.selectedDistrictId = district.districtId;
            this.userProfileForm
              .get('district')
              ?.setValue(this.selectedDistrictId);
            this.getWard();
            return;
          }
        });
        this.wards.forEach((ward) => {
          debugger;
          if (ward.wardName === address[0].trim()) {
            this.selectedWardCode = ward.wardCode;
            this.userProfileForm.get('ward')?.setValue(this.selectedWardCode);
          }
        });
        this.fillProfileForm();
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        alert(error.error.message);
      },
    });
  }

  getAddress(): void {
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
        debugger;
        if (!this.isFirstDistrict) {
          this.selectedProvinceId = Number.parseInt(
            this.userProfileForm.get('province')!.value
          );
        }
        this.ghnService.getDistrict(this.selectedProvinceId).subscribe({
          next: (response: any) => {
            this.districts = response.data.map((district: any) => {
              return {
                districtId: district.DistrictID,
                districtName: district.DistrictName,
              };
            });
            if (this.isFirstWard) {
              this.selectedDistrictId = Number.parseInt(
                this.userProfileForm.get('district')!.value
              );
            }
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
                this.isFirstWard = false;
              },
              error: (error: any) => {
                debugger;
                console.error('Error fetching wards:', error);
              },
            });
          },
          complete: () => {
            debugger;
            console.log('Completed!');
            this.isFirstDistrict = false;
          },
          error: (error: any) => {
            debugger;
            console.error('Error fetching districts:', error);
          },
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

  async getProvince(): Promise<void> {
    debugger;
    try {
      const response = await (await this.ghnService.getProvinceAsync()).toPromise();
      debugger;
      this.provinces = response.data.map((province: any) => {
        return {
          provinceId: province.ProvinceID,
          provinceName: province.ProvinceName,
        };
      });
      console.log('Get provinces successfully!');
    } catch (error) {
      debugger;
      console.error('Error fetching provinces:', error);
    }
  }

  getDistrict(): void {
    debugger;
    if (!this.isFirstDistrict) {
      this.selectedProvinceId = Number.parseInt(
        this.userProfileForm.get('province')!.value
      );
    }
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
        this.isFirstDistrict = false;
      },
      error: (error: any) => {
        debugger;
        console.error('Error fetching districts:', error);
      },
    });
  }

  getWard(): void {
    if (this.isFirstWard) {
      this.selectedDistrictId = Number.parseInt(
        this.userProfileForm.get('district')!.value
      );
    }
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
        this.isFirstWard = false;
      },
      error: (error: any) => {
        debugger;
        console.error('Error fetching wards:', error);
      },
    });
  }

  getDetailAddress(address: string[]): void {}

  fillProfileForm(): void {
    this.userProfileForm.patchValue({
      fullname: this.userResponse?.fullname ?? '',
      //address: this.userResponse?.address ?? '',
      province: this.selectedProvinceId,

      date_of_birth: this.userResponse?.date_of_birth
        .toISOString()
        .substring(0, 10),
    });
    this.userService.saveUserResponseToLocalStorage(this.userResponse);
  }

  passwordMatchValidator(): ValidatorFn {
    return (formGroup: AbstractControl): ValidationErrors | null => {
      const password = formGroup.get('password')?.value;
      const retypedPassword = formGroup.get('retype_password')?.value;
      if (password !== retypedPassword) {
        return { passwordMismatch: true };
      }
      return null;
    };
  }
  save(): void {
    debugger;
    if (this.userProfileForm.valid) {
      const updateUserDTO: UpdateUserDTO = {
        fullname: this.userProfileForm.get('fullname')?.value,
        address: this.userProfileForm.get('address')?.value,
        password: this.userProfileForm.get('password')?.value,
        retype_password: this.userProfileForm.get('retype_password')?.value,
        date_of_birth: this.userProfileForm.get('date_of_birth')?.value,
      };

      this.userService.updateUserDetail(this.token!, updateUserDTO).subscribe({
        next: (response: any) => {
          this.userService.removeUserFromLocalStorage();
          this.tokenService.removeToken();
          this.router.navigate(['/login']);
        },
        error: (error: any) => {
          alert(error.error.message);
        },
      });
    } else {
      if (this.userProfileForm.hasError('passwordMismatch')) {
        alert('Mật khẩu và mật khẩu gõ lại chưa chính xác');
      }
    }
  }
}
