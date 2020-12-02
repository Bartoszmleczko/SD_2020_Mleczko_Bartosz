import { NewTemporaryDiseaseDto, Plant } from "./../models/models";
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

  public getUsersAllRequests() {
    return this.httpClient.get(API_URL + "tempDiseases/users");
  }

  public getUsersRefusedRequests() {
    return this.httpClient.get(API_URL + "tempDiseases/users/refused");
  }

  public updateDisease(data: NewTemporaryDiseaseDto, index) {
    const newData = {
      id: index,
      diseaseName: data.diseaseName,
      diseaseDescription: data.diseaseDescription,
      precautionDiagnose: data.precautionDiagnose,
      interventionDiagnose: data.interventionDiagnose,
      riskFactors: JSON.stringify(data.riskFactors),
      symptoms: JSON.stringify(data.symptoms),
      rules: JSON.stringify(data.rules),
      properties: data.properties,
    };
    console.log(newData);
    return this.httpClient.put(API_URL + "tempDiseases/update", newData);
  }
}
