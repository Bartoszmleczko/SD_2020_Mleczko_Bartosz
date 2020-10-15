import { map, switchMap } from 'rxjs/operators';
import { Plant, DiagnoseForm } from './../../models/models';
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
  selectedSymptomsValues: string[];


  riskFactorGroup: FormGroup;
  factorArr: FormArray;
  selectedRiskFactorValues:[];


  plants: Plant[] =[
    {name: "Pszenica", templateName: "CEREALS"},
    {name: "Rzepak", templateName: "COLZA"},
    {name: "Ziemniaki", templateName: "POTATOES"}
  ];

  constructor(private diagnoseService: DiagnoseService, private formBuilder: FormBuilder) {
    this.symptomGroup = this.formBuilder.group({
      symptomArr: FormArray
    })
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

    const riskFactorsArray = [];

    riskFactorsArray.join(this.formData.riskFactors.values().next().value);

    riskFactorsArray.forEach(v => console.log(v));

  }


  getDiagnoseFormData(templateName: string) {
    this.diagnoseService.getDiagnoseForm(templateName).subscribe((data: DiagnoseForm) => {
        this.formData = data;
        this.symptomArr = this.buildSymptoms();
        this.symptomGroup = this.formBuilder.group(this.symptomArr);
    });
  }


  submitSymptoms(){
      this.symptomArr.controls.forEach((control, i) => {
        if(control.value){
          console.log(this.formData.symptoms[i].slotName);
          this.selectedSymptomsValues.push(this.formData.symptoms[i].slotName);
        }
      })
  }

}
