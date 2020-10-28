import { Plant } from "./../../models/models";
import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { DiagnoseService } from "src/app/diagnose/diagnose.service";

@Component({
  selector: "app-moderator",
  templateUrl: "./moderator.component.html",
  styleUrls: ["./moderator.component.css"],
})
export class ModeratorComponent implements OnInit {
  newPlantForm = this.fb.group({
    name: [""],
  });

  plants: Plant[] = [];

  constructor(
    private fb: FormBuilder,
    private diagnoseService: DiagnoseService
  ) {}

  ngOnInit() {}

  getPlants() {
    this.diagnoseService.getPlantTypes().subscribe((data: Plant[]) => {
      this.plants = data;
    });
  }
}
