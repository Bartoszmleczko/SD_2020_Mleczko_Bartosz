import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { AuthenticationService } from "../services/authentication.service";
import { TokenStorageService } from "../services/token-storage.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent implements OnInit {
  loginForm = this.fb.group({
    username: [""],
    password: [""],
  });

  constructor(
    private fb: FormBuilder,
    private authenticationService: AuthenticationService,
    private tokenStorage: TokenStorageService,
    private router: Router
  ) {}

  ngOnInit() {}

  login() {
    const credentials = {
      email: this.loginForm.get("username").value,
      password: this.loginForm.get("password").value,
    };
    this.authenticationService.login(credentials).subscribe((data) => {
      this.tokenStorage.saveUser(data);
      this.tokenStorage.saveJwtToken(data.jwtToken);
      this.router.navigate(["/home/main"]);
    });
  }
}
