import { BackendMessageComponent } from "./../../backend-message/backend-message/backend-message.component";
import { DiagnosePlaceholderComponent } from "./../diagnose-placeholder/diagnose-placeholder.component";
import { map, switchMap } from "rxjs/operators";
import {
  Plant,
  DiagnoseForm,
  RiskFactor,
  RequestSlotDto,
  PlantSicknessRequest,
  DiseaseDto,
  DiagnoseDto,
} from "./../../models/models";
import { Component, OnInit, Output, ViewChild } from "@angular/core";
import { DiagnoseService } from "../diagnose.service";
import { Observable } from "rxjs";
import { FormGroup, FormBuilder, FormControl, FormArray } from "@angular/forms";
import { MatDialog, MatDialogConfig } from "@angular/material";
import { DomSanitizer } from "@angular/platform-browser";

@Component({
  selector: "app-diagnose-wrapper",
  templateUrl: "./diagnose-wrapper.component.html",
  styleUrls: ["./diagnose-wrapper.component.css"],
})
export class DiagnoseWrapperComponent implements OnInit {
  selected = "CEREALS";
  formData: DiagnoseForm;

  symptomGroup: FormGroup;
  symptomArr: FormArray;
  selectedSymptomsValues: RequestSlotDto[] = [];
  symptomsCompleted: boolean = false;
  images: any[] = [];

  riskFactorGroup: FormGroup;
  factorArr: FormArray;
  flatRiskFactorArr: RiskFactor[];
  selectedRiskFactorValues: RequestSlotDto[] = [];

  diagnose: DiagnoseDto;

  plants: Plant[] = [];
  plant;

  constructor(
    private diagnoseService: DiagnoseService,
    private formBuilder: FormBuilder,
    public dialog: MatDialog,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    this.getPlantTypes();
  }

  getPlantTypes() {
    this.diagnoseService.getPlantTypes().subscribe((data: Plant[]) => {
      this.plants = data;
    });
  }

  buildSymptoms() {
    const arr = this.formData.symptoms.map((symptom) => {
      symptom.checked = false;
      return this.formBuilder.control(false);
    });
    return this.formBuilder.array(arr);
  }

  buildRiskFactors() {
    this.flatRiskFactorArr = [];
    const factors: Map<string, RiskFactor[]> = this.formData.riskFactors;

    let riskFactorControlArray: FormControl[] = [];

    for (let wrapper of Object.values(factors)) {
      for (let rf of wrapper) {
        riskFactorControlArray.push(this.formBuilder.control(false));
        this.flatRiskFactorArr.push(rf);
      }
    }

    return this.formBuilder.array(riskFactorControlArray);
  }

  getDiagnoseFormData(templateName: string) {
    if (templateName !== "none") {
      this.plant = templateName;
      this.diagnoseService
        .getDiagnoseForm(templateName)
        .subscribe((data: DiagnoseForm) => {
          this.formData = data;
          this.symptomArr = this.buildSymptoms();
          this.symptomGroup = this.formBuilder.group(this.symptomArr);
          this.factorArr = this.buildRiskFactors();
          this.riskFactorGroup = this.formBuilder.group(this.factorArr);
        });
    } else {
      this.closeForm();
    }
  }

  submitSymptoms() {
    this.selectedSymptomsValues = [];
    this.symptomArr.controls.forEach((control, i) => {
      if (control.value) {
        let tempDto: RequestSlotDto = {
          name: this.formData.symptoms[i].name,
          slotName: this.formData.symptoms[i].slotName,
        };
        this.selectedSymptomsValues.push(tempDto);
      }
    });
    this.symptomsCompleted = true;
  }

  submitRiskFactors() {
    this.selectedRiskFactorValues = [];

    this.factorArr.controls.forEach((control, i) => {
      if (control.value) {
        let tempDto: RequestSlotDto = {
          name: this.flatRiskFactorArr[i].name,
          slotName: this.flatRiskFactorArr[i].slotName,
        };
        this.selectedRiskFactorValues.push(tempDto);
      }
    });

    this.sendFormToEvaluation();
  }

  sendFormToEvaluation() {
    const sicknessRequest: PlantSicknessRequest = {
      riskFactors: this.selectedRiskFactorValues,
      symptoms: this.selectedSymptomsValues,
      plant: this.plant,
    };

    this.diagnoseService
      .sendFormForEvaluation(sicknessRequest)
      .subscribe((data: DiagnoseDto) => {
        console.log(data);
        this.diagnose = data;
        this.diagnose.diseases.forEach((diagnose: DiseaseDto, index) => {
          this.diagnoseService
            .getImage(diagnose.diseaseName)
            .subscribe((data) => {
              console.log(data);
              let imageBytes = null;
              let image = null;
              const reader = new FileReader();
              reader.readAsDataURL(data);
              reader.onload = (e) => (
                (imageBytes = reader.result),
                (this.images[index] = this.sanitizer.bypassSecurityTrustUrl(
                  imageBytes
                ))
              );
              console.log(this.images);
            });
        });

        this.symptomArr.reset();
        this.factorArr.reset();
      });
  }

  resetBoxes() {
    this.symptomArr.reset();
    this.factorArr.reset();
    this.diagnose = null;
  }

  closeForm() {
    this.formData = null;
    this.resetBoxes();
  }

  openDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = false;

    dialogConfig.panelClass = "app-full-bleed-dialog";

    this.dialog.open(DiagnosePlaceholderComponent, dialogConfig);
  }

  saveDiagnose() {
    const newDiagnose: DiagnoseDto = {
      id: null,
      note: null,
      diseases: this.diagnose.diseases,
      creationTime: null,
      riskFactors: this.diagnose.riskFactors,
      symptoms: this.diagnose.symptoms,
    };
    this.diagnoseService.saveDiagnose(newDiagnose).subscribe(
      (data) => {
        this.resetBoxes();
        this.closeForm();
        this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: { data: "Diagnoza została pomyślnie zapisana." },
        });
      },
      (err) => {
        this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: { data: err.error },
        });
      }
    );
  }
}
