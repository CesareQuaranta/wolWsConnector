package edu.wol.server.connector.ws;

public class SessionStartMessage {
	private String token;
	private String ip;
	private String quadrante;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getQuadrante() {
		return quadrante;
	}
	public void setQuadrante(String quadrante) {
		this.quadrante = quadrante;
	}
}
