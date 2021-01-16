import { ContactOldMessagesComponent } from "./../contact-old-messages/contact-old-messages.component";

import { MatDialog } from "@angular/material";
import { ContactMessageDto } from "./../../models/models";
import { Component, OnInit, ViewChild } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";
import { ContactService } from "../contact.service";
import { BackendMessageComponent } from "src/app/backend-message/backend-message/backend-message.component";

@Component({
  selector: "app-contact",
  templateUrl: "./contact.component.html",
  styleUrls: ["./contact.component.css"],
})
export class ContactComponent implements OnInit {
  @ViewChild(ContactOldMessagesComponent)
  contactOldMessagesComponent: ContactOldMessagesComponent;

  constructor() {}

  ngOnInit() {}

  pushNewMessage(event) {
    this.contactOldMessagesComponent.oldMessages.unshift(event);
  }
}
