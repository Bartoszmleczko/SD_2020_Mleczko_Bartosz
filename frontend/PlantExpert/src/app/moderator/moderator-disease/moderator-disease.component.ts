import { MatDialog } from "@angular/material";
import { FileUploadService } from "./../../services/file-upload.service";
import { ModeratorService } from "./../moderator.service";
import { FormArray } from "@angular/forms";
import {
  NewTemporaryDiseaseDto,
  Plant,
  RulePart,
  Templates,
} from "./../../models/models";
import { DiagnoseService } from "./../../diagnose/diagnose.service";
import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ModeratorRuleFormComponent } from "../moderator-rule-form/moderator-rule-form.component";
import { BackendMessageComponent } from "src/app/backend-message/backend-message/backend-message.component";

@Component({
  selector: "app-moderator-disease",
  templateUrl: "./moderator-disease.component.html",
  styleUrls: ["./moderator-disease.component.css"],
})
export class ModeratorDiseaseComponent implements OnInit {
  errors;
  filename: string = "";
  selectedFiles: FileList;
  rules: RulePart[] = [];
  templates: Templates;
  newDiseaseForm = this.fb.group({
    plant: [
      "",
      Validators.compose([Validators.required, Validators.minLength(1)]),
    ],

    diseaseName: [
      "",
      Validators.compose([Validators.required, Validators.minLength(5)]),
    ],
    diseaseDescription: [
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(50),
        Validators.maxLength(600),
      ]),
    ],
    precautionDiagnose: [
      "",
      Validators.compose([Validators.minLength(20), Validators.maxLength(200)]),
    ],
    interventionDiagnose: [
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(20),
        Validators.maxLength(200),
      ]),
    ],
    file: [""],
    symptoms: this.fb.array(
      [this.newTemplate()],
      Validators.compose([Validators.required, Validators.maxLength(10)])
    ),
    riskFactors: this.fb.array(
      [this.newTemplate()],
      Validators.compose([Validators.required, Validators.maxLength(10)])
    ),
  });
  imageFileToUpload: File = null;
  plants: Plant[] = [];
  plantSelect: string = null;

  constructor(
    private diagnoseService: DiagnoseService,
    private fb: FormBuilder,
    private uploadService: FileUploadService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.getPlants();
  }

  handleImageFileInput(event) {
    this.selectedFiles = event.target.files;
    this.imageFileToUpload = this.selectedFiles.item(0);
    this.filename = this.imageFileToUpload.name;
  }

  get symptoms(): FormArray {
    return this.newDiseaseForm.get("symptoms") as FormArray;
  }

  get riskFactors(): FormArray {
    return this.newDiseaseForm.get("riskFactors") as FormArray;
  }

  // get rules(): FormArray {
  //   return this.newDiseaseForm.get("rules") as FormArray;
  // }

  newTemplate(): FormGroup {
    return this.fb.group({
      templateName: [
        "",
        Validators.compose([
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(100),
        ]),
      ],
    });
  }

  getPlants() {
    this.diagnoseService.getPlantTypes().subscribe((data: Plant[]) => {
      this.plants = data;
    });
  }

  setPlant(name) {
    this.plantSelect = name;
  }

  addSymptom() {
    this.symptoms.push(this.newTemplate());
  }

  removeSymptom(i: number) {
    this.symptoms.removeAt(i);
  }

  addRiskFactor() {
    this.riskFactors.push(this.newTemplate());
  }

  removeRiskFactor(i: number) {
    this.riskFactors.removeAt(i);
  }

  removeRule(index: number) {
    this.rules.splice(index, 1);
  }

  // addRule() {
  //   this.rules.push(this.newTemplate());
  // }

  // removeRule(i: number) {
  //   this.rules.removeAt(i);
  // }

  getTemplates() {
    this.templates = {
      riskFactors: this.riskFactors.controls.map((rf) => rf.value),
      symptoms: this.symptoms.controls.map((s) => s.value),
      precautionDiagnose: this.newDiseaseForm.get("precautionDiagnose").value,
      interventionDiagnose: this.newDiseaseForm.get("interventionDiagnose")
        .value,
      rules: this.rules,
    };
    return this.templates;
  }

  addRuleForm() {
    const dialogRef = this.dialog.open(ModeratorRuleFormComponent, {
      width: "60%",
      panelClass: "app-full-bleed-dialog",
      data: { data: this.getTemplates() },
    });
    dialogRef.afterClosed().subscribe((data: RulePart) => {
      if (data) {
        this.rules.push(data);
      }
    });
  }

  get f() {
    return this.newDiseaseForm.controls;
  }

  submitDisease() {
    const diseaseName = this.newDiseaseForm.get("diseaseName").value;
    const diseaseDescription = this.newDiseaseForm.get("diseaseDescription")
      .value;
    const precautionDiagnose = this.newDiseaseForm.get("precautionDiagnose")
      .value;
    const interventionDiagnose = this.newDiseaseForm.get("interventionDiagnose")
      .value;
    const syptomArray = this.symptoms.controls.map((s) => s.value);
    const riskFactorArray = this.riskFactors.controls.map((rf) => rf.value);
    // const ruleArray = this.rules.controls.map((rule) => rule.value);

    const dto: NewTemporaryDiseaseDto = {
      plantType: this.newDiseaseForm.get("plant").value,
      diseaseName: diseaseName,
      diseaseDescription: diseaseDescription,
      precautionDiagnose: precautionDiagnose,
      interventionDiagnose: interventionDiagnose,
      symptoms: syptomArray,
      riskFactors: riskFactorArray,
      rules: this.rules,
      image: this.imageFileToUpload,
    };

    this.uploadService.postFile(dto).subscribe(
      (data) => {
        this.newDiseaseForm.reset();
        const dialogRef = this.dialog.open(BackendMessageComponent, {
          width: "30%",
          panelClass: "app-full-bleed-dialog",
          data: {
            data:
              "Poprawnie przesłano na serwer. Po zatwierdzeniu przez administrację choroba pojawi się na stronie.",
          },
        });
      },
      (err) => {
        this.errors = err;
        const dialogRef = this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: { data: err.error },
        });
      }
    );
  }
}
