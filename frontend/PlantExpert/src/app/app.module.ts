import { AdminService } from "./admin/admin.service";
import { BrowserModule } from "@angular/platform-browser";
import { LOCALE_ID, NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { RouterModule, Routes } from "@angular/router";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
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
import { NewDiseasePlaceholderComponent } from "./moderator/new-disease-placeholder/new-disease-placeholder.component";
import { FileUploadService } from "./services/file-upload.service";
import { MatTooltipModule } from "@angular/material/tooltip";
import { AdminComponent } from "./admin/admin/admin.component";
import { ModeratorPlantComponent } from "./moderator/moderator-plant/moderator-plant.component";
import { ModeratorDiseaseComponent } from "./moderator/moderator-disease/moderator-disease.component";
import { HttpInterceptorService } from "./services/http-interceptor.service";
import { DiagnosesTableComponent } from "./main/diagnoses-table/diagnoses-table.component";
import { DiagnosesNoteModalComponent } from "./main/diagnoses-note-modal/diagnoses-note-modal.component";
import { DiseasesRankComponent } from "./main/diseases-rank/diseases-rank.component";
import { MainComponent } from "./main/main/main.component";
import { registerLocaleData } from "@angular/common";
import localePl from "@angular/common/locales/pl";
import localePlExtra from "@angular/common/locales/extra/pl";
import { DiseaseDetailComponent } from "./main/disease-detail/disease-detail.component";
import { AdminPlantComponent } from "./admin/admin-plant/admin-plant.component";
import { AdminDiseaseComponent } from "./admin/admin-disease/admin-disease.component";
import { AdminRolesComponent } from "./admin/admin-roles/admin-roles.component";
import { AccountComponent } from "./account/account/account.component";
import { AccountService } from "./account/account.service";
import { AccountPasswordComponent } from "./account/account-password/account-password.component";
import { AccountDetailsComponent } from "./account/account-details/account-details.component";
import { RegisterActivationComponent } from "./register/register-activation/register-activation.component";
import { ModeratorRuleFormComponent } from "./moderator/moderator-rule-form/moderator-rule-form.component";
import { BackendMessageComponent } from "./backend-message/backend-message/backend-message.component";
import { AccountDetailsCardComponent } from "./account/account-details-card/account-details-card.component";
import { NewMessagesComponent } from "./admin/admin-messages/new-messages/new-messages.component";
import { OldMessagesComponent } from "./admin/admin-messages/old-messages/old-messages.component";
import { AnswerFormComponent } from "./admin/admin-messages/answer-form/answer-form.component";
import { MessagesComponent } from "./admin/admin-messages/messages/messages.component";
import { MessageInfoComponent } from "./admin/admin-messages/message-info/message-info.component";
import { ContactFormComponent } from "./contact/contact-form/contact-form.component";
import { ContactOldMessagesComponent } from "./contact/contact-old-messages/contact-old-messages.component";
import { AdminDiseaseRefuseComponent } from "./admin/admin-disease-refuse/admin-disease-refuse.component";
import { ModeratorRefusedDiseasesComponent } from "./moderator/moderator-refused-diseases/moderator-refused-diseases.component";
import { ModeratorAllDiseasesComponent } from './moderator-all-diseases/moderator-all-diseases.component';
import { ModeratorDiseaseEditFormComponent } from './moderator/moderator-disease-edit-form/moderator-disease-edit-form.component';
registerLocaleData(localePl);
const routes: Routes = [
  {
    path: "",
    component: AppComponent,
    canActivate: [AuthGuardService],
    data: { roles: "ROLE_USER" },
  },
  { path: "login", component: LoginComponent },
  { path: "register", component: RegisterComponent },
  { path: "activate/:token", component: RegisterActivationComponent },
  {
    path: "home",
    component: HomeComponent,
    canActivate: [AuthGuardService],
    data: { roles: "ROLE_USER" },
    children: [
      {
        path: "main",
        component: MainComponent,
        canActivate: [AuthGuardService],
        data: { roles: "ROLE_USER" },
      },
      {
        path: "diagnose",
        component: DiagnoseWrapperComponent,
        canActivate: [AuthGuardService],
        data: { roles: "ROLE_USER" },
      },
      {
        path: "encyclopedy",
        component: EncyclopedyComponent,
        canActivate: [AuthGuardService],
        data: { roles: "ROLE_USER" },
      },
      {
        path: "contact",
        component: ContactComponent,
        canActivate: [AuthGuardService],
        data: { roles: "ROLE_USER" },
      },
      {
        path: "account",
        component: AccountComponent,
        canActivate: [AuthGuardService],
        data: { roles: "ROLE_USER" },
      },
      {
        path: "moderator",
        component: ModeratorComponent,
        canActivate: [AuthGuardService],
        data: { roles: "ROLE_MODERATOR" },
        children: [
          {
            path: "plant",
            component: ModeratorPlantComponent,
            canActivate: [AuthGuardService],
            data: { roles: "ROLE_MODERATOR" },
          },
          {
            path: "disease",
            component: ModeratorDiseaseComponent,
            canActivate: [AuthGuardService],
            data: { roles: "ROLE_MODERATOR" },
          },
          {
            path: "refused",
            component: ModeratorRefusedDiseasesComponent,
            canActivate: [AuthGuardService],
            data: { roles: "ROLE_MODERATOR" },
          },
        ],
      },
      {
        path: "admin",
        component: AdminComponent,
        canActivate: [AuthGuardService],
        data: { roles: "ROLE_ADMIN" },
        children: [
          {
            path: "plant",
            component: AdminPlantComponent,
            canActivate: [AuthGuardService],
            data: { roles: "ROLE_ADMIN" },
          },
          {
            path: "disease",
            component: AdminDiseaseComponent,
            canActivate: [AuthGuardService],
            data: { roles: "ROLE_ADMIN" },
          },
          {
            path: "roles",
            component: AdminRolesComponent,
            canActivate: [AuthGuardService],
            data: { roles: "ROLE_ADMIN" },
          },
          {
            path: "messages",
            component: MessagesComponent,
            canActivate: [AuthGuardService],
            data: { roles: "ROLE_ADMIN" },
          },
        ],
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
    NewDiseasePlaceholderComponent,
    AdminComponent,
    ModeratorPlantComponent,
    ModeratorDiseaseComponent,
    DiagnosesTableComponent,
    DiagnosesNoteModalComponent,
    DiseasesRankComponent,
    MainComponent,
    DiseaseDetailComponent,
    AdminPlantComponent,
    AdminDiseaseComponent,
    AdminRolesComponent,
    AccountComponent,
    AccountPasswordComponent,
    AccountDetailsComponent,
    RegisterActivationComponent,
    ModeratorRuleFormComponent,
    BackendMessageComponent,
    AccountDetailsCardComponent,
    NewMessagesComponent,
    OldMessagesComponent,
    AnswerFormComponent,
    MessagesComponent,
    MessageInfoComponent,
    ContactFormComponent,
    ContactOldMessagesComponent,
    AdminDiseaseRefuseComponent,
    ModeratorRefusedDiseasesComponent,
    ModeratorAllDiseasesComponent,
    ModeratorDiseaseEditFormComponent,
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
    MatTooltipModule,
  ],
  providers: [
    AuthenticationService,
    TokenStorageService,
    RegisterService,
    DiagnoseService,
    EncyclopedyService,
    ContactService,
    ModeratorService,
    FileUploadService,
    AdminService,
    AccountService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true,
    },
    { provide: LOCALE_ID, useValue: "pl" },
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    DiagnosePlaceholderComponent,
    DiagnosesNoteModalComponent,
    DiseaseDetailComponent,
    ModeratorRuleFormComponent,
    BackendMessageComponent,
    AnswerFormComponent,
    MessageInfoComponent,
    AdminDiseaseRefuseComponent,
  ],
})
export class AppModule {}
