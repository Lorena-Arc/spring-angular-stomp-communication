import { Injectable } from '@angular/core';
import { Client, CompatClient, Frame, Stomp } from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
    providedIn: 'root'
  }
)
export class SocketService {
  url = 'http://localhost:8080/entry';
  stompClient: CompatClient;

  async connect() {
    const socket = new SockJS(this.url);
    this.stompClient = Stomp.over(socket);
    // there are for sure better methods, but it is what it is
    await (async () => {
      return new Promise<void>((resolve) => {
        this.stompClient.connect({}, () => {
          return resolve();
        });
      })
    })()
  }

  callConnect(request: any) {
     this.stompClient.send('/stomp/connect', {}, JSON.stringify(request));
  }

  callBrdcast(request: any) {
     this.stompClient.send('/stomp/brdcast', {}, JSON.stringify(request));
  }
}
