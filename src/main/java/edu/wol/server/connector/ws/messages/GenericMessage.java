package edu.wol.server.connector.ws.messages;

import java.util.Date;

public class GenericMessage {
protected Date time;
protected String source;
protected Object payload;

public GenericMessage(String source) {
	this.source = source;
	time=new Date();
	payload=null;
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
public Object getPayload() {
	return payload;
}
public void setPayload(Object payload) {
	this.payload = payload;
}
}
