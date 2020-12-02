import { MessageInfoComponent } from "./../message-info/message-info.component";
import { Component, OnInit } from "@angular/core";
import { MatDialog } from "@angular/material";
import { BackendMessageComponent } from "src/app/backend-message/backend-message/backend-message.component";
import { ContactService } from "src/app/contact/contact.service";
import { ContactMessageDto } from "src/app/models/models";

@Component({
  selector: "app-old-messages",
  templateUrl: "./old-messages.component.html",
  styleUrls: ["./old-messages.component.css"],
})
export class OldMessagesComponent implements OnInit {
  oldMessages: ContactMessageDto[] = [];

  constructor(
    private contactService: ContactService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.getOldMessages();
  }

  getOldMessages() {
    this.contactService
      .getAnsweredMessages()
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

  deleteMessage(index) {
    this.contactService.deleteMessage(this.oldMessages[index].id).subscribe(
      (data) => {
        const dialogRef = this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: { data: "Pomyślnie usunięto wiadomość" },
        });
      },
      (err) => {
        console.log(err);
        const dialogRef = this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: { data: err.error },
        });
      }
    );
    this.oldMessages.splice(index, 1);
  }
}
