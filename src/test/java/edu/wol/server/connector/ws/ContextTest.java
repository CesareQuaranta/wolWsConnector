package edu.wol.server.connector.ws;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.jsr356.server.AnnotatedServerEndpointConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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

import edu.wol.dom.Prospective;
import edu.wol.dom.User;
import edu.wol.dom.WorldContainer;
import edu.wol.dom.commands.Command;
import edu.wol.dom.services.UserEventListener;
import edu.wol.dom.services.UserInterface;
import edu.wol.dom.space.Position;
import edu.wol.server.connector.ws.decoders.RSADecoder;

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
@PropertySource("classpath:test.properties")
public class ContextTest {//implements WebSocketConfigurer{
	@Autowired
	private WebApplicationContext context;
	@Bean
	public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
 
        messageSource.setBasename("i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
 
        return messageSource;
    }
	@Bean
	public UserInterface mockUI(){
		return new UserInterface(){

			@Override
			public User loadUser(String username) {
				Prospective p=new Prospective();
				User user = new User(username,p);
				return user;
			}

			@Override
			public void moveUser(User user, Position pos) {
				System.out.println("User "+user.getUsername()+" move to "+pos.toString());
			}

			@Override
			public void rotateUser(User user, Position newHorizon) {
				System.out.println("User "+user.getUsername()+" rotare to "+newHorizon.toString());
			}

			@Override
			public void executeUserCommand(User user, Command com) {
				System.out.println("User "+user.getUsername()+" Execute command: "+com.toString());
			}

			@Override
			public void addUserListner(User user, UserEventListener listener) {
			}

			@Override
			public void removeUserListner(User user, UserEventListener listener) {
			}

			@Override
			public Collection getAllPhenomen(String wolID) throws IOException,
					Exception {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
	}
	@PostConstruct
	public void init() throws DeploymentException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	/*
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
	container = ( ServerContainer )context.getServletContext().
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
		};
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
