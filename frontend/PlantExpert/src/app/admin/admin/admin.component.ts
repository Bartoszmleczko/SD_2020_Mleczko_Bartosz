import { FormControl, FormGroup } from "@angular/forms";
import { FormBuilder } from "@angular/forms";
import { Component, OnInit } from "@angular/core";
import { AdminService } from "../admin.service";
import { TemporaryDiseaseDto } from "src/app/models/models";

@Component({
  selector: "app-admin",
  templateUrl: "./admin.component.html",
  styleUrls: ["./admin.component.css"],
})
export class AdminComponent implements OnInit {
  temporaryDiseases: TemporaryDiseaseDto[] = [];
  isDataAvailable = false;
  factorTypes: [] = [];

  constructor(private adminservice: AdminService, private fb: FormBuilder) {}

  ngOnInit() {
    this.getDiseaseTemplates();
    this.getFactorTypes();
  }

  public getDiseaseTemplates() {
    this.temporaryDiseases = [];
    this.adminservice.getDiseaseTemplates().subscribe((data: []) => {
      this.temporaryDiseases = data;
      if (this.temporaryDiseases.length > 0) {
        this.isDataAvailable = true;
      }
    });
  }

  public getFactorTypes() {
    this.adminservice.getFactorTypes().subscribe((data: []) => {
      this.factorTypes = data;
    });
  }

  public changeFactorType(factorType: string, index: number, index2: number) {
    this.temporaryDiseases[index].riskFactors[index2].factorType = factorType;
    console.log(this.temporaryDiseases[index]);
  }

  public acceptDisease(index: number) {
    const disease = this.temporaryDiseases[index];
    this.temporaryDiseases.slice(index, 1);
    this.adminservice.acceptDisease(disease).subscribe();
  }

  public deleteDisease(index: number) {
    this.temporaryDiseases.slice(index, 1);
    this.adminservice
      .deleteDisease(this.temporaryDiseases[index].id)
      .subscribe();
  }
}
