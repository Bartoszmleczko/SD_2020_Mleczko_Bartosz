import { Component, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";

@Component({
  selector: "app-moderator",
  templateUrl: "./moderator.component.html",
  styleUrls: ["./moderator.component.css"],
})
export class ModeratorComponent implements OnInit {
  newPlantForm = this.fb.group({
    name: [""],
  });

  constructor(private fb: FormBuilder) {}

  ngOnInit() {}
}
