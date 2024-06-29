import { Component } from '@angular/core';

@Component({
  selector: 'app-provider-manager',
  templateUrl: './provider-manager.component.html',
  styleUrls: ['./provider-manager.component.scss'],
})
export class ProviderManagerComponent {
  isModalOpen = false;

  openModal() {
    this.isModalOpen = true;
  }

  onModalClose() {
    this.isModalOpen = false;
  }
}
