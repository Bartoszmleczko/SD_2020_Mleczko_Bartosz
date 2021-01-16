import { Component, OnInit, ViewChild } from "@angular/core";
import { OldMessagesComponent } from "../old-messages/old-messages.component";

@Component({
  selector: "app-messages",
  templateUrl: "./messages.component.html",
  styleUrls: ["./messages.component.css"],
})
export class MessagesComponent implements OnInit {
  @ViewChild(OldMessagesComponent)
  oldMessagesComponent: OldMessagesComponent;

  constructor() {}

  ngOnInit() {}

  passToOldMessages(data) {
    this.oldMessagesComponent.oldMessages.unshift(data);
  }
}
