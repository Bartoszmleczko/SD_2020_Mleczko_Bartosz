import { DiagnoseService } from "./../../diagnose/diagnose.service";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-encyclopedy",
  templateUrl: "./encyclopedy.component.html",
  styleUrls: ["./encyclopedy.component.css"],
})
export class EncyclopedyComponent implements OnInit {
  imageURL: string;

  constructor(private diagnoseService: DiagnoseService) {}

  ngOnInit() {
    this.getImage();
  }

  getImage() {
    this.diagnoseService
      .getImage()
      .subscribe((data: string) => (this.imageURL = data));
  }
}
