import { Component, OnInit } from '@angular/core';
import { DiagnoseService } from '../diagnose.service';

interface Plant{
  name: String;
  templateName: String;
}

@Component({
  selector: 'app-diagnose-wrapper',
  templateUrl: './diagnose-wrapper.component.html',
  styleUrls: ['./diagnose-wrapper.component.css']
})
export class DiagnoseWrapperComponent implements OnInit {

  selected = "Pszenica";

  plants: Plant[] =[
    {name: "Pszenica", templateName: "CEREALS"},
    {name: "Rzepak", templateName: "COLZA"},
    {name: "Ziemniaki", templateName: "POTATOES"}
  ];

  constructor(private diagnoseService: DiagnoseService) { }

  ngOnInit() {
  }

  getDiagnoseFormData(templateName: string){

    console.log(templateName);
    this.diagnoseService.getDiagnoseFormData(templateName).subscribe(
      data => {
        console.log(data);
      }
    );


  }

}
