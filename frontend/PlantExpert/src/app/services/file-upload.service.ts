import { map } from "rxjs/operators";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { NewTemporaryDiseaseDto } from "../models/models";

const URL = "http://localhost:8886/diseases";

@Injectable({
  providedIn: "root",
})
export class FileUploadService {
  constructor(private httpClient: HttpClient) {}

  postFile(newDisease: NewTemporaryDiseaseDto): Observable<any> {
    const formData: FormData = new FormData();
    formData.append("plantType", newDisease.plantType);
    formData.append("diseaseName", newDisease.diseaseName);
    formData.append("diseaseDescription", newDisease.diseaseDescription);
    formData.append("precautionDiagnose", newDisease.precautionDiagnose);
    formData.append("interventionDiagnose", newDisease.interventionDiagnose);
    formData.append("symptoms", JSON.stringify(newDisease.symptoms));
    formData.append("riskFactors", JSON.stringify(newDisease.riskFactors));
    formData.append("rules", JSON.stringify(newDisease.rules));
    formData.append("image", newDisease.image);
    return this.httpClient.post(URL, formData);
  }
}
