import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

const API_URL = "http://localhost:8886/";
@Injectable({
  providedIn: "root",
})
export class ContactService {
  constructor(private httpClient: HttpClient) {}

  saveMessage(message) {
    return this.httpClient.post(API_URL + "messages", message);
  }

  getUnansweredMessages() {
    return this.httpClient.get(API_URL + "messages/unanswered");
  }

  getCurrentUserAllMessages() {
    return this.httpClient.get(API_URL + "users/messages");
  }
  getAnsweredMessages() {
    return this.httpClient.get(API_URL + "messages/answered");
  }

  deleteMessage(id) {
    return this.httpClient.delete(API_URL + "messages/" + id);
  }

  answerMessage(message) {
    return this.httpClient.put(API_URL + "messages", message);
  }
}
