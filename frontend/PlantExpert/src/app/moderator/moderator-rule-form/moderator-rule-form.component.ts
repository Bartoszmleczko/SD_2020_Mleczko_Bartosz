import { map } from "rxjs/operators";
import {
  FormArray,
  FormControl,
  FormGroup,
  ValidatorFn,
  Validators,
} from "@angular/forms";
import { FormBuilder } from "@angular/forms";
import { Component, Inject, OnDestroy, OnInit } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { RulePart, Templates } from "src/app/models/models";

const minSelectedBoxes: ValidatorFn = (fg: FormGroup) => {
  const symptoms = fg.get("symptoms") as FormArray;

  const riskFactors = fg.get("riskFactors") as FormArray;

  const symptomsSelected = symptoms.controls
    .map((control) => control.value)
    .reduce((prev, next) => (next ? prev + next : prev), 0);
  const riskFactorsSelected = riskFactors.controls
    .map((control) => control.value)
    .reduce((prev, next) => (next ? prev + next : prev), 0);

  return symptomsSelected + riskFactorsSelected >= 1
    ? null
    : { symptoms: false };
};

@Component({
  selector: "app-moderator-rule-form",
  templateUrl: "./moderator-rule-form.component.html",
  styleUrls: ["./moderator-rule-form.component.css"],
})
export class ModeratorRuleFormComponent implements OnInit, OnDestroy {
  templates: Templates = null;
  rule: RulePart = null;

  duplicateMessage: string = "";

  symptomIndexes: number[] = [];
  riskFactorIndexes: number[] = [];

  ruleForm = this.fb.group(
    {
      symptoms: this.fb.array([]),
      riskFactors: this.fb.array([]),
      diagnoseControls: ["", Validators.compose([Validators.required])],
    },
    { validators: minSelectedBoxes }
  );

  constructor(
    public dialogRef: MatDialogRef<ModeratorRuleFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.templates = this.data.data;
    this.buildSymptoms();
    this.buildRiskFactors();
  }

  ngOnDestroy() {
    this.clearData();
  }

  buildSymptoms() {
    this.templates.symptoms.forEach((s) => {
      this.symptoms.push(new FormControl(false));
    });
  }

  buildRiskFactors() {
    this.templates.riskFactors.forEach((s) => {
      this.riskFactors.push(new FormControl(false));
    });
  }

  get symptoms() {
    return this.ruleForm.get("symptoms") as FormArray;
  }

  get riskFactors() {
    return this.ruleForm.get("riskFactors") as FormArray;
  }

  get f() {
    return this.ruleForm.controls;
  }

  get templateControls() {
    return this.ruleForm.get("templateControls") as FormArray;
  }

  getCheckedSymptoms() {
    this.symptoms.controls.forEach((control, i) => {
      if (control.value && !this.symptomIndexes.includes(i)) {
        this.symptomIndexes.push(i);
      }
    });
  }

  getCheckedRiskFactors() {
    this.riskFactors.controls.forEach((control, i) => {
      if (control.value && !this.riskFactorIndexes.includes(i)) {
        this.riskFactorIndexes.push(i);
      }
    });
  }

  compareArrays(arr1: number[], arr2: number[]) {
    if (arr1.length !== arr2.length) return false;
    for (let i = 0, len = arr1.length; i < len; i++) {
      if (arr1[i] !== arr2[i]) {
        return false;
      }
    }
    return true;
  }

  checkIfDuplicate(): boolean {
    for (let i = 0; i < this.templates.rules.length; i++) {
      if (
        this.compareArrays(
          this.riskFactorIndexes,
          this.templates.rules[i].riskFactorIndexes
        ) &&
        this.compareArrays(
          this.symptomIndexes,
          this.templates.rules[i].symptomIndexes
        )
      ) {
        return true;
      }
    }
    return false;
  }

  prepareRule(): RulePart {
    this.getCheckedSymptoms();
    this.getCheckedRiskFactors();
    const diagnoseId = this.ruleForm.get("diagnoseControls").value;
    const result: RulePart = {
      symptomIndexes: this.symptomIndexes,
      riskFactorIndexes: this.riskFactorIndexes,
      diagnoseId: diagnoseId,
    };
    return result;
  }

  save(data: RulePart) {
    this.dialogRef.close(data);
  }

  addRule() {
    let data = this.prepareRule();
    if (!this.checkIfDuplicate()) {
      this.save(data);
      this.clearData();
      this.duplicateMessage = "";
    } else {
      this.duplicateMessage =
        "Identyczna lub podobna (z innym typem diagnozy) reguła już istnieje. Popraw dane";
      data = null;
      this.clearData();
    }
  }

  clearData() {
    this.ruleForm.reset();
    this.symptomIndexes = [];
    this.riskFactorIndexes = [];
  }
}
