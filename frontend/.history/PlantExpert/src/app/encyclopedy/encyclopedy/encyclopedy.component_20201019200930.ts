import { map } from "rxjs/operators";
import { DiagnoseService } from "./../../diagnose/diagnose.service";
import { Component, OnInit } from "@angular/core";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { EncyclopedyService } from "../encyclopedy.service";
import { Observable } from "rxjs";
import { DiseaseDto } from "src/app/models/models";
import { PageEvent } from "@angular/material";

@Component({
  selector: "app-encyclopedy",
  templateUrl: "./encyclopedy.component.html",
  styleUrls: ["./encyclopedy.component.css"],
})
export class EncyclopedyComponent implements OnInit {
  imageBytes: any;
  image: any;
  names: string[];
  images: SafeUrl[] = [];
  encyclopedyData: DiseaseDto[];
  pageSize: number = 5;
  pageNumber: number = 0;
  totalElements: number = 1;

  constructor(
    private diagnoseService: DiagnoseService,
    private sanitizer: DomSanitizer,
    private encyclopedyService: EncyclopedyService
  ) {}

  ngOnInit() {
    this.getData();
    this.images = [];
  }

  getData() {
    this.encyclopedyService
      .getData(this.pageNumber, this.pageSize)
      .subscribe((data) => {
        this.totalElements = data["totalElements"];
        this.encyclopedyData = data["content"];
        this.encyclopedyData.forEach((disease) => {
          console.log(disease.diseaseName);
          this.getImage(disease.diseaseName);
        });
      });
    window.setTimeout(() => {}, 2000);
  }

  getAllNames(diseases: DiseaseDto[]): string[] {
    return diseases.map((dis) => dis.diseaseName);
  }

  nextPage(event: PageEvent) {
    const request = {};
    this.pageNumber = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getData();
  }

  getImage(name: string) {
    this.imageBytes = null;
    this.image = null;
    this.diagnoseService.getImage(name).subscribe((data) => {
      const reader = new FileReader();
      console.log(data);
      reader.readAsDataURL(data);
      reader.onloadend = (e) => (
        (this.imageBytes = reader.result),
        (this.image = this.sanitizer.bypassSecurityTrustUrl(this.imageBytes))
      );
      this.images.push(this.image);
    });
  }
}
