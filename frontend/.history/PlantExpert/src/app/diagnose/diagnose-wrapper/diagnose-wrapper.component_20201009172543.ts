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
  arr:FormControl[];


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



  getDiagnoseFormData(templateName: string) {
    this.diagnoseService.getDiagnoseForm(templateName).subscribe((data: DiagnoseForm) => {
        this.formData = data;
        this.symptomGroup = this.formBuilder.group({
          symptoms: this.buildSymptoms()
        });
    });
  }


  submitSymptoms(value){
      this.formData.symptoms.forEach(s => console.log(this.symptomGroup.get(s.name).value));
      this.formData.symptoms.forEach(s => console.log(s.checked));
  }

}
