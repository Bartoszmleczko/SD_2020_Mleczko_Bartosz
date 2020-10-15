import { Component, OnInit } from '@angular/core';

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

  constructor() { }

  ngOnInit() {
  }

  getDiagnoseFormData(templateName: string){

    console.log(templateName);

  }

}
