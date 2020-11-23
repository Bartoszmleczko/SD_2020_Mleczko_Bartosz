import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { AuthenticationService } from "src/app/services/authentication.service";

@Component({
  selector: "app-register-activation",
  templateUrl: "./register-activation.component.html",
  styleUrls: ["./register-activation.component.css"],
})
export class RegisterActivationComponent implements OnInit {
  token: string = null;
  message: string = null;

  constructor(
    private authenicationService: AuthenticationService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.token = this.route.snapshot.paramMap.get("token");
    this.authenicationService
      .activateAccount(this.token)
      .subscribe((data: any) => {
        this.message = data.message;
      });
  }
}
