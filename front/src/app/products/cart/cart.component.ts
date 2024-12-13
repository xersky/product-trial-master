import { Component } from '@angular/core';
import {Product} from "../data-access/product.model";
import {CartService} from "./cart.service";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {
  public cart: Product[];

  constructor(private cartService: CartService) {
    this.cart = this.cartService.getCartItems();
  }

  public removeFromCart(product: Product): void {
    this.cartService.removeFromCart(product);
  }
  getTotalPrice(): number {
    return this.cart.reduce((total, item) => total + (item.price * (item.quantity ?? 1)), 0);
  }
}
