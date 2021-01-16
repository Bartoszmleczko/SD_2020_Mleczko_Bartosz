import { AsyncSubject } from "rxjs";
import { Subject } from "rxjs";
import { Component, Inject, OnInit } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { ContactMessageDto } from "src/app/models/models";
import { maxLength } from "../../../services/maxlength.validator";
@Component({
  selector: "app-answer-form",
  templateUrl: "./answer-form.component.html",
  styleUrls: ["./answer-form.component.css"],
})
export class AnswerFormComponent implements OnInit {
  private editorSubject: Subject<any> = new AsyncSubject();

  answerForm = this.fb.group({
    answer: [
      "",
      Validators.compose([Validators.required]),
      maxLength(this.editorSubject, 200, 10),
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

  handleEditorInit(e) {
    this.editorSubject.next(e.editor);
    this.editorSubject.complete();
  }

  get f() {
    return this.answerForm.controls;
  }

  sendAnswer() {
    this.originalMessage.answer = this.answerForm.get("answer").value;
    this.dialogRef.close(this.originalMessage);
  }

  close() {
    this.dialogRef.close();
  }
}
