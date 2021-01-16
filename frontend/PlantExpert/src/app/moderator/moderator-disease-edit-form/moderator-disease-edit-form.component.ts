import { ModeratorInstructionEditComponent } from "./../moderator-instruction-edit/moderator-instruction-edit.component";
import { ModeratorDiseaseInstructionComponent } from "./../moderator-disease-instruction/moderator-disease-instruction.component";
import { ModeratorService } from "./../moderator.service";
import { ModeratorRuleFormComponent } from "./../moderator-rule-form/moderator-rule-form.component";
import { MatDialog } from "@angular/material";
import { FormGroup } from "@angular/forms";
import { FormArray } from "@angular/forms";
import { FormBuilder } from "@angular/forms";
import { Validators } from "@angular/forms";
import {
  NewTemporaryDiseaseDto,
  RulePart,
  Templates,
} from "./../../models/models";
import { Component, EventEmitter, OnInit, Output } from "@angular/core";

@Component({
  selector: "app-moderator-disease-edit-form",
  templateUrl: "./moderator-disease-edit-form.component.html",
  styleUrls: ["./moderator-disease-edit-form.component.css"],
})
export class ModeratorDiseaseEditFormComponent implements OnInit {
  @Output() emitter: EventEmitter<NewTemporaryDiseaseDto> = new EventEmitter();

  disease: NewTemporaryDiseaseDto = null;

  rules: RulePart[] = [];
  templates: Templates;
  newDiseaseForm = this.fb.group({
    diseaseName: [
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(50),
      ]),
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
      Validators.compose([
        Validators.required,
        Validators.minLength(20),
        Validators.maxLength(200),
      ]),
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
      [],
      Validators.compose([Validators.required, Validators.maxLength(10)])
    ),
    riskFactors: this.fb.array(
      [],
      Validators.compose([Validators.required, Validators.maxLength(10)])
    ),
  });
  keepRules: boolean;

  constructor(
    private fb: FormBuilder,
    private dialog: MatDialog,
    private moderatorService: ModeratorService
  ) {}

  ngOnInit() {}

  changeDisease(data: NewTemporaryDiseaseDto) {
    this.newDiseaseForm.reset();
    this.clearFormArray(this.symptoms);
    this.clearFormArray(this.riskFactors);
    console.log(data);
    this.disease = data;
    this.setFormData();
  }

  setFormData() {
    this.newDiseaseForm.get("diseaseName").setValue(this.disease.diseaseName);
    this.newDiseaseForm
      .get("diseaseDescription")
      .setValue(this.disease["description"]);
    this.newDiseaseForm
      .get("precautionDiagnose")
      .setValue(this.disease.precautionDiagnose);
    this.newDiseaseForm
      .get("interventionDiagnose")
      .setValue(this.disease.interventionDiagnose);
    if (
      this.disease["properties"].riskFactors ||
      this.disease["properties"].symptoms ||
      this.disease["properties"].rules ||
      this.disease["properties"].name
    ) {
      this.keepRules = false;
      this.addSymptom();
      this.addRiskFactor();
      this.rules = [];
      this.templates = null;
    } else {
      this.keepRules = true;
      this.disease.symptoms.forEach((s) => {
        this.symptoms.push(this.newTemplateWithName(s["name"]));
      });
      this.disease.riskFactors.forEach((rf) => {
        this.riskFactors.push(this.newTemplateWithName(rf["name"]));
      });
      this.rules = this.disease.rules;
      this.getTemplates();
      console.log(this.getTemplates());
    }
  }

  clearFormArray = (formArray: FormArray) => {
    while (formArray.length !== 0) {
      formArray.removeAt(0);
    }
  };

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

  get symptoms(): FormArray {
    return this.newDiseaseForm.get("symptoms") as FormArray;
  }

  get riskFactors(): FormArray {
    return this.newDiseaseForm.get("riskFactors") as FormArray;
  }

  get f() {
    return this.newDiseaseForm.controls;
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

  newTemplateWithName(name): FormGroup {
    return this.fb.group({
      templateName: [
        name,
        Validators.compose([
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(100),
        ]),
      ],
    });
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

  openInstruction() {
    this.dialog.open(ModeratorInstructionEditComponent, {
      width: "50%",
      panelClass: "app-full-bleed-dialog",
    });
  }

  sendData() {
    const diseaseName = this.newDiseaseForm.get("diseaseName").value;
    const diseaseDescription = this.newDiseaseForm.get("diseaseDescription")
      .value;
    const precautionDiagnose = this.newDiseaseForm.get("precautionDiagnose")
      .value;
    const interventionDiagnose = this.newDiseaseForm.get("interventionDiagnose")
      .value;
    const syptomArray = this.symptoms.controls.map((s) => s.value);
    const riskFactorArray = this.riskFactors.controls.map((rf) => rf.value);

    const dto: NewTemporaryDiseaseDto = {
      plantType: null,
      diseaseName: diseaseName,
      diseaseDescription: diseaseDescription,
      precautionDiagnose: precautionDiagnose,
      interventionDiagnose: interventionDiagnose,
      symptoms: syptomArray,
      riskFactors: riskFactorArray,
      rules: this.rules,
      image: null,
      properties: this.disease.properties,
    };
    console.log(dto);
    this.moderatorService
      .updateDisease(dto, this.disease["id"])
      .subscribe((data) => {
        this.clearData();
        this.emitter.emit(this.disease);
      });
  }

  clearData() {
    this.disease = null;
    this.rules = [];
    this.templates = null;
    this.newDiseaseForm.reset();
    this.clearFormArray(this.symptoms);
    this.clearFormArray(this.riskFactors);
  }
}
