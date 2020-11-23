import {
  FormArray,
  FormControl,
  FormGroup,
  ValidatorFn,
  Validators,
} from "@angular/forms";
import { FormBuilder } from "@angular/forms";
import { Component, Inject, OnInit } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { RulePart, Templates } from "src/app/models/models";

@Component({
  selector: "app-moderator-rule-form",
  templateUrl: "./moderator-rule-form.component.html",
  styleUrls: ["./moderator-rule-form.component.css"],
})
export class ModeratorRuleFormComponent implements OnInit {
  templates: Templates = null;
  ruleParts: RulePart[] = [];

  ruleForm = this.fb.group({
    symptoms: this.fb.array([]),
    riskFactors: this.fb.array([]),
    diagnoseControls: ["", Validators.compose([Validators.required])],
  });

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

  newTemplate(): FormGroup {
    return this.fb.group({
      templateName: ["", Validators.compose([Validators.required])],
    });
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

  // addPart(name: string) {
  //   if (name.charAt(0) === "S") {
  //     let part: RulePart = {
  //       shortName: name,
  //       fullName: this.templates.symptoms[Number.parseInt(name.charAt(1))].name,
  //     };
  //     this.templates.symptoms[Number.parseInt(name.charAt(1))].toDisable = true;
  //   }
  //   if (name.charAt(0) === "R") {
  //     let part: RulePart = {
  //       shortName: name,
  //       fullName: this.templates.riskFactors[Number.parseInt(name.charAt(1))]
  //         .name,
  //     };
  //     this.templates.riskFactors[
  //       Number.parseInt(name.charAt(1))
  //     ].toDisable = true;
  //   }
  // }

  get f() {
    return this.ruleForm.controls;
  }

  get templateControls() {
    return this.ruleForm.get("templateControls") as FormArray;
  }
}
