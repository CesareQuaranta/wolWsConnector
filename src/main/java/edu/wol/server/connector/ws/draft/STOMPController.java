package edu.wol.server.connector.ws.draft;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

//@Controller
public class STOMPController {
	//    @MessageMapping("/chat")
	  //  @SendTo("/topic/messages")
	    public String send(final String message) throws Exception {

	        final String time = new SimpleDateFormat("HH:mm").format(new Date());
	        return message+time;
	    }
}
