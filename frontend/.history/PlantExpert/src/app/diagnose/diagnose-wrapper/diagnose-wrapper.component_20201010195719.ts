import { map, switchMap } from 'rxjs/operators';
import { Plant, DiagnoseForm, RiskFactor, RequestSlotDto, PlantSicknessRequest, DiseaseDto } from './../../models/models';
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
  selectedSymptomsValues: RequestSlotDto[] = [];
  symptomsCompleted: boolean = false;


  riskFactorGroup: FormGroup;
  factorArr: FormArray;
  flatRiskFactorArr: RiskFactor[];
  selectedRiskFactorValues: RequestSlotDto[] = [];

  diagnose: DiseaseDto[];


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

    this.flatRiskFactorArr = [];
    const factors: Map<string, RiskFactor[]> = this.formData.riskFactors;

    let riskFactorControlArray: FormControl[] = [];


    for( let wrapper of Object.values(factors)){
      for(let rf of wrapper){
        riskFactorControlArray.push(this.formBuilder.control(false));
        this.flatRiskFactorArr.push(rf);
      }
    }

    return this.formBuilder.array(riskFactorControlArray);
  }


  getDiagnoseFormData(templateName: string) {
    this.diagnoseService.getDiagnoseForm(templateName).subscribe((data: DiagnoseForm) => {
        this.formData = data;
        this.symptomArr = this.buildSymptoms();
        this.symptomGroup = this.formBuilder.group(this.symptomArr);
        this.factorArr = this.buildRiskFactors();
        this.riskFactorGroup = this.formBuilder.group(this.factorArr);
    });
  }


  submitSymptoms(){
      this.symptomArr.controls.forEach((control, i) => {
        if (control.value) {
          let tempDto: RequestSlotDto = {name: this.formData.symptoms[i].name, slotName: this.formData.symptoms[i].slotName};
          this.selectedSymptomsValues.push(tempDto);
        }
      });
      this.symptomsCompleted = true;
  }

  submitRiskFactors(){
    this.factorArr.controls.forEach((control, i) => {
      if(control.value){
        let tempDto: RequestSlotDto = {name: this.flatRiskFactorArr[i].name, slotName: this.flatRiskFactorArr[i].slotName};
        this.selectedRiskFactorValues.push(tempDto);
      }
    });

    this.sendFormToEvaluation();

  }



  sendFormToEvaluation(){

    const sicknessRequest: PlantSicknessRequest = {riskFactors: this.selectedRiskFactorValues, symptoms: this.selectedSymptomsValues};

    this.diagnoseService.sendFormForEvaluation(sicknessRequest).subscribe((data: DiseaseDto[]) =>{
      console.log(data);
      this.diagnose = data;
      this.symptomArr.reset();
    } );

  }

  resetBoxes(){
    console.log("reset");
  }

}
