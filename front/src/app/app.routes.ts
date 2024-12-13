import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";
import {CartComponent} from "./products/cart/cart.component";
import {ContactComponent} from "./products/contact/contact.component";

export const APP_ROUTES: Routes = [
  {
    path: "home",
    component: HomeComponent,
  },
  {
    path: "products",
    loadChildren: () =>
      import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES)
  },
  { path: "", redirectTo: "home", pathMatch: "full" },
  { path: "cart", component: CartComponent},
  { path: "contact",component: ContactComponent}
];
