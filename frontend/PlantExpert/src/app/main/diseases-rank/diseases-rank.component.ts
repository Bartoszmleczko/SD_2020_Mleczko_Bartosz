import { DiseaseDetailComponent } from "./../disease-detail/disease-detail.component";
import { MatDialog } from "@angular/material";
import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { DiagnoseService } from "src/app/diagnose/diagnose.service";
import { EncyclopedyService } from "src/app/encyclopedy/encyclopedy.service";
import { ExtendedDiseaseDto } from "src/app/models/models";

@Component({
  selector: "app-diseases-rank",
  templateUrl: "./diseases-rank.component.html",
  styleUrls: ["./diseases-rank.component.css"],
})
export class DiseasesRankComponent implements OnInit {
  diseases: ExtendedDiseaseDto[] = [];

  constructor(
    private encyclopedyService: EncyclopedyService,
    private diagnoseService: DiagnoseService,
    private sanitizer: DomSanitizer,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.getTop5Diseases();
  }

  getTop5Diseases() {
    this.encyclopedyService
      .getTop5Diseases()
      .subscribe((data: ExtendedDiseaseDto[]) => {
        this.diseases = data;
        this.diseases.forEach((d) => this.getImage(d));
      });
  }

  getImage(disease: any) {
    let imageBytes = null;
    this.diagnoseService.getImage(disease.diseaseName).subscribe((data) => {
      const reader = new FileReader();
      reader.readAsDataURL(data);
      reader.onloadend = (e) => (
        (imageBytes = reader.result),
        (disease.url = this.sanitizer.bypassSecurityTrustUrl(imageBytes))
      );
    });
  }

  openModal(index) {
    console.log(index);
    this.dialog.open(DiseaseDetailComponent, {
      width: "80%",
      panelClass: "app-full-bleed-dialog",
      data: { data: this.diseases[index] },
    });
  }
}
