import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

const API_URL = "http://localhost:8886/";
@Injectable({
  providedIn: "root",
})
export class AccountService {
  constructor(private httpClient: HttpClient) {}

  getDetails() {
    return this.httpClient.get(API_URL + "users/details");
  }

  changeDetails(detailsForm) {
    return this.httpClient.put(API_URL + "users/details", detailsForm);
  }

  changePassword(data) {
    return this.httpClient.put(API_URL + "users/password", data);
  }
}
