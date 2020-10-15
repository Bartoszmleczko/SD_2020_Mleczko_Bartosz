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
      this.symptomGroup.controls['symptoms'].addControl();
    });

    return this.formBuilder.array(arr);

  }


  getDiagnoseFormData(templateName: string) {
    this.diagnoseService.getDiagnoseForm(templateName).subscribe((data: DiagnoseForm) => {
        console.log(data);
        this.formData = data;

        this.formData.symptoms.forEach( (x) => x.selected = false);

        this.symptomGroup = new FormGroup({
          checkboxes: new FormGroup({ }),
        });
        
        this.formData.symptoms.forEach(item => {
          this.symptomGroup.controls['checkboxes'].addControl(item, new FormControl(true));
        });
    });
  }


  submitSymptoms(value){
    const formValue = Object.assign({}, value, {
      symptoms: value.symptoms.map((selected, i) => {
        console.log(i + ' ' + selected);
        return {
          selected
        }
      })
    });
  }

}
