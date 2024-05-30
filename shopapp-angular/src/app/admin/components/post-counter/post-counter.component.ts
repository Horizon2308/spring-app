import { Component, ElementRef, Renderer2, ViewChild } from '@angular/core';
import { ProductService } from '../../services/products.service';
import { Product } from '../../models/product';
import { environment } from 'src/app/user/environments/environment';
import { Customer } from '../../models/customer';
import { CustomerService } from '../../services/customer.service';
import { CustomerDTO } from '../../dtos/customer.dto';
import { DialogConfirmComponent } from 'src/app/ultils/dialog-confirm/dialog-confirm.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-post-counter',
  templateUrl: './post-counter.component.html',
  styleUrls: ['./post-counter.component.scss'],
})
export class PostCounterComponent {
  searchTerm: string = '';
  showDropDown: boolean = false;
  filterdOptions: Product[] = [];
  selectedProduct: Product[] = [];

  customers: Customer[] = [];
  searchCustomerTerm: string = '';
  showDropDownForCustomer: boolean = false;
  selectedCustomer: Customer = {} as Customer;
  customerDTO: CustomerDTO = {
    fullname: '',
    email: '',
    address: '',
    phone_number: '',
  };

  @ViewChild('closeCustomer', { static: false }) closeCustomer:
    | ElementRef
    | undefined;

  constructor(
    private productService: ProductService,
    private customerService: CustomerService,
    private dialog: MatDialog,
    private renderer: Renderer2
  ) {}

  onSearch(): void {
    if (this.searchTerm === '') {
      this.filterdOptions = [];
      this.showDropDown = false;
      return;
    }

    this.productService.searchProducts(this.searchTerm).subscribe({
      next: (response: any) => {
        response.data.forEach((product: Product) => {
          product.url = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`;
          product.counter_quantity = 1;
        });
        this.filterdOptions = response.data;
        this.showDropDown = this.filterdOptions.length > 0;
        if (this.showDropDown) {
          document.querySelector('.dropdown')?.classList.add('show');
        } else {
          document.querySelector('.dropdown')?.classList.remove('show');
        }
      },
      complete: () => {},
      error: (error: any) => {
        console.error('Error fetching products:', error);
      },
    });
  }

  onProductClick(id: number): void {
    const existedProduct = this.selectedProduct.find(
      (product) => product.id == id
    );
    if (existedProduct) {
      existedProduct.counter_quantity += 1;
    } else {
      this.productService.getProduct(id).subscribe({
        next: (response: any) => {
          debugger;
          this.selectedProduct.push(response.data);
        },
        complete: () => {
          debugger;
        },
        error: (error: any) => {
          debugger;
          alert(error.error);
          console.error('Error fetching products:', error);
        },
      });
    }
  }

  removeProduct(id: number): void {
    this.selectedProduct = this.selectedProduct.filter((p) => p.id !== id);
  }

  searchCustomer(): void {
    if (this.searchCustomerTerm === '') {
      this.customers = [];
      this.showDropDownForCustomer = false;
      return;
    }

    this.customerService.searchCustomers(this.searchCustomerTerm).subscribe({
      next: (response: any) => {
        this.customers = response.data;
        this.showDropDownForCustomer = this.customers.length > 0;
        if (this.showDropDownForCustomer) {
          document.querySelector('.dropdown')?.classList.add('show');
        } else {
          document.querySelector('.dropdown')?.classList.remove('show');
        }
      },
      complete: () => {},
      error: (error: any) => {
        console.error('Error fetching products:', error);
      },
    });
  }

  createCustomer() {
    this.customerService.createCustomer(this.customerDTO).subscribe({
      next: (response: any) => {
        let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
          width: '250px',
          data: {
            title: 'Thêm khách hàng',
            message: 'Thêm thành công',
          },
        });
        dialogConfirm.afterClosed().subscribe((result) => {
          if (result) {
            this.renderer
              .selectRootElement(this.closeCustomer!.nativeElement)
              .click();
          }
        });
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        debugger;
        console.log(error.error);
        let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
          width: '250px',
          data: {
            title: 'Thêm khách hàng',
            message: 'Thêm thất bại',
          },
        });
        dialogConfirm.afterClosed().subscribe((result) => {
          if (result) {
            this.renderer
              .selectRootElement(this.closeCustomer!.nativeElement)
              .click();
          }
        });
      },
    });
  }
}
