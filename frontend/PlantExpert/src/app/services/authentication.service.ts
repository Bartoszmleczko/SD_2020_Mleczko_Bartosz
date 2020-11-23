import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";

const AUTH_API = "http://localhost:8886/login";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
};

@Injectable({
  providedIn: "root",
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient) {}

  login(credentials): Observable<any> {
    return this.httpClient.post(AUTH_API, credentials, httpOptions);
  }

  activateAccount(token: string) {
    return this.httpClient.get("http://localhost:8886/activate/" + token);
  }
}
