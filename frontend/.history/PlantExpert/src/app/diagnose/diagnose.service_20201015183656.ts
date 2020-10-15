import { map } from "rxjs/operators";
import { DiagnoseForm, PlantSicknessRequest } from "./../models/models";
import { Observable } from "rxjs";
import { TokenStorageService } from "./../services/token-storage.service";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

const API_URL = "http://localhost:8886/";
@Injectable({
  providedIn: "root",
})
export class DiagnoseService {
  constructor(
    private httpClient: HttpClient,
    tokenStorage: TokenStorageService
  ) {}

  public getDiagnoseForm(plant: string): Observable<DiagnoseForm> {
    return this.httpClient.get<DiagnoseForm>(
      API_URL + "factors?plantType=" + plant
    );
  }

  public sendFormForEvaluation(request: PlantSicknessRequest) {
    return this.httpClient.post(API_URL + "factors", request);
  }

  public getImage() {
    return this.httpClient.get(API_URL + "images");
  }
}
