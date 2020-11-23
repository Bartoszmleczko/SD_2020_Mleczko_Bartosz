import { Component, OnInit } from "@angular/core";
import { TokenStorageService } from "../services/token-storage.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.css"],
})
export class HeaderComponent implements OnInit {
  constructor(
    private tokenStorage: TokenStorageService,
    private router: Router
  ) {}

  user = null;

  ngOnInit() {
    this.user = this.tokenStorage.getUser();
  }

  logout() {
    this.tokenStorage.signOut();
    this.router.navigate(["/login"]);
  }
}
