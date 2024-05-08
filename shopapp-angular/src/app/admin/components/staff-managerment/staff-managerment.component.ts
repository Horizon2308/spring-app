import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/user/services/user.service';

@Component({
  selector: 'app-staff-managerment',
  templateUrl: './staff-managerment.component.html',
  styleUrls: ['./staff-managerment.component.scss'],
})
export class StaffManagermentComponent implements OnInit {
  userName: string = '';
  keyword: string = '';

  constructor(private userService: UserService) {}
  ngOnInit(): void {
    this.userName = this.userService.getUserResponseFromLocalStorage()!.fullname;
  }

  searchStaff() {
    
  }
}
