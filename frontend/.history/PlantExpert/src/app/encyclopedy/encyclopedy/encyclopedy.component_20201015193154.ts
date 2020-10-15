import { DiagnoseService } from "./../../diagnose/diagnose.service";
import { Component, OnInit } from "@angular/core";
import { DomSanitizer } from "@angular/platform-browser";
import { read } from "fs";

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
    });
  }
}
