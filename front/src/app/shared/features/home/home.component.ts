import { Component } from "@angular/core";
import { RouterLink } from "@angular/router";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import {ShopComponent} from "../../../products/ui/shop/shop.component";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"],
  standalone: true,
  imports: [CardModule, RouterLink, ButtonModule, ShopComponent],
})
export class HomeComponent {
  public readonly appTitle = "ALTEN SHOP";
}
