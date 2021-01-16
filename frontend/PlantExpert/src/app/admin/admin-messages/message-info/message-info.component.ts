import { ContactMessageDto } from "./../../../models/models";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material";
import { Component, Inject, OnInit } from "@angular/core";

@Component({
  selector: "app-message-info",
  templateUrl: "./message-info.component.html",
  styleUrls: ["./message-info.component.css"],
})
export class MessageInfoComponent implements OnInit {
  originalMessage: ContactMessageDto;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<MessageInfoComponent>
  ) {}

  ngOnInit() {
    this.originalMessage = this.data.data;
  }

  close() {
    this.dialogRef.close();
  }
}
