import { Component, OnInit } from '@angular/core';
import { SocketService } from "./socket.service";
import { FormBuilder, FormControl, FormGroup } from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'stomp-client';

  form: FormGroup;

  list: Message[] = [];
  constructor(private service: SocketService, private fb: FormBuilder) {
  }

  async ngOnInit() {
    this.form = this.fb.group({
      subject: '',
      content: '',
    });
    await this.connect();
  }

  async connect() {
    await this.service.connect();
    console.log('mami')
    this.service.stompClient.subscribe('/user/queue/connect', (response: any) => {
      const msg = JSON.parse(response.body);
      this.list.push(msg);
    })

    this.service.stompClient.subscribe('/topic/message', (response: any) => {
      const msg = JSON.parse(response.body);
      this.list.push(msg);
    })


    this.service.callConnect({
      subject: "bla",
      content: "blabla",
    })
  }

  send() {
    this.service.callBrdcast({...this.form.getRawValue()});
  }
}


interface Message {
  subject: string;
  content: string;
}
