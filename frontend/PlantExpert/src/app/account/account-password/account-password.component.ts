import { Component, OnInit } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";

@Component({
  selector: "app-account-password",
  templateUrl: "./account-password.component.html",
  styleUrls: ["./account-password.component.css"],
})
export class AccountPasswordComponent implements OnInit {
  passwordForm = this.fb.group({
    oldPassword: [
      "",
      Validators.compose([Validators.required, Validators.minLength(3)]),
    ],
    newPassword: [
      "",
      Validators.compose([Validators.required, Validators.minLength(8)]),
    ],
    matchingPassword: [
      "",
      Validators.compose([Validators.required, Validators.minLength(8)]),
    ],
  });

  constructor(private fb: FormBuilder) {}

  //TODO add matchingPasswordValidator

  get f() {
    return this.passwordForm.controls;
  }

  ngOnInit() {}
}
