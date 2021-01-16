import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { MatDialog } from "@angular/material";
import { Router } from "@angular/router";
import { BackendMessageComponent } from "src/app/backend-message/backend-message/backend-message.component";
import { AuthenticationService } from "src/app/services/authentication.service";
import { TokenStorageService } from "src/app/services/token-storage.service";

@Component({
  selector: "app-login-form",
  templateUrl: "./login-form.component.html",
  styleUrls: ["./login-form.component.css"],
})
export class LoginFormComponent implements OnInit {
  loginForm = this.fb.group({
    username: [""],
    password: [""],
  });

  constructor(
    private fb: FormBuilder,
    private authenticationService: AuthenticationService,
    private tokenStorage: TokenStorageService,
    private router: Router,
    private dialog: MatDialog
  ) {}

  ngOnInit() {}

  login() {
    const credentials = {
      email: this.loginForm.get("username").value,
      password: this.loginForm.get("password").value,
    };
    this.authenticationService.login(credentials).subscribe(
      (data) => {
        this.tokenStorage.saveUser(data);
        this.tokenStorage.saveJwtToken(data.jwtToken);
        this.router.navigate(["/home/main"]);
      },
      (err) => {
        this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: { data: "Niepoprawny email lub hasło." },
        });
      }
    );
  }
}
