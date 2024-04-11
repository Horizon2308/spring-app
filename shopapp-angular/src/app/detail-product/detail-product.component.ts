import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/product.service';
import { Product } from '../models/product';
import { ProductImage } from '../models/product.image';
import { environment } from '../environments/environment';

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

  constructor(private productService: ProductService) {}
  ngOnInit() {
    const idParam = 5;
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

  previousImage(): void {
    debugger;
    this.showImage(this.currentIndexImage - 1);
  }
}
