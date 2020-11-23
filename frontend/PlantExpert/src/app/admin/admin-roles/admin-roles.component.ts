import { FormBuilder } from "@angular/forms";
import { AdminService } from "./../admin.service";
import { Component, OnInit } from "@angular/core";
import { UserDto } from "src/app/models/models";

@Component({
  selector: "app-admin-roles",
  templateUrl: "./admin-roles.component.html",
  styleUrls: ["./admin-roles.component.css"],
})
export class AdminRolesComponent implements OnInit {
  searchForm = this.fb.group({
    name: [""],
  });

  users: UserDto[] = [];

  constructor(private adminService: AdminService, private fb: FormBuilder) {}

  ngOnInit() {
    this.findAllByUsername();
  }

  findAllByUsername() {
    const username = this.searchForm.get("name").value;
    console.log(username);
    this.adminService
      .findAllByUsername(username)
      .subscribe((data: UserDto[]) => {
        this.users = data;
      });
  }

  grantModeratorAuthority(index: number) {
    this.adminService
      .grantModeratorRole(this.users[index])
      .subscribe((data: UserDto) => {});
    this.users[index].roles.push("ROLE_MODERATOR");
  }

  takeModeratorAuthority(index: number) {
    this.adminService
      .takeModeratorRole(this.users[index])
      .subscribe((data: UserDto) => {});
    let ind: number = this.users[index].roles.indexOf("ROLE_MODERATOR");
    this.users[index].roles.splice(ind, 1);
  }
}
