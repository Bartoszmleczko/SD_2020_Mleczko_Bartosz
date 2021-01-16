import { AsyncSubject, Subject } from "rxjs";
import { BackendMessageComponent } from "./../../backend-message/backend-message/backend-message.component";
import { ContactMessageDto } from "./../../models/models";
import { MatDialog } from "@angular/material";
import { ContactService } from "./../contact.service";
import { FormBuilder } from "@angular/forms";
import { Validators } from "@angular/forms";
import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { maxLength } from "../../services/maxlength.validator";

@Component({
  selector: "app-contact-form",
  templateUrl: "./contact-form.component.html",
  styleUrls: ["./contact-form.component.css"],
})
export class ContactFormComponent implements OnInit {
  @Output()
  messageEmitter: EventEmitter<ContactMessageDto> = new EventEmitter();
  private editorSubject: Subject<any> = new AsyncSubject();
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
      Validators.compose([Validators.required]),
      maxLength(this.editorSubject, 200, 5),
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
      (data: ContactMessageDto) => {
        this.contactForm.reset();
        this.messageEmitter.emit(data);
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

  handleEditorInit(e) {
    this.editorSubject.next(e.editor);
    this.editorSubject.complete();
  }
}
