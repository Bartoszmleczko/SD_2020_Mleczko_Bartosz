import { Component, Inject, OnInit } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { ContactMessageDto } from "src/app/models/models";

@Component({
  selector: "app-answer-form",
  templateUrl: "./answer-form.component.html",
  styleUrls: ["./answer-form.component.css"],
})
export class AnswerFormComponent implements OnInit {
  answerForm = this.fb.group({
    answer: [
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(200),
      ]),
    ],
  });

  originalMessage: ContactMessageDto;

  constructor(
    public dialogRef: MatDialogRef<AnswerFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.originalMessage = this.data.data;
  }

  get f() {
    return this.answerForm.controls;
  }

  sendAnswer() {
    this.originalMessage.answer = this.answerForm.get("answer").value;
    this.dialogRef.close(this.originalMessage);
  }
}
