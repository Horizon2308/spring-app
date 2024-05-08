import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfirmComponent } from './dialog-confirm/dialog-confirm.component';

@Injectable({
  providedIn: 'root',
})
export class DialogUtilities {
  constructor(private dialog: MatDialog) {}

    getConfirmDialog(title: string, content: string) {
      let dialogConfirm = this.dialog.open(DialogConfirmComponent, {
        width: '250px',
        data: {
          title: title,
          message: content,
        },
      });
      dialogConfirm.afterClosed().subscribe((result) => {
        if (result) {
          location.reload();
        }
      });
  }
}
