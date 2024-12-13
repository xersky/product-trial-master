import {
  Component,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import {CartService} from "./products/cart/cart.service";
import {DialogModule} from "primeng/dialog";
import {Product} from "./products/data-access/product.model";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, DialogModule],
})
export class AppComponent {
  title = "ALTEN SHOP";
  cartCount = 0;
  cartItems: Product[] = [];

  constructor(private cartService: CartService) {
    this.cartService.cartCount$.subscribe((count) => {
      this.cartCount = count;
    });

    this.cartService.getCartItemsObservable().subscribe((items) => {
      this.cartItems = items;
    });
  }
}
