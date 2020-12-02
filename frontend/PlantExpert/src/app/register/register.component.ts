import { BackendMessageComponent } from "./../backend-message/backend-message/backend-message.component";
import { MatDialog } from "@angular/material";
import { Validators } from "@angular/forms";
import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { RegisterService } from "../services/register.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.css"],
})
export class RegisterComponent implements OnInit {
  registerForm = this.fb.group({
    email: ["", Validators.compose([Validators.email, Validators.required])],
    password: [
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(20),
      ]),
    ],
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
        Validators.maxLength(30),
      ]),
    ],
  });

  constructor(
    private fb: FormBuilder,
    private registerService: RegisterService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit() {}

  register() {
    const registerRequest = {
      email: this.registerForm.get("email").value,
      password: this.registerForm.get("password").value,
      firstName: this.registerForm.get("firstName").value,
      lastName: this.registerForm.get("lastName").value,
    };
    this.registerService.register(registerRequest).subscribe(
      (data) => {
        this.registerForm.reset();
        this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: { data: "Link aktywacyjny został wysłany na adres email." },
        });
      },

      (err) => {
        this.registerForm.reset();
        this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: { data: err.error },
        });
      }
    );
    this.router.navigate(["/login"]);
  }

  get f() {
    return this.registerForm.controls;
  }
}
