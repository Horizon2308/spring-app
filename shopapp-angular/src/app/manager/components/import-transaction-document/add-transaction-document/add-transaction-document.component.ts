import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Provider } from 'src/app/admin/models/provider';
import { ProviderService } from 'src/app/admin/services/provider.service';
import { RawProductDTO } from 'src/app/manager/dtos/raw.product.dto';
import { TransactionDocumentDTO } from 'src/app/manager/dtos/transaction.document.dto';
import { TransactionDocumentService } from 'src/app/manager/services/transaction.document.service';
import { DialogConfirmComponent } from 'src/app/ultils/dialog-confirm/dialog-confirm.component';
import { UserService } from 'src/app/user/services/user.service';

@Component({
  selector: 'app-add-transaction-document',
  templateUrl: './add-transaction-document.component.html',
  styleUrls: ['./add-transaction-document.component.scss'],
})
export class AddTransactionDocumentComponent implements OnInit {
  transactionDocumentDTO: TransactionDocumentDTO = {
    name: '',
    user_id: 0,
    raw_products: [],
  };
  userName: string =
    this.userService.getUserResponseFromLocalStorage()!.fullname;

  transactionDocumentName: string = '';
  rawProducts: RawProductDTO[] = [];
  typingRawProduct: RawProductDTO = {
    name: '',
    note: '',
    price: 0,
    provider_id: 0,
    quantity: 0,
  };
  providers: Provider[] = [];
  today = new Date();

  day: string = '';
  month: string = '';
  year: number = 0;
  index: number = 0;
  totalPrice: number = 0;
  editedIndex: number = 0;

  editedRawProduct: RawProductDTO = {} as RawProductDTO;

  isModalOpen = false;

  constructor(
    private transactionDocumentService: TransactionDocumentService,
    private userService: UserService,
    private providerService: ProviderService,
    private dialog: MatDialog
  ) {}
  ngOnInit(): void {
    this.index = 0;
    this.year = this.today.getFullYear();
    this.month = String(this.today.getMonth() + 1).padStart(2, '0'); // Tháng bắt đầu từ 0
    this.day = String(this.today.getDate()).padStart(2, '0');
    this.getAllProviders();
    this.getTotalPrice();
  }

  createTransactionDocument(): void {
    debugger;
    this.transactionDocumentDTO.name = this.transactionDocumentName;
    this.transactionDocumentDTO.raw_products = this.rawProducts;
    this.transactionDocumentDTO.user_id =
      this.userService.getUserResponseFromLocalStorage()!.id;
    this.transactionDocumentService
      .addTransactionDocument(this.transactionDocumentDTO)
      .subscribe({
        next: (response: any) => {
          let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
            width: '250px',
            data: {
              title: 'Nhập đơn hàng',
              message: 'Nhập thành công',
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
  addRawProductToTransactionDocument(): void {
    this.rawProducts.push(this.typingRawProduct);
    this.clearTypingRawProduct();
    this.getTotalPrice();
  }

  getEditedRawProduct(index: number): void {
    this.isModalOpen = true;
    const rawProduct = this.rawProducts.at(index);
    if (rawProduct) {
      this.editedRawProduct = { ...rawProduct };
    }
    this.editedIndex = index;
  }

  editRawProduct(): void {
    this.rawProducts[this.editedIndex] = { ...this.editedRawProduct };
    this.isModalOpen = false;
    this.getTotalPrice();
  }
  clearTypingRawProduct(): void {
    this.typingRawProduct = {
      name: '',
      note: '',
      price: 0,
      provider_id: 0,
      quantity: 0,
    };
  }

  onModalClose() {
    this.isModalOpen = false;
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
}
