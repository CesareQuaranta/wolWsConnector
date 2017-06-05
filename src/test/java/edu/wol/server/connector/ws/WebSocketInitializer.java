package edu.wol.server.connector.ws;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.websocket.DeploymentException;
import javax.websocket.Extension;
import javax.websocket.server.ServerContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@WebListener
public class WebSocketInitializer implements ServletContextListener{
		private final static String SERVER_CONTAINER_ATTRIBUTE = "javax.websocket.server.ServerContainer";
		public final static String WEBSOCKET_ENDPOINT = "/ws";
		final static Logger logger = LoggerFactory.getLogger(WebSocketInitializer.class);
		
		public WebSocketInitializer(){
			System.out.println("INSTANZIATED WEBSOCKET CONFIGURATOR");
		}
		
		@Override
		public void contextInitialized(ServletContextEvent sce) {
			ServletContext container = sce.getServletContext();

			final ServerContainer serverContainer = (ServerContainer) container.getAttribute(SERVER_CONTAINER_ATTRIBUTE);
			try {
				serverContainer.addEndpoint(new WebSocketEndopointConfig4Test(WebSocketEndpoint.class, WEBSOCKET_ENDPOINT));
				logger.info("REGISTERED WEBSOCKET ENDPOINT AT "+WEBSOCKET_ENDPOINT);
				//serverContainer.addEndpoint(new WebSocketEndopointConfig(AnnotatedWebSocketEndpoint.class,"ws2"));
				
			} catch (Exception e) {
				logger.error("Error WebSocket init", e);
			}
			Set<Extension> installedExtensions = serverContainer.getInstalledExtensions();
			logger.debug("Installed extensions: " + installedExtensions.size());
			
		}

		@Override
		public void contextDestroyed(ServletContextEvent sce) {
			// TODO Auto-generated method stub
			
		}
}
