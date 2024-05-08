import {
  Component,
  ElementRef,
  OnInit,
  Renderer2,
  ViewChild,
} from '@angular/core';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { UserService } from 'src/app/user/services/user.service';
import { ProviderService } from '../../services/provider.service';
import { ProviderDTO } from '../../dtos/provider.dto';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmComponent } from 'src/app/ultils/dialog-confirm/dialog-confirm.component';
import { Provider } from '../../models/provider';
import { CategoryService } from '../../services/category.service';
import { Category } from '../../models/category';
import { CategoryDTO } from '../../dtos/category.dto';
import { InsertProductDTO } from '../../dtos/insert.product.dto';
import { ProductService } from '../../services/products.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss'],
})
export class AddProductComponent implements OnInit {
  public Editor = ClassicEditor;
  public model = {
    editorData: '',
  };
  user: string = '';
  providerName: string = '';
  categoryName: string = '';
  providers: Provider[] = [];
  categories: Category[] = [];
  imagesUrl: string[] = [];
  insertProductDTO: InsertProductDTO = {
    name: '',
    price: 0,
    description: '',
    quantity: 0,
    product_status: 0,
    provider_id: 0,
    category_id: 0,
    images: [],
  };
  @ViewChild('closeProvider', { static: false }) closeProvider:
    | ElementRef
    | undefined;

  @ViewChild('closeCategory', { static: false }) closeCategory:
    | ElementRef
    | undefined;

  constructor(
    private userService: UserService,
    private providerService: ProviderService,
    private categoryService: CategoryService,
    private productService: ProductService,
    private dialog: MatDialog,
    private renderer: Renderer2
  ) {}

  ngOnInit(): void {
    this.user = this.userService.getUserResponseFromLocalStorage()!.fullname;
    this.getAllProviders();
    this.getCategories(0, 100);
  }

  addProvider(): void {
    debugger;
    let providerDTO: ProviderDTO = {
      provider_name: this.providerName,
    };
    this.providerService.addProvider(providerDTO).subscribe({
      next: (response: any) => {
        let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
          width: '250px',
          data: {
            title: 'Thêm nhà cung cấp',
            message: 'Thêm thành công',
          },
        });
        dialogConfirm.afterClosed().subscribe((result) => {
          if (result) {
            this.getAllProviders();
            this.renderer
              .selectRootElement(this.closeProvider!.nativeElement)
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
            title: 'Thêm nhà cung cấp',
            message: 'Thêm thất bại',
          },
        });
        dialogConfirm.afterClosed().subscribe((result) => {
          if (result) {
            location.reload();
          }
        });
      },
    });
  }

  getCategories(page: number, limit: number) {
    this.categoryService.getAllCategories(page, limit).subscribe({
      next: (response: any) => {
        debugger;
        this.categories = response.data.categories.content;
      },
      complete: () => {
        debugger;
      },
      error: (error: any) => {
        console.error('Error fetching categories:', error);
      },
    });
  }

  onFileChange(event: any) {
    debugger;
    // Retrieve selected files from input element
    const files = event.target.files;
    // Limit the number of selected files to 5
    if (files.length > 5) {
      alert('Please select a maximum of 5 images.');
      return;
    }
    for (let i = 0; i < files.length; i++) {
      this.imagesUrl[i] = URL.createObjectURL(files[i]);
    }
    // Store the selected files in the newProduct object
    this.insertProductDTO.images = files;
    debugger;
  }

  insertProduct() {
    this.insertProductDTO.description = this.model.editorData;
    this.productService.insertProduct(this.insertProductDTO).subscribe({
      next: (response) => {
        debugger;
        if (this.insertProductDTO.images.length > 0) {
          const productId = response.data.id; // Assuming the response contains the newly created product's ID
          this.productService
            .uploadImages(productId, this.insertProductDTO.images)
            .subscribe({
              next: (imageResponse) => {
                debugger;
                // Handle the uploaded images response if needed
                console.log('Images uploaded successfully:', imageResponse);
                let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
                  width: '250px',
                  data: {
                    title: 'Thêm sản phẩm',
                    message: 'Thêm sản phẩm thành công',
                  },
                });
                dialogConfirm.afterClosed().subscribe((result) => {
                  if (result) {
                    location.reload();
                  }
                });
              },
              error: (error) => {
                debugger;
                // Handle the error while uploading images
                alert(error.error);
                let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
                  width: '250px',
                  data: {
                    title: 'Thêm sản phẩm',
                    message: 'Thêm sản phẩm thất bại',
                  },
                });
                dialogConfirm.afterClosed().subscribe((result) => {
                  if (result) {
                    dialogConfirm.close();
                  }
                });
              },
            });
        } else {
          let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
            width: '250px',
            data: {
              title: 'Thêm sản phẩm',
              message: 'Thêm sản phẩm thành công',
            },
          });
          dialogConfirm.afterClosed().subscribe((result) => {
            if (result) {
              location.reload();
            }
          });
        }
      },
      error: (error) => {
        debugger;
        // Handle error while inserting the product
        alert(error.error);
        console.error('Error inserting product:', error);
        let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
          width: '250px',
          data: {
            title: 'Thêm sản phẩm',
            message: 'Thêm sản phẩm thất bại',
          },
        });
        dialogConfirm.afterClosed().subscribe((result) => {
          if (result) {
            dialogConfirm.close();
          }
        });
      },
    });
  }

  addCategory(): void {
    let categoryDTO: CategoryDTO = {
      category_name: this.categoryName,
    };
    this.categoryService.addCategory(categoryDTO).subscribe({
      next: (response: any) => {
        let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
          width: '250px',
          data: {
            title: 'Thêm danh mục',
            message: 'Thêm thành công',
          },
        });
        dialogConfirm.afterClosed().subscribe((result) => {
          if (result) {
            this.getCategories(0, 100);
            this.renderer
              .selectRootElement(this.closeCategory!.nativeElement)
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
            title: 'Thêm danh mục',
            message: 'Thêm thất bại',
          },
        });
        dialogConfirm.afterClosed().subscribe((result) => {
          if (result) {
            dialogConfirm.close();
          }
        });
      },
    });
  }

  getAllProviders(): void {
    this.providerService.getAllProviders().subscribe({
      next: (response: any) => {
        debugger;
        this.providers = response.data;
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
