import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/user/services/user.service';
import { StatisticService } from '../../services/statistic.service';
import { environment } from 'src/app/user/environments/environment';
import { Product } from '../../models/product';
import { Order } from 'src/app/user/models/order';
import { OrderDetail } from 'src/app/user/models/order.detail';
import { OrderService } from 'src/app/user/services/order.service';

@Component({
  selector: 'app-income-managerment',
  templateUrl: './income-managerment.component.html',
  styleUrls: ['./income-managerment.component.scss'],
})
export class IncomeManagermentComponent implements OnInit {
  user: string = '';
  avatar: string = '';
  numberOfStaffs: number = 0;
  numberOfProducts: number = 0;
  numberOfOrders: number = 0;
  totalMoney: number = 0;
  numberOfProductsSoldOut: number = 0;
  numberOfCancelledOrder: number = 0;
  popularProducts: Product[] = [];
  orders: Order[] = [];
  productsIsSoldOut: Product[] = [];

  constructor(
    private userService: UserService,
    private statisticService: StatisticService,
    private orderService: OrderService
  ) {}
  ngOnInit(): void {
    this.user = this.userService.getUserResponseFromLocalStorage()!.fullname;
    this.avatar = this.userService.getUserResponseFromLocalStorage()!.avatar;
    this.avatar = `${environment.apiBaseUrl}/users/avatar/${this.avatar}`;
    this.countStaffs();
    this.countProducts();
    this.countOrders();
    this.getTotalMoney();
    this.countProductsSoldOut();
    this.countCancelledOrders();
    this.getPopularProducts();
    this.getAllOrders();
    this.getProductsIsSoldOut();
  }

  countStaffs(): void {
    this.statisticService.countStaffs().subscribe({
      next: (response: any) => {
        debugger;
        this.numberOfStaffs = response.data;
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
  getTotalMoney(): void {
    this.statisticService.getTotalMoney().subscribe({
      next: (response: any) => {
        debugger;
        this.totalMoney = response.data;
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

  countCancelledOrders(): void {
    this.statisticService.countCancelledOrder().subscribe({
      next: (response: any) => {
        debugger;
        this.numberOfCancelledOrder = response.data;
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

  getPopularProducts(): void {
    this.statisticService.getPopularProducts().subscribe({
      next: (response: any) => {
        debugger;
        this.popularProducts = response.data;
      },
      complete: () => {},
      error: (error: any) => {
        console.error('Error fetching products:', error);
      },
    });
  }

  getAllOrders(): void {
    this.statisticService.getSuccessfulOrders().subscribe({
      next: (response: any) => {
        debugger;
        this.orders = response.data;
        this.getListOfProductName();
      },
      complete: () => {},
      error: (error: any) => {
        console.error('Error fetching products:', error);
      },
    });
  }

  getListOfProductName(): void {
    debugger;
    this.orders.forEach((order: Order) => {
      order.listOfProductName = '';
      order.quantity = 0;
      order.order_details.forEach((orderDetails: OrderDetail) => {
        debugger;
        order.listOfProductName += orderDetails.product.name;
        order.listOfProductName += ', ';
        order.quantity += orderDetails.number_of_products;
      });
      order.listOfProductName = order.listOfProductName.substring(
        0,
        order.listOfProductName.length - 2
      );
    });
  }

  getProductsIsSoldOut(): void {
    this.statisticService.getProductsIsSoldOut().subscribe({
      next: (response: any) => {
        debugger;
        response.data.forEach((product: Product) => {
          product.url = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`;
        });
        this.productsIsSoldOut = response.data;
      },
      complete: () => {},
      error: (error: any) => {
        console.error('Error fetching products:', error);
      },
    });
  }
}
