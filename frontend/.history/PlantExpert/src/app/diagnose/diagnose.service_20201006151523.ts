import { map } from 'rxjs/operators';
import { DiagnoseForm } from './../models/models';
import { Observable } from 'rxjs';
import { TokenStorageService } from './../services/token-storage.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

const API_URL = 'http://localhost:8886/';
@Injectable({
  providedIn: 'root'
})
export class DiagnoseService {

  constructor(private httpClient: HttpClient, tokenStorage: TokenStorageService) { }

  public getDiagnoseForm(plant: string) {
    return this.httpClient.get<DiagnoseForm>(API_URL + 'factors?plantType=' + plant);
  }



}
