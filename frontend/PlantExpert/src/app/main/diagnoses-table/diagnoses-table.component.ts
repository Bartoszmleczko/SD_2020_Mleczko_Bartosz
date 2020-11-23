import { DiagnosesNoteModalComponent } from "./../diagnoses-note-modal/diagnoses-note-modal.component";
import { MatDialog } from "@angular/material";
import { DiagnoseDto } from "./../../models/models";
import { Component, OnInit } from "@angular/core";
import { DiagnoseService } from "src/app/diagnose/diagnose.service";

@Component({
  selector: "app-diagnoses-table",
  templateUrl: "./diagnoses-table.component.html",
  styleUrls: ["./diagnoses-table.component.css"],
})
export class DiagnosesTableComponent implements OnInit {
  diagnoses: DiagnoseDto[] = [];

  constructor(
    private diagnoseService: DiagnoseService,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.getUserDiagnoses();
  }

  public getUserDiagnoses() {
    this.diagnoseService.getUserDiagnoses().subscribe((data: DiagnoseDto[]) => {
      this.diagnoses = data;
    });
  }

  public openDiagnoseModal(index: number) {
    const diagnoseDialog = this.dialog.open(DiagnosesNoteModalComponent, {
      width: "80%",
      panelClass: "app-full-bleed-dialog",
      data: { data: this.diagnoses[index] },
    });
  }
}
