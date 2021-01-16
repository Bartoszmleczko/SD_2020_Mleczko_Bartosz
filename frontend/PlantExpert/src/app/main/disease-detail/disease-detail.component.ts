import { Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-disease-detail",
  templateUrl: "./disease-detail.component.html",
  styleUrls: ["./disease-detail.component.css"],
})
export class DiseaseDetailComponent implements OnInit {
  disease = null;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<DiseaseDetailComponent>
  ) {}

  ngOnInit() {
    this.disease = this.data.data;
  }

  close() {
    this.dialogRef.close();
  }
}
