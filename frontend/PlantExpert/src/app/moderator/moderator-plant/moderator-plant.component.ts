import { Plant } from "./../../models/models";
import { ModeratorService } from "./../moderator.service";
import { HttpClient } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";

@Component({
  selector: "app-moderator-plant",
  templateUrl: "./moderator-plant.component.html",
  styleUrls: ["./moderator-plant.component.css"],
})
export class ModeratorPlantComponent implements OnInit {
  newPlantForm = this.fb.group({
    name: [
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(20),
      ]),
    ],
  });

  backendMessage: string = "";

  constructor(
    private moderatorService: ModeratorService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {}

  savePlantRequest() {
    const plant: Plant = {
      id: null,
      name: this.newPlantForm.get("name").value,
      requestDate: null,
    };
    console.log();
    this.moderatorService
      .sendNewPlantRequest(plant)
      .subscribe((data: Plant) => {
        this.backendMessage = "Pomyślnie zapisano";
        this.newPlantForm.reset();
        this.newPlantForm.get("name").setValue("");
      });
  }

  get f() {
    return this.newPlantForm.controls;
  }
}
