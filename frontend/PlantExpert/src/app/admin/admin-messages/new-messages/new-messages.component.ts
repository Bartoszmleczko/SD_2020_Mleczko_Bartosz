import { AnswerFormComponent } from "./../answer-form/answer-form.component";
import { MatDialog } from "@angular/material";
import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { ContactService } from "src/app/contact/contact.service";
import { ContactMessageDto } from "src/app/models/models";

@Component({
  selector: "app-new-messages",
  templateUrl: "./new-messages.component.html",
  styleUrls: ["./new-messages.component.css"],
})
export class NewMessagesComponent implements OnInit {
  newMessages: ContactMessageDto[] = [];

  @Output()
  messageEmitter: EventEmitter<ContactMessageDto> = new EventEmitter();

  constructor(
    private contactService: ContactService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.getUnansweredMessages();
  }

  getUnansweredMessages() {
    this.contactService
      .getUnansweredMessages()
      .subscribe((data: ContactMessageDto[]) => {
        this.newMessages = data;
      });
  }

  deleteMessage(index) {
    console.log(this.newMessages[index].id);
    this.contactService
      .deleteMessage(this.newMessages[index].id)
      .subscribe((data) => {});
    this.newMessages.splice(index, 1);
  }

  openReplyDialog(index) {
    const dialogRef = this.dialog.open(AnswerFormComponent, {
      width: "50%",
      panelClass: "app-full-bleed-dialog",
      data: { data: this.newMessages[index] },
    });
    dialogRef.afterClosed().subscribe((message: ContactMessageDto) => {
      if (message != null && message.answer != null) {
        this.contactService
          .answerMessage(message)
          .subscribe((data: ContactMessageDto) => {
            this.messageEmitter.emit(data);
            this.newMessages.splice(index, 1);
          });
      }
    });
  }
}
