import { Plant } from "./../../models/models";
import { AdminService } from "./../admin.service";
import { Component, OnInit } from "@angular/core";
import { BackendMessageComponent } from "src/app/backend-message/backend-message/backend-message.component";
import { MatDialog } from "@angular/material";

@Component({
  selector: "app-admin-plant",
  templateUrl: "./admin-plant.component.html",
  styleUrls: ["./admin-plant.component.css"],
})
export class AdminPlantComponent implements OnInit {
  plantRequests: Plant[] = [];

  constructor(private adminService: AdminService, private dialog: MatDialog) {}

  ngOnInit() {
    this.getPlantRequests();
  }

  getPlantRequests() {
    this.adminService.getPlantRequests().subscribe((data: Plant[]) => {
      this.plantRequests = data;
    });
  }

  acceptRequest(index: number) {
    this.adminService.acceptPlantRequest(this.plantRequests[index]).subscribe(
      (data: Plant) => {
        this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: { data: "Pomyślnie dodano roślinę." },
        });
      },
      (err) => {
        this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: { data: err.error },
        });
      }
    );
    this.plantRequests.splice(index, 1);
  }

  deletePlantRequest(index: number) {
    this.adminService
      .declinePlantRequest(this.plantRequests[index].id)
      .subscribe(
        (data: string) => {
          this.dialog.open(BackendMessageComponent, {
            width: "25%",
            panelClass: "app-full-bleed-dialog",
            data: { data: "Usunięto roślinę." },
          });
        },
        (err) => {
          console.log(err);
          this.dialog.open(BackendMessageComponent, {
            width: "25%",
            panelClass: "app-full-bleed-dialog",
            data: { data: err.error },
          });
        }
      );
    this.plantRequests.splice(index, 1);
  }
}
