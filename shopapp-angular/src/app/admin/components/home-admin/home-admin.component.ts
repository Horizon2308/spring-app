import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/user/services/user.service';
import { StatisticService } from '../../services/statistic.service';
import { Order } from 'src/app/user/models/order';
import { OrderStatistic } from '../../models/order.statistic';
import { UserStatistic } from '../../models/user.statistic';

@Component({
  selector: 'app-home-admin',
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.scss'],
})
export class HomeAdminComponent implements OnInit {
  user: string = '';
  numberOfCustomers: number = 0;
  numberOfProducts: number = 0;
  numberOfOrders: number = 0;
  numberOfProductsSoldOut: number = 0;
  latestOrders: OrderStatistic[] = [];
  latestCustomers: UserStatistic[] = [];

  constructor(
    private userService: UserService,
    private statisticService: StatisticService
  ) {}

  ngOnInit(): void {
    this.user = this.userService.getUserResponseFromLocalStorage()!.fullname;
    this.countCustomer();
    this.countProducts();
    this.countOrders();
    this.countProductsSoldOut();
    this.getLatestOrders();
    this.getLatestCustomers();
  }

  countCustomer(): void {
    this.statisticService.countCustomers().subscribe({
      next: (response: any) => {
        debugger;
        this.numberOfCustomers = response.data;
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        console.error('Error getting roles:', error);
      },
    });
  }

  countProducts(): void {
    this.statisticService.countProducts().subscribe({
      next: (response: any) => {
        debugger;
        this.numberOfProducts = response.data;
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        console.error('Error getting roles:', error);
      },
    });
  }

  countOrders(): void {
    this.statisticService.countOrders().subscribe({
      next: (response: any) => {
        debugger;
        this.numberOfOrders = response.data;
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        console.error('Error getting roles:', error);
      },
    });
  }

  countProductsSoldOut(): void {
    this.statisticService.countProductsSoldOut().subscribe({
      next: (response: any) => {
        debugger;
        this.numberOfProductsSoldOut = response.data;
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        console.error('Error getting roles:', error);
      },
    });
  }

  getLatestOrders(): void {
    this.statisticService.getLatestOrders().subscribe({
      next: (response: any) => {
        debugger;
        this.latestOrders = response.data;
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        console.error('Error getting roles:', error);
      },
    });
  }

  getLatestCustomers(): void {
    this.statisticService.getLatestCustomers().subscribe({
      next: (response: any) => {
        debugger;
        this.latestCustomers = response.data;
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        console.error('Error getting roles:', error);
      },
    });
  }
}
