import { map, switchMap } from 'rxjs/operators';
import { Plant, DiagnoseForm, RiskFactor } from './../../models/models';
import { Component, OnInit, Output, ViewChild } from '@angular/core';
import { DiagnoseService } from '../diagnose.service';
import { Observable } from 'rxjs';
import { DiagnoseFormComponent } from '../diagnose-form/diagnose-form.component';
import { FormGroup, FormBuilder, FormControl, FormArray } from '@angular/forms';

@Component({
  selector: 'app-diagnose-wrapper',
  templateUrl: './diagnose-wrapper.component.html',
  styleUrls: ['./diagnose-wrapper.component.css']
})
export class DiagnoseWrapperComponent implements OnInit {

  selected = "CEREALS";
  formData: DiagnoseForm;

  symptomGroup: FormGroup;
  symptomArr: FormArray;
  selectedSymptomsValues: string[] = [];


  riskFactorGroup: FormGroup;
  factorArr: FormArray;
  selectedRiskFactorValues:[] ;


  plants: Plant[] =[
    {name: "Pszenica", templateName: "CEREALS"},
    {name: "Rzepak", templateName: "COLZA"},
    {name: "Ziemniaki", templateName: "POTATOES"}
  ];

  constructor(private diagnoseService: DiagnoseService, private formBuilder: FormBuilder) {

   }

  ngOnInit() {
  }

  buildSymptoms(){

  const arr = this.formData.symptoms.map(symptom => {
    symptom.checked = false;
    return this.formBuilder.control(false);
  });

  return this.formBuilder.array(arr);

  }


  buildRiskFactors(){

    let riskFactorsArray: RiskFactor[] = [];

    for(let [key,val] of this.formData.riskFactors){
        console.log(valArr);
      
    }

  }


  getDiagnoseFormData(templateName: string) {
    this.diagnoseService.getDiagnoseForm(templateName).subscribe((data: DiagnoseForm) => {
        this.formData = data;
        this.symptomArr = this.buildSymptoms();
        this.symptomGroup = this.formBuilder.group(this.symptomArr);
        this.buildRiskFactors();
    });
  }


  submitSymptoms(){
      this.symptomArr.controls.forEach((control, i) => {
        if (control.value) {
          console.log(this.formData.symptoms[i].slotName + ' ' + i);
          this.selectedSymptomsValues.push(this.formData.symptoms[i].slotName);
        }
      });
  }

}
