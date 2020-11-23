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

@Component({
  selector: "app-diagnoses-note-modal",
  templateUrl: "./diagnoses-note-modal.component.html",
  styleUrls: ["./diagnoses-note-modal.component.css"],
})
export class DiagnosesNoteModalComponent implements OnInit {
  diagnose: DiagnoseDto = null;

  diseases: ExtendedDiseaseDto[] = [];
  arr: string[] = [];

  noteForm = this.fb.group({
    note: [
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(30),
        Validators.maxLength(100),
      ]),
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
        this.diagnose = data;
      },
      (error) => {
        console.log("Błąd zapisu");
      }
    );
  }
}
