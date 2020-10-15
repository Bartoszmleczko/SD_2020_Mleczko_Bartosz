import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import {HttpClientModule} from '@angular/common/http';


import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HeaderComponent } from './header/header.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { AuthenticationService } from './services/authentication.service';
import { AuthGuardService } from './services/auth-guard.service';
import { TokenStorageService } from './services/token-storage.service';
import { RegisterService } from './services/register.service';
import { DiagnoseFormComponent } from './diagnose/diagnose-form/diagnose-form.component';
import { DiagnoseWrapperComponent } from './diagnose/diagnose-wrapper/diagnose-wrapper.component';
import {MatSelectModule} from '@angular/material/select';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

const routes: Routes = [
  { path: '', component: AppComponent, canActivate: [AuthGuardService], data: {roles: 'USER'} },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent},
  { path: 'home', component: HomeComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}, children: [
    { path: 'diagnose', component: DiagnoseWrapperComponent, canActivate: [AuthGuardService], data: {roles: 'USER'}}
  ]}
  
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    RegisterComponent,
    HomeComponent,
    DiagnoseFormComponent,
    DiagnoseWrapperComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatSelectModule,
    BrowserAnimationsModule
  ],
  providers: [AuthenticationService, TokenStorageService, RegisterService],
  bootstrap: [AppComponent]
})
export class AppModule { }
