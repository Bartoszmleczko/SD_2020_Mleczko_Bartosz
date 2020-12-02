import { DomSanitizer } from "@angular/platform-browser";
import { DiagnoseService } from "src/app/diagnose/diagnose.service";
import { BackendMessageComponent } from "./../../backend-message/backend-message/backend-message.component";
import { AdminDiseaseRefuseComponent } from "./../admin-disease-refuse/admin-disease-refuse.component";
import { MatDialog } from "@angular/material";
import { AdminService } from "./../admin.service";
import { RefuseFormDto, TemporaryDiseaseDto } from "./../../models/models";
import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";

@Component({
  selector: "app-admin-disease",
  templateUrl: "./admin-disease.component.html",
  styleUrls: ["./admin-disease.component.css"],
})
export class AdminDiseaseComponent implements OnInit {
  temporaryDiseases: TemporaryDiseaseDto[] = [];
  isDataAvailable = false;
  factorTypes: [] = [];
  refuseData: RefuseFormDto;

  constructor(
    private adminservice: AdminService,
    private fb: FormBuilder,
    private dialog: MatDialog,
    private diagnoseService: DiagnoseService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    this.getDiseaseTemplates();
    this.getFactorTypes();
  }

  public getDiseaseTemplates() {
    this.temporaryDiseases = [];
    this.adminservice
      .getDiseaseTemplates()
      .subscribe((data: TemporaryDiseaseDto[]) => {
        this.temporaryDiseases = data;
        this.temporaryDiseases.forEach((td) => {
          this.getImage(td);
        });

        if (this.temporaryDiseases.length > 0) {
          this.isDataAvailable = true;
        }
      });
  }

  getImage(disease: TemporaryDiseaseDto) {
    let imageBytes = null;
    let image = null;
    this.diagnoseService.getTempImage(disease.diseaseName).subscribe((data) => {
      const reader = new FileReader();
      reader.readAsDataURL(data);
      reader.onloadend = (e) => (
        (imageBytes = reader.result),
        (disease.url = this.sanitizer.bypassSecurityTrustUrl(imageBytes))
      );
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
    this.temporaryDiseases.splice(index, 1);
    this.adminservice.acceptDisease(disease).subscribe();
  }

  public deleteDisease(index: number) {
    this.temporaryDiseases.splice(index, 1);
    this.adminservice
      .deleteDisease(this.temporaryDiseases[index].id)
      .subscribe();
  }

  public refuseDisease(index) {
    const i = this.temporaryDiseases[index].id;
    const ref = this.dialog.open(AdminDiseaseRefuseComponent, {
      width: "25%",
      panelClass: "app-full-bleed-dialog",
    });

    ref.afterClosed().subscribe((data: RefuseFormDto) => {
      if (data) {
        console.log(data);
        this.refuseData = data;
        this.refuseData.id = i;
        this.adminservice.refuseDisease(data).subscribe(
          (dat) => {
            this.temporaryDiseases.splice(index, 1);
          },
          (err) => {}
        );
      }
    });
  }
}
