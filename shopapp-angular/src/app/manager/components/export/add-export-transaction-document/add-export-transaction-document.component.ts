import { ChangeDetectorRef, Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ExportRawProductDTO } from 'src/app/manager/dtos/export.raw.product.dto';
import { ExportTransactionDocumentDTO } from 'src/app/manager/dtos/export.transaction.document.dto';
import { RawProductDTO } from 'src/app/manager/dtos/raw.product.dto';
import { RawProduct } from 'src/app/manager/models/raw.product';
import { Store } from 'src/app/manager/models/store';
import { RawProductService } from 'src/app/manager/services/raw.product.service';
import { StoreService } from 'src/app/manager/services/store.service';
import { TransactionDocumentService } from 'src/app/manager/services/transaction.document.service';
import { DialogConfirmComponent } from 'src/app/ultils/dialog-confirm/dialog-confirm.component';
import { UserService } from 'src/app/user/services/user.service';

@Component({
  selector: 'app-add-export-transaction-document',
  templateUrl: './add-export-transaction-document.component.html',
  styleUrls: ['./add-export-transaction-document.component.scss'],
})
export class AddExportTransactionDocumentComponent {
  transactionDocumentDTO: ExportTransactionDocumentDTO = {
    name: '',
    user_id: 0,
    raw_products: [],
  };
  userName: string =
    this.userService.getUserResponseFromLocalStorage()!.fullname;

  transactionDocumentName: string = '';
  rawProducts: ExportRawProductDTO[] = [];
  typingRawProduct: ExportRawProductDTO = {
    store_id: 0,
  } as ExportRawProductDTO;
  stores: Store[] = [];
  today = new Date();

  day: string = '';
  month: string = '';
  year: number = 0;
  index: number = 0;
  totalPrice: number = 0;
  editedIndex: number = 0;

  editedRawProduct: ExportRawProductDTO = {} as ExportRawProductDTO;

  searchTerm: string = '';
  showDropDown: boolean = false;
  filterdOptions: RawProduct[] = [];

  isModalOpen = false;

  quantity: number = 0;

  constructor(
    private transactionDocumentService: TransactionDocumentService,
    private userService: UserService,
    private rawProductService: RawProductService,
    private storeService: StoreService,
    private cdr: ChangeDetectorRef,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.index = 0;
    this.year = this.today.getFullYear();
    this.month = String(this.today.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
    this.day = String(this.today.getDate()).padStart(2, '0');
    this.getAllStores();
  }

  getAllStores(): void {
    this.storeService.getAllStore().subscribe({
      next: (response: any) => {
        debugger;
        this.stores = response.data;
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

  onSearch(): void {
    debugger;
    if (this.searchTerm === '') {
      this.filterdOptions = [];
      this.showDropDown = false;
      return;
    }

    this.rawProductService.searchRawProducts(this.searchTerm).subscribe({
      next: (response: any) => {
        debugger;
        this.filterdOptions = response.data;
        this.showDropDown = this.filterdOptions.length > 0;
        // if (this.showDropDown) {
        //   document.querySelector('.dropdown')?.classList.add('show');
        // } else {
        //   document.querySelector('.dropdown')?.classList.remove('show');
        // }
        this.cdr.detectChanges();
      },
      complete: () => {},
      error: (error: any) => {
        debugger;
        console.error('Error fetching products:', error);
      },
    });
  }

  createTransactionDocument(): void {
    debugger;
    this.transactionDocumentDTO.name = this.transactionDocumentName;
    this.transactionDocumentDTO.raw_products = this.rawProducts;
    this.transactionDocumentDTO.user_id =
      this.userService.getUserResponseFromLocalStorage()!.id;
    this.transactionDocumentService
      .addExportTransactionDocument(this.transactionDocumentDTO)
      .subscribe({
        next: (response: any) => {
          let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
            width: '250px',
            data: {
              title: 'Xuất đơn hàng',
              message: 'Xuất thành công',
            },
          });
          dialogConfirm.afterClosed().subscribe((result) => {
            if (result) {
              location.reload();
            }
          });
        },
        complete: () => {
          debugger;
        },
        error: (error: any) => {
          debugger;
          alert(error.error);
          console.error('Error creating transaction document:', error);
        },
      });
  }

  onRawProductClick(id: number): void {
    this.filterdOptions.forEach((item) => {
      if (item.id === id) {
        this.typingRawProduct.id = item.id;
        this.typingRawProduct.name = item.name;
        this.typingRawProduct.price = item.price;
        this.typingRawProduct.store_id = 0;
        this.showDropDown = false;
      }
    });
  }

  addRawProductToTransactionDocument(): void {
    this.typingRawProduct.quantity = this.quantity;
    this.quantity = 0;
    this.rawProducts.push(this.typingRawProduct);
    this.clearTypingRawProduct();
    this.getTotalPrice();
  }

  removeRawProduct(index: number) {
    this.rawProducts.splice(index, 1);
    this.getTotalPrice();
  }

  getTotalPrice(): void {
    this.totalPrice = 0;
    this.rawProducts.forEach((item) => {
      this.totalPrice += item.price;
    });
  }

  clearTypingRawProduct(): void {
    this.typingRawProduct = {
      id: 0,
      name: '',
      price: 0,
      store_id: 0,
      quantity: 0,
    };
  }
}
