import { DiagnoseForm } from './../../models/models';
import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl } from '@angular/forms';

@Component({
  selector: 'app-diagnose-form',
  templateUrl: './diagnose-form.component.html',
  styleUrls: ['./diagnose-form.component.css']
})
export class DiagnoseFormComponent implements OnInit {

  @Input()
  formData: DiagnoseForm;

  symptomGroup: FormGroup;
  ordersData = [];

  get symptomsFormArray(){
    return this.symptomGroup.controls.symptomForms as FormArray;
  }

  constructor(private formBuilder: FormBuilder) {
    this.symptomGroup = this.formBuilder.group({
        symptomForms: new FormArray([])
    });
   }



  ngOnInit() {
    this.addSymptomsToSymptomForms();
  }


   public addSymptomsToSymptomForms(): void {
     this.formData.symptoms.forEach( (x) => this.symptomsFormArray.push(new FormControl(false)));
   }
}
