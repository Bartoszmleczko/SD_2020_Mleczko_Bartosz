import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

const API_URL = "http://localhost:8886/";

@Injectable({
  providedIn: "root",
})
export class EncyclopedyService {
  constructor(private httpClient: HttpClient) {}

  getData(page: number, size: number) {
    this.httpClient.get(API_URL + "diseases?page=" + page + ",size=" + size);
  }
}
