import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { Product } from '../../models/product';
import { ProductImage } from '../../models/product.image';
import { environment } from '../../environments/environment';
import { CartService } from '../../services/cart.service';
import { ActivatedRoute, Router } from '@angular/router';
import { DialogConfirmComponent } from 'src/app/ultils/dialog-confirm/dialog-confirm.component';
import { MatDialog } from '@angular/material/dialog';
import { HeaderComponent } from '../header/header.component';
import { OrderComponent } from '../order/order.component';

@Component({
  selector: 'app-detail-product',
  templateUrl: './detail-product.component.html',
  styleUrls: ['./detail-product.component.scss'],
})
export class DetailProductComponent implements OnInit {
  product?: Product;
  productId: number = 0;
  currentIndexImage: number = 0;
  quantity: number = 1;
  isPressedAddToCart: boolean = false;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private headerComponent: HeaderComponent,
    private orderComponent: OrderComponent,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private dialog: MatDialog
  ) {}
  ngOnInit() {
    const idParam = this.activatedRoute.snapshot.paramMap.get('id');
    debugger;
    if (idParam !== null) {
      this.productId = +idParam;
    }
    if (!isNaN(this.productId)) {
      this.productService.getProductDetails(this.productId).subscribe({
        next: (response: any) => {
          if (
            response.data.product_images &&
            response.data.product_images.length > 0
          ) {
            response.data.product_images.forEach(
              (product_image: ProductImage) => {
                product_image.url = `${environment.apiBaseUrl}/products/images/${product_image.url}`;
              }
            );
          }
          this.product = response.data;
          debugger;
          this.showImage(0);
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

  showImage(index: number) {
    if (
      this.product &&
      this.product.product_images &&
      this.product.product_images.length > 0
    ) {
      if (index < 0) {
        index = 0;
      }
      if (index > this.product.product_images.length - 1) {
        index = this.product.product_images.length - 1;
      }
      this.currentIndexImage = index;
    }
  }

  thumbnailClick(index: number) {
    this.currentIndexImage = index;
  }

  nextImage(): void {
    debugger;
    this.showImage(this.currentIndexImage + 1);
  }

  decreaseQuantity(): void {
    --this.quantity;
    if (this.quantity <= 0 && this.product) {
      this.quantity = 1;
    }
  }

  increaseQuantity(): void {
    ++this.quantity;
    if (this.quantity > this.product!.quantity && this.product) {
      this.quantity = this.product!.quantity;
    }
  }

  previousImage(): void {
    debugger;
    this.showImage(this.currentIndexImage - 1);
  }

  addProductToCart() {
    debugger;
    this.isPressedAddToCart = true;
    if (this.product) {
      this.cartService.addProductIntoCartItems(this.productId, this.quantity);
      let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
        width: '250px',
        data: {
          title: 'Thêm vào giỏ hàng',
          message: 'Thêm sản phẩm thành công',
        },
      });
      debugger;
      this.headerComponent.refreshNumberOfProducts();
      // this.headerComponent.numberOfProducts =
      //   this.cartService.getNumberOfProducts();
      //this.orderComponent.updateCartFromCartItems();
      dialogConfirm.afterClosed().subscribe((result) => {
        if (result) {
          dialogConfirm.close();
        }
      });
    } else {
      let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
        width: '250px',
        data: {
          title: 'Thêm vào giỏ hàng',
          message: 'Không thể thêm sản phẩm',
        },
      });
      dialogConfirm.afterClosed().subscribe((result) => {
        if (result) {
          dialogConfirm.close();
        }
      });
    }
  }
  buyNow(): void {
    if (this.isPressedAddToCart == false) {
      this.addProductToCart();
    }
    this.router.navigate(['/orders']);
  }
}
