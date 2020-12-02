import { ContactMessageDto } from "./../../models/models";
import { MatDialog } from "@angular/material";
import { ContactService } from "./../contact.service";
import { Component, OnInit } from "@angular/core";
import { MessageInfoComponent } from "src/app/admin/admin-messages/message-info/message-info.component";

@Component({
  selector: "app-contact-old-messages",
  templateUrl: "./contact-old-messages.component.html",
  styleUrls: ["./contact-old-messages.component.css"],
})
export class ContactOldMessagesComponent implements OnInit {
  oldMessages: ContactMessageDto[] = [];

  constructor(
    private contactService: ContactService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.getAllUserMessages();
  }

  getAllUserMessages() {
    this.contactService
      .getCurrentUserAllMessages()
      .subscribe((data: ContactMessageDto[]) => {
        this.oldMessages = data;
      });
  }

  openInfoDialog(index) {
    const dialogRef = this.dialog.open(MessageInfoComponent, {
      width: "50%",
      panelClass: "app-full-bleed-dialog",
      data: { data: this.oldMessages[index] },
    });
  }
}
