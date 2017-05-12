package edu.wol.server.connector.ws;

import javax.annotation.PostConstruct;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.jsr356.server.AnnotatedServerEndpointConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.ExceptionWebSocketHandlerDecorator;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.websocket.server.ServerEndpointConfig;
/**
 * 
 * Date 29/apr/2017
 * @Author Cesare Quaranta
 *
 *Configurazione contesto spring per test
 */
@Configuration
@ComponentScan(basePackages = {"wol.server.connector.ws"})
@EnableWebSocket
public class TestContextConfiguration {//implements WebSocketConfigurer{
	@Autowired
	private WebApplicationContext context;
	private ServerContainer container;
	
	@Bean
	public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
 
        messageSource.setBasename("i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
 
        return messageSource;
    }
	
	public class SpringServerEndpointConfigurator extends ServerEndpointConfig.Configurator {
		@Override
		public < T > T getEndpointInstance( Class< T > endpointClass ) 
		throws InstantiationException {
		return context.getAutowireCapableBeanFactory().createBean( endpointClass ); 
		}
		}
	@Bean
	public ServerEndpointConfig.Configurator configurator() {
		return new SpringServerEndpointConfigurator();
	}




	@PostConstruct
	public void init() throws DeploymentException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	/*container = ( ServerContainer )context.getServletContext().
	getAttribute( javax.websocket.server.ServerContainer.class.getName() );

	container.addEndpoint(AnnotatedWebSocketEndpoint.class);*/
	
	/*new AnnotatedServerEndpointConfig( 
		AnnotatedWebSocketEndpoint.class, 
		AnnotatedWebSocketEndpoint.class.getAnnotation( ServerEndpoint.class ) 
		) {
			@Override
			public Configurator getConfigurator() {
			return configurator();
			}
		};*/
	} 
	/*
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
      registry.addHandler(testWebSocketHandler(),
            "/echo").setHandshakeHandler(handshakeHandler());
    }
    
    @Bean
    public WebSocketHandler testWebSocketHandler() {
    	return new ExceptionWebSocketHandlerDecorator(new PerConnectionWebSocketHandler(WolWebSocketHandler.class));
    }

    @Bean
    public DefaultHandshakeHandler handshakeHandler() {

        WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
        policy.setInputBufferSize(8192);
        policy.setIdleTimeout(600000);
        return new WolHandshakeHandler(new JettyRequestUpgradeStrategy(policy));
    }*/
}
