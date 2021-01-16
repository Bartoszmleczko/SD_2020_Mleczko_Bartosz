import { Plant, UserDto } from "./../models/models";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { TemporaryDiseaseDto, RefuseFormDto } from "../models/models";

const API_URL = "http://localhost:8886/";

@Injectable({
  providedIn: "root",
})
export class AdminService {
  constructor(private httpClient: HttpClient) {}

  public getDiseaseTemplates() {
    return this.httpClient.get(API_URL + "tempDiseases");
  }

  public getFactorTypes() {
    return this.httpClient.get(API_URL + "factorTypes");
  }

  public acceptDisease(disease: TemporaryDiseaseDto) {
    return this.httpClient.post(API_URL + "diseases/accept", disease);
  }

  public deleteDisease(index: number) {
    return this.httpClient.delete(API_URL + "diseases/discard?id=" + index);
  }

  public getPlantRequests() {
    return this.httpClient.get(API_URL + "plantRequests");
  }

  public acceptPlantRequest(plant: Plant) {
    return this.httpClient.put(API_URL + "plantRequests", plant);
  }

  public declinePlantRequest(id: number) {
    return this.httpClient.delete(API_URL + "plantRequests?id=" + id);
  }

  public findAllByUsername(username: string) {
    if (username != null) {
      return this.httpClient.get(API_URL + "users?username=" + username);
    } else {
      return this.httpClient.get(API_URL + "users");
    }
  }

  public grantModeratorRole(dto: UserDto) {
    return this.httpClient.put(API_URL + "users/grantMod", dto);
  }

  public takeModeratorRole(dto: UserDto) {
    return this.httpClient.put(API_URL + "users/declineMod", dto);
  }

  public refuseDisease(data: RefuseFormDto) {
    return this.httpClient.post(API_URL + "tempDiseases/refuse", data);
  }

  public banUser(user: UserDto) {
    return this.httpClient.put(API_URL + "users/ban", user);
  }

  public unbanUser(user: UserDto) {
    return this.httpClient.put(API_URL + "users/unban", user);
  }
}
