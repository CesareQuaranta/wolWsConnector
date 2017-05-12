package edu.wol.server.connector.ws.draft;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
/*
 * 
 * Date 28/apr/2017
 * @Author Cesare Quaranta
 *
 * Implementazione dell'iterfaccia @see WebSocketConfigurer per permettere di configurare tramite annotation 
 * le classi che implementano l'interfaccia websocket
 */
//@Configuration
//@EnableWebSocket
//@EnableWebMvc
public class WebSocketConfig{// extends WebMvcConfigurerAdapter implements WebSocketConfigurer{
	/*@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		 registry.addHandler(WolWebSocketHandler(), "/ws").setHandshakeHandler(new WolHandshakeHandler(new JettyRequestUpgradeStrategy()));
		 //registry.addHandler(WolInterfaceHandler(), "/wol").setAllowedOrigins("*");//.setHandshakeHandler(new DefaultHandshakeHandler(new JettyRequestUpgradeStrategy()));//.addInterceptors(new HttpSessionHandshakeInterceptor());
	}
	
	@Override // Allow serving HTML files through the default Servlet
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	@Bean
    public WebSocketHandler WolWebSocketHandler() {
        return new WolWebSocketHandler();
    }
	@Bean
    public DefaultHandshakeHandler handshakeHandler() {
        return new DefaultHandshakeHandler(new JettyRequestUpgradeStrategy());
    }
/*	@Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }
	
	@Bean
    public WebSocketHandler WolInterfaceHandler() {
        return new WolTextInterfaceHandler();
    }
	
*/
}
