import { Plant } from "./../models/models";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

const API_URL = "http://localhost:8886/";
@Injectable({
  providedIn: "root",
})
export class ModeratorService {
  constructor(private httpClient: HttpClient) {}

  public sendNewPlantRequest(plant: Plant) {
    return this.httpClient.post(API_URL + "plantRequests", plant);
  }
}
