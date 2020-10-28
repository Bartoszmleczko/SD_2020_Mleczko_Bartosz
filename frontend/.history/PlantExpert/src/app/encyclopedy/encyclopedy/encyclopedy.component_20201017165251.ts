import { map } from "rxjs/operators";
import { DiagnoseService } from "./../../diagnose/diagnose.service";
import { Component, OnInit } from "@angular/core";
import { DomSanitizer, SafeUrl } from "@angular/platform-browser";
import { EncyclopedyService } from "../encyclopedy.service";
import { Observable } from "rxjs";
import { DiseaseDto } from "src/app/models/models";

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
  encyclopedyData: DiseaseDto[] = [];

  constructor(
    private diagnoseService: DiagnoseService,
    private sanitizer: DomSanitizer,
    private encyclopedyService: EncyclopedyService
  ) {}

  ngOnInit() {
    // this.getImage();
  }

  getData() {
    this.encyclopedyService.getData(0, 5).subscribe((data: DiseaseDto[]) => {
      this.encyclopedyData = data;
    });
  }

  getAllNames(diseases: DiseaseDto[]): string[] {
    return diseases.map((dis) => dis.diseaseName);
  }

  getImage(names: string[]) {
    this.encyclopedyService.getImages(names).subscribe((data: Blob) => {
      let image: SafeUrl = null;

      const reader = new FileReader();
      reader.readAsDataURL(data);
      reader.onload = (e) => (
        (this.imageBytes = reader.result),
        (image = this.sanitizer.bypassSecurityTrustUrl(this.imageBytes))
      );
      console.log(image);
    });
  }

  // getImage() {
  //   this.diagnoseService.getImage().subscribe((data) => {
  //     const reader = new FileReader();
  //     reader.readAsDataURL(data);
  //     reader.onload = (e) => (
  //       (this.imageBytes = reader.result),
  //       (this.image = this.sanitizer.bypassSecurityTrustUrl(this.imageBytes))
  //     );
  //   });
  // }
}
