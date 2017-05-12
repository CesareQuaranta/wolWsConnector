package edu.wol.server.connector.ws;

import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;


public class WolHandshakeHandler extends DefaultHandshakeHandler {//HttpSessionHandshakeInterceptor
public static final String PROTOCOL = "wol/1.0";

	public WolHandshakeHandler(
			RequestUpgradeStrategy requestUpgradeStrategy) {
		super(requestUpgradeStrategy);
		this.setSupportedProtocols(PROTOCOL);
	}

	public WolHandshakeHandler() {
		this.setSupportedProtocols(PROTOCOL);
	}
}
