import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

const API_URL = "http://localhost:8886/";

@Injectable({
  providedIn: "root",
})
export class EncyclopedyService {
  constructor(private httpClient: HttpClient) {}

  getData(page: number, size: number) {
    this.httpClient.get(API_URL + "diseases?page=" + page + ",size=" + size);
  }

  public getImages(names: string[]) {
    this.httpClient.get(API_URL + "diseases/images" + JSON.stringify(names), {
      responseType: "blob",
    });
  }
}
