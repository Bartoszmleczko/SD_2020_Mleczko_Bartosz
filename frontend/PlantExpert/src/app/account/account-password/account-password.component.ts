import { Component, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  ValidatorFn,
  Validators,
} from "@angular/forms";
import { MatDialog } from "@angular/material";
import { BackendMessageComponent } from "src/app/backend-message/backend-message/backend-message.component";
import { AccountService } from "../account.service";

@Component({
  selector: "app-account-password",
  templateUrl: "./account-password.component.html",
  styleUrls: ["./account-password.component.css"],
})
export class AccountPasswordComponent implements OnInit {
  constructor() {}

  ngOnInit() {}
}
