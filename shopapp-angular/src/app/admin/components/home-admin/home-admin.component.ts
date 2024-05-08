import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/user/services/user.service';

@Component({
  selector: 'app-home-admin',
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.scss'],
})
export class HomeAdminComponent implements OnInit {
  user: string = '';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.user = this.userService.getUserResponseFromLocalStorage()!.fullname;
  }
}
