import { map } from "rxjs/operators";
import { DiagnoseService } from "./../../diagnose/diagnose.service";
import { Component, OnInit } from "@angular/core";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { EncyclopedyService } from "../encyclopedy.service";
import { Observable } from "rxjs";
import { DiseaseDto, Plant } from "src/app/models/models";
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
  plantTypes: Plant[] = [];
  plant: string = "_";

  constructor(
    private diagnoseService: DiagnoseService,
    private sanitizer: DomSanitizer,
    private encyclopedyService: EncyclopedyService
  ) {}

  ngOnInit() {
    this.getData();
    this.getPlants();
    this.images = [];
  }

  getPlants() {
    this.diagnoseService.getPlantTypes().subscribe((data: Plant[]) => {
      this.plantTypes = data;
    });
  }

  setPlant(name) {
    this.plant = name;
    this.getData();
  }

  getData() {
    this.encyclopedyService
      .getData(this.pageNumber, this.pageSize, this.plant)
      .subscribe((data) => {
        this.totalElements = data["totalElements"];
        this.encyclopedyData = data["content"];
        this.encyclopedyData.forEach((disease) => {
          this.getImage(disease);
        });
      });
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

  getImage(disease: DiseaseDto) {
    this.imageBytes = null;
    this.image = null;
    this.diagnoseService.getImage(disease.diseaseName).subscribe((data) => {
      const reader = new FileReader();
      reader.readAsDataURL(data);
      reader.onloadend = (e) => (
        (this.imageBytes = reader.result),
        (disease.url = this.sanitizer.bypassSecurityTrustUrl(this.imageBytes))
      );
    });
  }
}
