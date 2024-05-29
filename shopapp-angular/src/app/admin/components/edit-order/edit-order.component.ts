import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { OrderResponse } from 'src/app/responses/order/order.response';
import { DialogConfirmComponent } from 'src/app/ultils/dialog-confirm/dialog-confirm.component';
import { environment } from 'src/app/user/environments/environment';
import { OrderDetail } from 'src/app/user/models/order.detail';
import { OrderService } from 'src/app/user/services/order.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit-order',
  templateUrl: './edit-order.component.html',
  styleUrls: ['./edit-order.component.scss'],
})
export class EditOrderComponent implements OnInit {
  orderId: number = 0;
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
  orderStatus: string = '';
  statusSelected: number = 0;

  constructor(
    private orderService: OrderService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    debugger;
    if (idParam !== null) {
      this.orderId = +idParam;
    }
    if (!isNaN(this.orderId) && this.orderId != 0) {
      this.orderService.getOrderById(this.orderId).subscribe({
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
          this.getSelectedStatus();
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

  updateOrder(): void {
    this.orderService
      .updateStatus(this.orderResponse.id, this.orderStatus)
      .subscribe({
        next: (response: any) => {
          let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
            width: '250px',
            data: {
              title: 'Cập nhật đơn hàng',
              message: 'Cập nhật thành công',
            },
          });
          dialogConfirm.afterClosed().subscribe((result) => {
            if (result) {
              location.reload();
              // this.renderer
              //   .selectRootElement(this.closeUpdate!.nativeElement)
              //   .click();
            }
          });
        },
        complete: () => {
          debugger;
        },
        error: (error: any) => {
          let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
            width: '250px',
            data: {
              title: 'Cập nhật đơn hàng',
              message: 'Cập nhật thất bại',
            },
          });
          dialogConfirm.afterClosed().subscribe((result) => {
            if (result) {
              // this.renderer
              //   .selectRootElement(this.closeUpdate!.nativeElement)
              //   .click();
            }
          });
        },
      });
  }

  anotherVersionOfUpdateOrder(): void {
    this.orderService
      .updateStatus(this.orderResponse.id, this.orderStatus)
      .subscribe({
        next: (response: any) => {
          let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
            width: '250px',
            data: {
              title: 'Cập nhật đơn hàng',
              message: 'Cập nhật thành công',
            },
          });
          dialogConfirm.afterClosed().subscribe((result) => {
            if (result) {
              this.router.navigate(['/admin/orders-manager']);
              // this.renderer
              //   .selectRootElement(this.closeUpdate!.nativeElement)
              //   .click();
            }
          });
        },
        complete: () => {
          debugger;
        },
        error: (error: any) => {
          let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
            width: '250px',
            data: {
              title: 'Cập nhật đơn hàng',
              message: 'Cập nhật thất bại',
            },
          });
          dialogConfirm.afterClosed().subscribe((result) => {
            if (result) {
              // this.renderer
              //   .selectRootElement(this.closeUpdate!.nativeElement)
              //   .click();
            }
          });
        },
      });
  }

  getSelectedStatus(): void {
    this.orderStatus = this.orderResponse.status;
    switch (this.orderResponse.status) {
      case 'pending':
        this.statusSelected = 1;
        break;
      case 'processing':
        this.statusSelected = 2;
        break;
      case 'shipped':
        this.statusSelected = 3;
        break;
      case 'delivered':
        this.statusSelected = 4;
        break;
      case 'success':
        this.statusSelected = 5;
        break;
    }
  }
  changeToPending() {
    this.orderStatus = 'pending';
    this.statusSelected = 1;
  }
  changeToProcessing() {
    this.orderStatus = 'processing';
    this.statusSelected = 2;
  }
  changeToShipped() {
    this.orderStatus = 'shipped';
    this.statusSelected = 3;
  }
  changeToDelivered() {
    this.orderStatus = 'delivered';
    this.statusSelected = 4;
  }
  changeToSuccess() {
    this.orderStatus = 'success';
    this.statusSelected = 5;
  }
  changeToCencelled() {
    this.orderStatus = 'cancelled';
    this.statusSelected = 0;
    this.anotherVersionOfUpdateOrder();
  }
  confirmOrder() {
    this.orderStatus = 'processing';
    this.statusSelected = 2;
    this.anotherVersionOfUpdateOrder();
  }
}
