import { ModeratorDiseaseEditFormComponent } from "./../moderator-disease-edit-form/moderator-disease-edit-form.component";
import { FormArray, FormGroup } from "@angular/forms";
import { Validators } from "@angular/forms";
import { FormBuilder } from "@angular/forms";
import {
  NewTemporaryDiseaseDto,
  RulePart,
  Templates,
} from "./../../models/models";
import { TemporaryDiseaseDto } from "src/app/models/models";
import { Component, OnInit, ViewChild } from "@angular/core";
import { ModeratorService } from "../moderator.service";

@Component({
  selector: "app-moderator-refused-diseases",
  templateUrl: "./moderator-refused-diseases.component.html",
  styleUrls: ["./moderator-refused-diseases.component.css"],
})
export class ModeratorRefusedDiseasesComponent implements OnInit {
  @ViewChild(ModeratorDiseaseEditFormComponent, { static: false })
  editFormComponent: ModeratorDiseaseEditFormComponent;

  refusedDiseases: NewTemporaryDiseaseDto[] = [];

  constructor(
    private moderatorService: ModeratorService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.getRefusedDiseases();
    console.log(this.refusedDiseases);
  }

  getRefusedDiseases() {
    this.moderatorService
      .getUsersRefusedRequests()
      .subscribe((data: NewTemporaryDiseaseDto[]) => {
        this.refusedDiseases = data;
      });
  }

  changeDisease(index: number) {
    this.editFormComponent.changeDisease(this.refusedDiseases[index]);
  }

  removeDiseaseFromList(data: NewTemporaryDiseaseDto) {
    if (this.refusedDiseases.includes(data)) {
      const index = this.refusedDiseases.findIndex((d) => (d = data));
      this.refusedDiseases.splice(index, 1);
    }
  }
}
