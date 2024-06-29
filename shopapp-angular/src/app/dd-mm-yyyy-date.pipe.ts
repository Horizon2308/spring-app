import { DatePipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'ddMmYYYYDate',
})
export class DdMmYYYYDatePipe extends DatePipe implements PipeTransform {
  override transform(value: any, args?: any): any {
    return super.transform(value, 'dd/MM/yyyy HH:mm:ss');
  }
}
