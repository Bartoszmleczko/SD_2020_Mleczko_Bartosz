import { map } from 'rxjs/operators';
import { Plant, DiagnoseForm } from './../../models/models';
import { Component, OnInit } from '@angular/core';
import { DiagnoseService } from '../diagnose.service';

@Component({
  selector: 'app-diagnose-wrapper',
  templateUrl: './diagnose-wrapper.component.html',
  styleUrls: ['./diagnose-wrapper.component.css']
})
export class DiagnoseWrapperComponent implements OnInit {

  selected = "Pszenica";
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
    this.diagnoseService.getDiagnoseForm(templateName).subscribe(
      data => {this.formData = data;}
    );
    console.log(this.formData);

  }

}
