import { DiagnoseForm } from './../../models/models';
import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl } from '@angular/forms';

@Component({
  selector: 'app-diagnose-form',
  templateUrl: './diagnose-form.component.html',
  styleUrls: ['./diagnose-form.component.css']
})
export class DiagnoseFormComponent implements OnInit {

  @Input()
  formData: DiagnoseForm;

  symptomGroup: FormGroup;
  ordersData = [];

  constructor(private formBuilder: FormBuilder) {
   }



  ngOnInit() {
  }


}
