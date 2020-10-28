import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule, Routes } from "@angular/router";
import { HttpClientModule } from "@angular/common/http";
import { MatPaginatorModule } from "@angular/material/paginator";
import { AppComponent } from "./app.component";
import { LoginComponent } from "./login/login.component";
import { HeaderComponent } from "./header/header.component";
import { RegisterComponent } from "./register/register.component";
import { HomeComponent } from "./home/home.component";
import { AuthenticationService } from "./services/authentication.service";
import { AuthGuardService } from "./services/auth-guard.service";
import { TokenStorageService } from "./services/token-storage.service";
import { RegisterService } from "./services/register.service";
import { DiagnoseWrapperComponent } from "./diagnose/diagnose-wrapper/diagnose-wrapper.component";
import { MatSelectModule } from "@angular/material/select";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { DiagnoseService } from "./diagnose/diagnose.service";
import { MatStepperModule } from "@angular/material/stepper";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { DiagnosePlaceholderComponent } from "./diagnose/diagnose-placeholder/diagnose-placeholder.component";
import { MatDialogModule } from "@angular/material/dialog";
import { EncyclopedyComponent } from "./encyclopedy/encyclopedy/encyclopedy.component";
import { EncyclopedyService } from "./encyclopedy/encyclopedy.service";
import { ContactComponent } from "./contact/contact/contact.component";
import { ModeratorComponent } from "./moderator/moderator/moderator.component";
import { ContactService } from "./contact/contact.service";
import { ModeratorService } from "./moderator/moderator.service";

const routes: Routes = [
  {
    path: "",
    component: AppComponent,
    canActivate: [AuthGuardService],
    data: { roles: "USER" },
  },
  { path: "login", component: LoginComponent },
  { path: "register", component: RegisterComponent },
  {
    path: "home",
    component: HomeComponent,
    canActivate: [AuthGuardService],
    data: { roles: "USER" },
    children: [
      {
        path: "diagnose",
        component: DiagnoseWrapperComponent,
        canActivate: [AuthGuardService],
        data: { roles: "USER" },
      },
      {
        path: "encyclopedy",
        component: EncyclopedyComponent,
        canActivate: [AuthGuardService],
        data: { roles: "USER" },
      },
      {
        path: "contact",
        component: ContactComponent,
        canActivate: [AuthGuardService],
        data: { roles: "USER" },
      },
      {
        path: "moderator",
        component: ModeratorComponent,
        canActivate: [AuthGuardService],
        data: { roles: "USER" },
      },
    ],
  },
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    RegisterComponent,
    HomeComponent,
    DiagnoseWrapperComponent,
    DiagnosePlaceholderComponent,
    EncyclopedyComponent,
    ContactComponent,
    ModeratorComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatSelectModule,
    BrowserAnimationsModule,
    MatStepperModule,
    MatCheckboxModule,
    MatDialogModule,
    MatPaginatorModule,
  ],
  providers: [
    AuthenticationService,
    TokenStorageService,
    RegisterService,
    DiagnoseService,
    EncyclopedyService,
    ContactService,
    ModeratorService,
  ],
  bootstrap: [AppComponent],
  entryComponents: [DiagnosePlaceholderComponent],
})
export class AppModule {}
