import { Plant } from "./../../models/models";
import { AdminService } from "./../admin.service";
import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-admin-plant",
  templateUrl: "./admin-plant.component.html",
  styleUrls: ["./admin-plant.component.css"],
})
export class AdminPlantComponent implements OnInit {
  plantRequests: Plant[] = [];

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.getPlantRequests();
  }

  getPlantRequests() {
    this.adminService.getPlantRequests().subscribe((data: Plant[]) => {
      this.plantRequests = data;
    });
  }

  acceptRequest(index: number) {
    this.adminService
      .acceptPlantRequest(this.plantRequests[index])
      .subscribe((data: Plant) => {});
    this.plantRequests.splice(index, 1);
  }

  deletePlantRequest(index: number) {
    this.adminService
      .declinePlantRequest(this.plantRequests[index].id)
      .subscribe((data: string) => {
        console.log(data);
      });
    this.plantRequests.splice(index, 1);
  }
}
