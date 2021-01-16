import { Component, Inject, OnInit } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";

@Component({
  selector: "app-backend-message",
  templateUrl: "./backend-message.component.html",
  styleUrls: ["./backend-message.component.css"],
})
export class BackendMessageComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<BackendMessageComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit() {}

  close() {
    this.dialogRef.close();
  }
}
