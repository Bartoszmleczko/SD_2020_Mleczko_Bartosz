import { MatDialog } from "@angular/material";
import { FileUploadService } from "./../../services/file-upload.service";
import { ModeratorService } from "./../moderator.service";
import { FormArray } from "@angular/forms";
import {
  NewTemporaryDiseaseDto,
  Plant,
  Templates,
} from "./../../models/models";
import { DiagnoseService } from "./../../diagnose/diagnose.service";
import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ModeratorRuleFormComponent } from "../moderator-rule-form/moderator-rule-form.component";

@Component({
  selector: "app-moderator-disease",
  templateUrl: "./moderator-disease.component.html",
  styleUrls: ["./moderator-disease.component.css"],
})
export class ModeratorDiseaseComponent implements OnInit {
  errors = [];
  filename: string = "";
  selectedFiles: FileList;
  newDiseaseForm = this.fb.group({
    diseaseName: [
      "",
      Validators.compose([Validators.required, Validators.minLength(5)]),
    ],
    diseaseDescription: [
      "",
      Validators.compose([Validators.required, Validators.minLength(50)]),
    ],
    precautionDiagnose: ["", Validators.minLength(20)],
    interventionDiagnose: [
      "",
      Validators.compose([Validators.required, Validators.minLength(20)]),
    ],
    file: ["", Validators.required],
    symptoms: this.fb.array(
      [this.newTemplate()],
      Validators.compose([Validators.required])
    ),
    riskFactors: this.fb.array(
      [this.newTemplate()],
      Validators.compose([Validators.required])
    ),
    rules: this.fb.array(
      [this.newTemplate()]
      // Validators.compose([Validators.required])
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
        Validators.compose([Validators.required, Validators.minLength(5)]),
      ],
    });
  }

  getPlants() {
    this.diagnoseService.getPlantTypes().subscribe((data: Plant[]) => {
      this.plants = data;
    });
  }

  setPlant(name) {
    if (name !== "none") {
      this.plantSelect = name;
    }
    console.log(this.plantSelect);
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

  // addRule() {
  //   this.rules.push(this.newTemplate());
  // }

  // removeRule(i: number) {
  //   this.rules.removeAt(i);
  // }

  getTemplates() {
    const templates: Templates = {
      riskFactors: this.riskFactors.controls.map((rf) => rf.value),
      symptoms: this.symptoms.controls.map((s) => s.value),
      precautionDiagnose: this.newDiseaseForm.get("precautionDiagnose").value,
      interventionDiagnose: this.newDiseaseForm.get("interventionDiagnose")
        .value,
    };
    return templates;
  }

  addRuleForm() {
    console.log(this.symptoms.status);
    this.dialog.open(ModeratorRuleFormComponent, {
      width: "60%",
      panelClass: "app-full-bleed-dialog",
      data: { data: this.getTemplates() },
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
      plantType: this.plantSelect,
      diseaseName: diseaseName,
      diseaseDescription: diseaseDescription,
      precautionDiagnose: precautionDiagnose,
      interventionDiagnose: interventionDiagnose,
      symptoms: syptomArray,
      riskFactors: riskFactorArray,
      rules: null,
      image: this.imageFileToUpload,
    };

    this.uploadService.postFile(dto).subscribe(
      (data) => {
        console.log(data);
      },
      (err) => {
        this.errors = err;
        console.log(this.errors);
      }
    );
  }
}
