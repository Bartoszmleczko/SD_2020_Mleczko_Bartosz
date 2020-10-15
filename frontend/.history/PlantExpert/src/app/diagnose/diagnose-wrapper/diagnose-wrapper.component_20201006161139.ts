import { DiagnoseForm } from './../../../../../.history/PlantExpert/src/app/models/models_20201006144113';
import { map, switchMap } from 'rxjs/operators';
import { Plant, DiagnoseForm } from './../../models/models';
import { Component, OnInit } from '@angular/core';
import { DiagnoseService } from '../diagnose.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-diagnose-wrapper',
  templateUrl: './diagnose-wrapper.component.html',
  styleUrls: ['./diagnose-wrapper.component.css']
})
export class DiagnoseWrapperComponent implements OnInit {

  selected = "CEREALS";
  formData: DiagnoseForm;

  plants: Plant[] =[
    {name: "Pszenica", templateName: "CEREALS"},
    {name: "Rzepak", templateName: "COLZA"},
    {name: "Ziemniaki", templateName: "POTATOES"}
  ];

  constructor(private diagnoseService: DiagnoseService) { }

  ngOnInit() {
  }

  getDiagnoseFormData(templateName: string) {
    console.log(templateName);
    console.log(this.formData);
    this.diagnoseService.getDiagnoseForm(templateName).subscribe((data: DiagnoseForm) => this.formData = data);
    
  }

}
