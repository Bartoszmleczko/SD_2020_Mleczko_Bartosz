import { RefuseFormDto } from "./../../models/models";
import { MAT_DIALOG_DATA } from "@angular/material";
import { Inject } from "@angular/core";
import { MatDialogRef } from "@angular/material";
import {
  FormArray,
  FormBuilder,
  ValidatorFn,
  FormControl,
} from "@angular/forms";
import { Component, OnInit } from "@angular/core";

function minSelectedCheckboxes(min = 1) {
  const validator: ValidatorFn = (formArray: FormArray) => {
    const totalSelected = formArray.controls
      // get a list of checkbox values (boolean)
      .map((control) => control.value)
      // total up the number of checked checkboxes
      .reduce((prev, next) => (next ? prev + next : prev), 0);

    // if the total is not greater than the minimum, return the error message
    return totalSelected >= min ? null : { required: true };
  };

  return validator;
}

@Component({
  selector: "app-admin-disease-refuse",
  templateUrl: "./admin-disease-refuse.component.html",
  styleUrls: ["./admin-disease-refuse.component.css"],
})
export class AdminDiseaseRefuseComponent implements OnInit {
  names = [
    "Nazwa",
    "Opis",
    "Diagnoze zapobiegawcza",
    "Diagnoza interwencyjna",
    "Symptomy",
    "Czynniki ryzyka",
    "Reguły",
  ];

  newForm = this.fb.group({
    namesBoxes: this.fb.array([], { validators: minSelectedCheckboxes(1) }),
  });

  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<AdminDiseaseRefuseComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit() {
    this.createBoxes();
  }

  get f() {
    return this.newForm.controls;
  }

  get nameBoxes() {
    return this.newForm.get("namesBoxes") as FormArray;
  }

  createBoxes() {
    this.names.forEach((n) => this.nameBoxes.push(new FormControl(false)));
  }

  prepareDto() {
    const result: RefuseFormDto = {
      id: null,
      name: this.nameBoxes.get("0").value ? true : false,
      description: this.nameBoxes.get("1").value,
      precautionDiagnose: this.nameBoxes.get("2").value,
      interventionDiagnose: this.nameBoxes.get("3").value,
      symptoms: this.nameBoxes.get("4").value,
      riskFactors: this.nameBoxes.get("5").value,
      rules: this.nameBoxes.get("6").value,
    };
    return result;
  }

  send() {
    const result = this.prepareDto();
    this.newForm.reset();
    this.dialogRef.close(result);
  }
}
