import { DiagnoseService } from "./../../diagnose/diagnose.service";
import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { EncyclopedyService } from "../encyclopedy.service";

@Component({
  selector: "app-encyclopedy",
  templateUrl: "./encyclopedy.component.html",
  styleUrls: ["./encyclopedy.component.css"],
})
export class EncyclopedyComponent implements OnInit {
  imageBytes: any;
  image: any;

  constructor(
    private diagnoseService: DiagnoseService,
    private sanitizer: DomSanitizer,
    private encyclopedyService: EncyclopedyService
  ) {}

  ngOnInit() {
    // this.getImage();
  }

  getImage(names: string[]) {
    this.encyclopedyService.getImages(names).subscribe();
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
