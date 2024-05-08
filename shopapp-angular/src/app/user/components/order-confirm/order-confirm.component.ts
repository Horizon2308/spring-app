import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';
import { ActivatedRoute } from '@angular/router';
import { OrderResponse } from '../../../responses/order/order.response';
import { OrderDetail } from '../../models/order.detail';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-order-confirm',
  templateUrl: './order-confirm.component.html',
  styleUrls: ['./order-confirm.component.scss'],
})
export class OrderConfirmComponent implements OnInit {
  orderResponse: OrderResponse = {
    id: 0, // Hoặc bất kỳ giá trị số nào bạn muốn
    user_id: 0,
    fullname: '',
    phone_number: '',
    email: '',
    address: '',
    note: '',
    order_date: new Date(),
    status: '',
    total_money: 0, // Hoặc bất kỳ giá trị số nào bạn muốn
    shipping_method: '',
    shipping_address: '',
    shipping_date: new Date(),
    payment_method: '',
    order_details: [], // Một mảng rỗng
  };
  constructor(
    private orderService: OrderService,
    private route: ActivatedRoute,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getOrderDetails();
  }

  getOrderDetails(): void {
    debugger;
    const orderId = Number(this.activatedRoute.snapshot.paramMap.get('id'));
    this.orderService.getOrderById(orderId).subscribe({
      next: (response: any) => {
        debugger;
        this.orderResponse.id = response.data.id;
        this.orderResponse.user_id = response.data.user_id;
        this.orderResponse.fullname = response.data.full_name;
        this.orderResponse.email = response.data.email;
        this.orderResponse.phone_number = response.data.phone_number;
        this.orderResponse.address = response.data.shipping_address;
        this.orderResponse.note = response.data.note;
        this.orderResponse.order_date = new Date(
          response.data.order_date[0],
          response.data.order_date[1] - 1,
          response.data.order_date[2]
        );

        if (response.data.order_details) {
          this.orderResponse.order_details = response.data.order_details.map(
            (order_detail: OrderDetail) => {
              order_detail.product.thumbnail = `${environment.apiBaseUrl}/products/images/${order_detail.product.thumbnail}`;
              return order_detail;
            }
          );
        }
        this.orderResponse.payment_method = response.data.payment_method;
        if (response.data.shipping_date) {
          this.orderResponse.shipping_date = new Date(
            response.data.shipping_date[0],
            response.data.shipping_date[1] - 1,
            response.data.shipping_date[2]
          );
        }

        this.orderResponse.shipping_method = response.data.shipping_method;

        this.orderResponse.status = response.data.status;
        this.orderResponse.total_money = response.data.total_money;
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        console.error('Error fetching detail:', error);
      },
    });
  }
}
