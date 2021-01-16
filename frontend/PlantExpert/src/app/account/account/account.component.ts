import { UserDetails } from "./../../models/models";
import { ViewChild } from "@angular/core";
import { Component, OnInit } from "@angular/core";
import { AccountDetailsCardComponent } from "../account-details-card/account-details-card.component";
import { AccountDetailsComponent } from "../account-details/account-details.component";

@Component({
  selector: "app-account",
  templateUrl: "./account.component.html",
  styleUrls: ["./account.component.css"],
})
export class AccountComponent implements OnInit {
  @ViewChild(AccountDetailsCardComponent)
  accountDetailsComponent: AccountDetailsCardComponent;

  constructor() {}

  ngOnInit() {}

  emitNewDetails(event: UserDetails) {
    this.accountDetailsComponent.details.firstName = event.firstName;
    this.accountDetailsComponent.details.lastName = event.lastName;
  }
}
