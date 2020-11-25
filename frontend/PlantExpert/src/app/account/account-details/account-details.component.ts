import { FormBuilder, Validators } from "@angular/forms";
import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { AccountService } from "../account.service";
import { MatDialog } from "@angular/material";
import { BackendMessageComponent } from "src/app/backend-message/backend-message/backend-message.component";
import { UserDetails } from "src/app/models/models";

@Component({
  selector: "app-account-details",
  templateUrl: "./account-details.component.html",
  styleUrls: ["./account-details.component.css"],
})
export class AccountDetailsComponent implements OnInit {
  @Output()
  accountDetailsEmitter: EventEmitter<UserDetails> = new EventEmitter();

  detailsForm = this.fb.group({
    firstName: [
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(20),
      ]),
    ],
    lastName: [
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(20),
      ]),
    ],
  });

  constructor(
    private fb: FormBuilder,
    private accountService: AccountService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {}

  get f() {
    return this.detailsForm.controls;
  }

  changeDetails() {
    const details: UserDetails = {
      firstName: this.detailsForm.get("firstName").value,
      lastName: this.detailsForm.get("lastName").value,
      joinDate: null,
    };

    this.accountService.changeDetails(details).subscribe(
      (data) => {
        this.detailsForm.reset();
        this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: { data: "Pomyślnie zmieniono dane." },
        });
      },
      (err) => {
        console.log(err);
        this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: { data: err.error },
        });
      }
    );
    this.accountDetailsEmitter.emit(details);
  }
}
