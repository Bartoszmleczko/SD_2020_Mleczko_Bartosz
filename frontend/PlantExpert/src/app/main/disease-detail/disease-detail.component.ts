import { Inject } from "@angular/core";
import { MAT_DIALOG_DATA } from "@angular/material";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-disease-detail",
  templateUrl: "./disease-detail.component.html",
  styleUrls: ["./disease-detail.component.css"],
})
export class DiseaseDetailComponent implements OnInit {
  disease = null;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit() {
    this.disease = this.data.data;
  }
}
