import { DiagnoseService } from "./../../diagnose/diagnose.service";
import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";

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
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    this.getImage();
  }

  getImage() {
    this.diagnoseService.getImage().subscribe((data) => {
      console.log(data);
      const reader = new FileReader();
      reader.readAsDataURL(new Blob([data]));
      this.image = this.sanitizer.bypassSecurityTrustUrl(
        "data:image/jpg;base64," + this.image
      );
    });
  }
}
