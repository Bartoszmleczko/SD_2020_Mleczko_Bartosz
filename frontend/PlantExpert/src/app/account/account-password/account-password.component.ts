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
  passwordForm = this.fb.group({
    oldPassword: ["", Validators.compose([Validators.required])],
    newPassword: [
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(15),
      ]),
    ],
  });

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService
  ) {}

  ngOnInit() {}

  changePassword() {
    const data = {
      oldPassword: this.passwordForm.get("oldPassword").value,
      newPassword: this.passwordForm.get("newPassword").value,
    };
    this.passwordForm.reset();
    this.accountService.changePassword(data).subscribe((data) => {});
  }

  get f() {
    return this.passwordForm.controls;
  }
}
