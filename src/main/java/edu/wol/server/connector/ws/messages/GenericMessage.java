package edu.wol.server.connector.ws.messages;

import java.util.Date;

public class GenericMessage {
protected Date time;
protected String source;

public GenericMessage(String source) {
	this.source = source;
	time=new Date();
}
public Date getTime() {
	return time;
}
public void setTime(Date time) {
	this.time = time;
}
public String getSource() {
	return source;
}
public void setSource(String source) {
	this.source = source;
}
}
