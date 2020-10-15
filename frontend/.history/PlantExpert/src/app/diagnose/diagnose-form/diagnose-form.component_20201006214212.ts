import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-diagnose-form',
  templateUrl: './diagnose-form.component.html',
  styleUrls: ['./diagnose-form.component.css']
})
export class DiagnoseFormComponent implements OnInit {

  symptomGroup: FormGroup;
  ordersData = [];


  constructor(private formBuilder: FormBuilder) {
    this.symptomGroup = this.formBuilder.group({
        orders: []
    });
   }

  ngOnInit() {
  }

}
