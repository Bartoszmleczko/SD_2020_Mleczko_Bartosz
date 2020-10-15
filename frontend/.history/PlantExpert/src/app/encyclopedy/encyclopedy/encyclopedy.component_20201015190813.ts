import { DiagnoseService } from "./../../diagnose/diagnose.service";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-encyclopedy",
  templateUrl: "./encyclopedy.component.html",
  styleUrls: ["./encyclopedy.component.css"],
})
export class EncyclopedyComponent implements OnInit {
  imageBytes: any;
  image: any;

  constructor(private diagnoseService: DiagnoseService) {}

  ngOnInit() {
    this.getImage();
  }

  getImage() {
    this.diagnoseService.getImage().subscribe((data) => {
      this.imageBytes = data;
      this.image = "data:image/jpeg;base64," + this.imageBytes;
    });
  }
}
