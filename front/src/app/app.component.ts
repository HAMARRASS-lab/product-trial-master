import {
  Component, inject,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import {ProductsService} from "./products/data-access/products.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent],
})
export class AppComponent {
  title = "ALTEN SHOP";

  private readonly productsService = inject(ProductsService);

  get totalProductQuantity(): number {
    return this.productsService.productsListInCart().reduce(
      (total, product) => total + product.quantity,
      0
    );
  }
}
