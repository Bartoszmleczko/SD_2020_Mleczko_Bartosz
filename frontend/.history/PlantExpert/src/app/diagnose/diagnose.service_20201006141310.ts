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

  public getdiagnoseForm(plant: string): Observable<any> {
    return this.httpClient.get(API_URL + 'factors?plantType=' + plant);
  }



}
