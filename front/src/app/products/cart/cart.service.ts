import {Injectable} from "@angular/core";
import {BehaviorSubject, Observable} from "rxjs";
import {Product} from "../data-access/product.model";

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cart: Product[] = [];
  private cartSubject = new BehaviorSubject<Product[]>([]);
  private cartCountSubject = new BehaviorSubject<number>(0);

  cartCount$ = this.cartCountSubject.asObservable();
  private maxQuantity = 0;

  getCartItemsObservable(): Observable<Product[]> {
    return this.cartSubject.asObservable();
  }

  getCartItems(): Product[] {
    return this.cart;
  }

  addToCart(product: Product): void {
      const existingProduct = this.cart.find(item => item.id === product.id);
      if (product.quantity > 0) {
        if (existingProduct) {
          existingProduct.quantity++;
        } else {
          this.cart.push({...product, quantity: 1});
        }
        product.quantity--;
        this.updateCart();
      }
  }

  removeFromCart(product: Product): void {
    const index = this.cart.findIndex(item => item.id === product.id);
    if (index !== -1) {
      this.cart.splice(index, 1);
    }
    this.updateCart();
  }

  private updateCart(): void {
    const totalCount = this.cart.reduce((count, item) => count + (item.quantity ?? 1), 0);
    this.cartCountSubject.next(totalCount);
    this.cartSubject.next(this.cart);
  }
}
