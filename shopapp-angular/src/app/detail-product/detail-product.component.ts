import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/product.service';
import { Product } from '../models/product';
import { ProductImage } from '../models/product.image';
import { environment } from '../environments/environment';
import { CartService } from '../services/cart.service';
import { ActivatedRoute, Router } from '@angular/router';

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
    private activatedRoute: ActivatedRoute,
    private router: Router
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
    this.isPressedAddToCart = true;
    if (this.product) {
      this.cartService.addProductIntoCartItems(this.productId, this.quantity);
      alert('Sản phẩm đã được thêm vào giỏ hàng!');
    } else {
      alert('Không thể thêm sản phẩm vào giỏ hàng!');
    }
  }
  buyNow(): void {
    if (this.isPressedAddToCart == false) {
      this.addProductToCart();
    }
    this.router.navigate(['/orders']);
  }
}
