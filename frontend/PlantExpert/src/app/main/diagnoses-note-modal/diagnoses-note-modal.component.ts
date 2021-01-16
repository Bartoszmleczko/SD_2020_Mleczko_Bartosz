import { AsyncSubject } from "rxjs";
import { Subject } from "rxjs";
import { FormBuilder, Validators } from "@angular/forms";
import { Component, Inject, OnInit, Optional } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { EncyclopedyService } from "src/app/encyclopedy/encyclopedy.service";
import {
  DiagnoseDto,
  DiseaseDto,
  ExtendedDiseaseDto,
} from "src/app/models/models";
import { DomSanitizer } from "@angular/platform-browser";
import { DiagnoseService } from "src/app/diagnose/diagnose.service";
import { maxLength } from "../../services/maxlength.validator";
@Component({
  selector: "app-diagnoses-note-modal",
  templateUrl: "./diagnoses-note-modal.component.html",
  styleUrls: ["./diagnoses-note-modal.component.css"],
})
export class DiagnosesNoteModalComponent implements OnInit {
  diagnose: DiagnoseDto = null;
  private editorSubject: Subject<any> = new AsyncSubject();
  diseases: ExtendedDiseaseDto[] = [];
  arr: string[] = [];

  noteForm = this.fb.group({
    note: [
      "",
      Validators.compose([Validators.required]),
      maxLength(this.editorSubject, 100, 30),
    ],
  });

  constructor(
    public dialogRef: MatDialogRef<DiagnosesNoteModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private encyclopedyService: EncyclopedyService,
    private diagnoseService: DiagnoseService,
    private sanitizer: DomSanitizer,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.diagnose = this.data.data;
    console.log(this.diagnose);
    this.getDataNames(this.diagnose);
  }

  handleEditorInit(e) {
    this.editorSubject.next(e.editor);
    this.editorSubject.complete();
  }

  getDataNames(diag: DiagnoseDto) {
    for (let d of diag.diseases) {
      this.arr.push(d.diseaseName);
    }

    this.getDataFromDtos(this.arr);
  }

  getDataFromDtos(names: string[]) {
    this.encyclopedyService
      .getDiseasesFromDtos(names)
      .subscribe((data: any[]) => {
        console.log(data);
        this.diseases = data;
        this.diseases.forEach((d) => this.getImage(d));
      });
  }

  get f() {
    return this.noteForm.controls;
  }

  close() {
    this.dialogRef.close();
  }

  getImage(disease: any) {
    let imageBytes = null;
    this.diagnoseService.getImage(disease.name).subscribe((data) => {
      const reader = new FileReader();
      reader.readAsDataURL(data);
      reader.onloadend = (e) => (
        (imageBytes = reader.result),
        (disease.url = this.sanitizer.bypassSecurityTrustUrl(imageBytes))
      );
    });
  }

  addNote() {
    const newNote = this.noteForm.get("note").value;
    this.diagnose.note = newNote;
    this.diagnoseService.updateDiagnose(this.diagnose).subscribe(
      (data: DiagnoseDto) => {
        this.noteForm.reset();
        this.diagnose = data;
      },
      (error) => {
        console.log("Błąd zapisu");
      }
    );
  }
}
