import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";
import {ShopComponent} from "./products/ui/shop/shop.component";
import {ShoppingCartComponent} from "./products/ui/shopping-cart/shopping-cart.component";
import {ContactComponent} from "./shared/features/contact/contact.component";

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
  { path: "", redirectTo: "home", pathMatch: "full" }
  , {
    path: "list-products-to-shop",
    component: ShopComponent,
  }, {
    path: "list-shopping-cart",
    component: ShoppingCartComponent,
  }, {
    path: "contact",
    component: ContactComponent,
  },
];
