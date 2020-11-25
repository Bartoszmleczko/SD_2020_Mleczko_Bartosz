import { MatDialog } from "@angular/material";
import { ContactMessageDto } from "./../../models/models";
import { Component, OnInit } from "@angular/core";
import { FormBuilder, Validators } from "@angular/forms";
import { ContactService } from "../contact.service";
import { BackendMessageComponent } from "src/app/backend-message/backend-message/backend-message.component";

@Component({
  selector: "app-contact",
  templateUrl: "./contact.component.html",
  styleUrls: ["./contact.component.css"],
})
export class ContactComponent implements OnInit {
  contactForm = this.fb.group({
    title: [
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(15),
      ]),
    ],
    text: [
      "",
      Validators.compose([
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(200),
      ]),
    ],
  });

  constructor(
    private fb: FormBuilder,
    private contactService: ContactService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {}

  get f() {
    return this.contactForm.controls;
  }

  parseMessage(): ContactMessageDto {
    const message: ContactMessageDto = {
      id: null,
      header: this.contactForm.get("title").value,
      content: this.contactForm.get("text").value,
      creationDate: null,
      answer: null,
      answerTime: null,
      status: null,
      email: null,
      firstName: null,
      lastName: null,
    };
    return message;
  }

  sendMessage() {
    const message = this.parseMessage();
    this.contactService.saveMessage(message).subscribe(
      (data) => {
        this.contactForm.reset();
        this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: {
            data:
              "Pomyślnie wysłano wiadomość. Odpowiedź administracji zostanie wysłana na twój email",
          },
        });
      },
      (err) => {
        this.dialog.open(BackendMessageComponent, {
          width: "25%",
          panelClass: "app-full-bleed-dialog",
          data: {
            data:
              "Podczas wysyłania wiadomości wystąpił błąd. Spróbuj ponownie.",
          },
        });
      }
    );
  }
}
