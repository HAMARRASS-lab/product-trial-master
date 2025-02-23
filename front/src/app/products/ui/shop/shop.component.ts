import {Component, computed, inject, OnInit, signal} from '@angular/core';
import {ProductsService} from "../../data-access/products.service";
import {Product} from "../../data-access/product.model";




@Component({
  selector: 'app-shop',
  standalone: true,
  imports: [],
  templateUrl: './shop.component.html',
  styleUrl: './shop.component.css'
})
export class ShopComponent implements OnInit{
  private readonly productsService = inject(ProductsService);

  public readonly products = this.productsService.products;

  public currentPage = signal<number>(1);
  public pageSize = signal<number>(9); // Number of products per page

  ngOnInit() {
    this.productsService.get().subscribe();
  }

  addToCart(product: Product) {
    const currentCart = this.productsService.productsListInCart();

    const existingProduct = currentCart.find(item => item.id === product.id);

    if (existingProduct) {
      const updatedCart = currentCart.map(item =>
        item.id === product.id ? { ...item, quantity: item.quantity + 1 } : item
      );
      this.productsService.productsListInCart.set(updatedCart);
    } else {
      this.productsService.productsListInCart.set([...currentCart, { ...product, quantity: 1 }]);
    }
  }
  public paginatedProducts = computed(() => {
    const startIndex = (this.currentPage() - 1) * this.pageSize();
    return this.products().slice(startIndex, startIndex + this.pageSize());
  });

  previousPage() {
    if (this.currentPage() > 1) {
      this.currentPage.update(value => value - 1);
    }
  }

  nextPage() {
    const totalPages = Math.ceil(this.products().length / this.pageSize());
    if (this.currentPage() < totalPages) {
      this.currentPage.update(value => value + 1);
    }
  }

  protected readonly Math = Math;
}
