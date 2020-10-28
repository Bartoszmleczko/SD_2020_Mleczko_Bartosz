import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

const API_URL = "http://localhost:8886/";

@Injectable({
  providedIn: "root",
})
export class EncyclopedyService {
  constructor(private httpClient: HttpClient) {}

  getData(page: number, size: number) {
    return this.httpClient.get(
      API_URL + "diseases?page=" + page + "&size=" + size
    );
  }

  public getImages(names: string[]) {
    let params = new HttpParams();

    params = params.append("names", names.join(","));

    return this.httpClient.get(API_URL + "diseases/images", {
      responseType: "blob",
      params: params,
    });
  }
}
