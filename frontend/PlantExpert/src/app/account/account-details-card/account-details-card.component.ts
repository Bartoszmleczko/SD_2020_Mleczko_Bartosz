import { Component, OnInit, ViewChild } from "@angular/core";
import { UserDetails } from "src/app/models/models";
import { AccountService } from "../account.service";

@Component({
  selector: "app-account-details-card",
  templateUrl: "./account-details-card.component.html",
  styleUrls: ["./account-details-card.component.css"],
})
export class AccountDetailsCardComponent implements OnInit {
  details: UserDetails;

  constructor(private accountService: AccountService) {}

  ngOnInit() {
    this.getDetails();
  }

  getDetails() {
    this.accountService.getDetails().subscribe((data: UserDetails) => {
      console.log(data);
      this.details = data;
    });
  }
}
