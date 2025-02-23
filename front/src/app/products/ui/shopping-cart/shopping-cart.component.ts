import {Component, EventEmitter, inject, OnInit, Output} from '@angular/core';
import {ProductsService} from "../../data-access/products.service";
import {Product} from "../../data-access/product.model";

@Component({
  selector: 'app-shopping-cart',
  standalone: true,
  imports: [],
  templateUrl: './shopping-cart.component.html',
  styleUrl: './shopping-cart.component.css'
})
export class ShoppingCartComponent implements OnInit{

  private readonly productsService = inject(ProductsService);

  public readonly productsList = this.productsService.productsListInCart;

  ngOnInit() {

  }

  increaseQuantity(product: any) {
    product.quantity++;
  }

  decreaseQuantity(product: any) {
    if (product.quantity > 1) {
      product.quantity--;
    } else {
      this.removeFromCart(product);
    }
  }

  removeFromCart(product: Product) {
    this.productsService.productsListInCart.set(
      this.productsService.productsListInCart().filter(item => item.name !== product.name)
    );
  }

  getTotalPrice(): number {
    return this.productsService.productsListInCart().reduce(
      (total, product) => total + product.price * product.quantity,
      0
    );
  }

}
