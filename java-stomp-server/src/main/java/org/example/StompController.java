package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Data
class Message {
    String subject;
    String content;
}

/**
 * The meaning of a destination is intentionally left opaque in the STOMP spec.
 * It can be any string, and it’s entirely up to STOMP servers to define the
 * semantics and the syntax of the destinations that they support.
 * It is very common, however, for destinations to be path-like strings where
 * "/topic/.." implies publish-subscribe (one-to-many)
 * and "/queue/" implies point-to-point (one-to-one) message exchanges.
 * docs: https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#websocket-stomp
 */
@Controller
@Slf4j
@AllArgsConstructor
public class StompController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    // this in NOT called when connecting, has to be called manually
    @MessageMapping("/connect")
    @SendToUser("/queue/connect")
    public Message connect(Message message, SimpMessageHeaderAccessor headerAccessor) {
        log.debug(headerAccessor.getSessionId());
        // echo message back -> this will go to queue/connect only to the user who sent it
        return message;
    }

    @MessageMapping("/brdcast")
//    @SendTo("/topic/message") -> alternative with return
    public void brdcast(Message message, SimpMessageHeaderAccessor headerAccessor) {
        log.debug("broadcast");
        // send to everyone
        simpMessagingTemplate.convertAndSend("/topic/message", message);
    }
}

