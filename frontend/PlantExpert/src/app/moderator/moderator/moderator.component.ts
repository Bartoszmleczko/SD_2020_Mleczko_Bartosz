import { Plant } from "./../../models/models";
import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { DiagnoseService } from "src/app/diagnose/diagnose.service";
import { FileUploadService } from "src/app/services/file-upload.service";

@Component({
  selector: "app-moderator",
  templateUrl: "./moderator.component.html",
  styleUrls: ["./moderator.component.css"],
})
export class ModeratorComponent implements OnInit {
  selectedFiles: FileList;
  selectedFiles2: FileList;
  textFileToUpload: File = null;
  imageFileToUpload: File = null;
  filename: string = "";
  filename2: string = "";
  newPlantForm = this.fb.group({
    name: [""],
  });

  plant = null;
  plants: Plant[] = [];

  constructor(
    private fb: FormBuilder,
    private diagnoseService: DiagnoseService,
    private fileUploadService: FileUploadService
  ) {}

  ngOnInit() {
    this.getPlants();
  }

  handleTextFileInput(event) {
    this.selectedFiles = event.target.files;
    this.textFileToUpload = this.selectedFiles.item(0);
    this.filename = this.textFileToUpload.name;
    console.log(this.textFileToUpload);
  }

  handleImageFileInput(event) {
    this.selectedFiles2 = event.target.files;
    this.imageFileToUpload = this.selectedFiles2.item(0);
    this.filename2 = this.imageFileToUpload.name;
    console.log(this.imageFileToUpload);
  }

  getPlants() {
    this.diagnoseService.getPlantTypes().subscribe((data: Plant[]) => {
      this.plants = data;
    });
  }

  // uploadFileToActivity() {
  //   this.fileUploadService
  //     .postFile(this.textFileToUpload, this.plant, this.imageFileToUpload)
  //     .subscribe(
  //       (data) => {},
  //       (error) => {
  //         console.log(error);
  //       }
  //     );
  // }

  setPlant(name) {
    this.plant = name;
  }
}
