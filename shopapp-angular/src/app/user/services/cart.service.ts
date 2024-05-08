import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cart: Map<number, number> = new Map<number, number>();
  constructor() {
    this.refreshCartItems();
  }

  private getCartKey(): string {
    const userResponseJson = localStorage.getItem('user');
    const userResponse = JSON.parse(userResponseJson!);
    return `cart_${userResponse?.id ?? ''}`;
  }

  refreshCartItems(): void {
    const storedCart = localStorage.getItem(this.getCartKey());
    if (storedCart) {
      this.cart = new Map(JSON.parse(storedCart));
    } else {
      this.cart = new Map<number, number>();
    }
  }

  private storeCartItems(): void {
    debugger;
    localStorage.setItem(
      this.getCartKey(),
      JSON.stringify(Array.from(this.cart.entries()))
    );
  }

  addProductIntoCartItems(productId: number, quantity: number): void {
    debugger;
    if (this.cart.has(productId)) {
      this.cart.set(productId, this.cart.get(productId)! + quantity);
    } else {
      this.cart.set(productId, quantity);
    }
    this.storeCartItems();
  }

  getCartItems(): Map<number, number> {
    return this.cart;
  }

  setCartItems(cart: Map<number, number>): void {
    this.cart = cart;
    this.storeCartItems();
  }

  getNumberOfProducts(): number {
    return this.cart.size;
  }

  clearCartItems(): void {
    this.cart.clear();
    this.storeCartItems();
  }
}
