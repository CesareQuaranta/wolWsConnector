package edu.wol.server.connector.ws;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.Session;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.glassfish.tyrus.client.ThreadPoolConfig;
import org.glassfish.tyrus.container.jdk.client.JdkClientContainer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes={ContextTest.class})
@ContextConfiguration(classes={ContextTest.class})//loader=AnnotationConfigContextLoader.class,
//@ContextConfiguration(initializers = {TestAppContextInitializer.class})
@WebAppConfiguration
public class TestWebsocket {
	private static final String TEST_CONTEXT = "/websocket-test";
	private static final int SERVER_PORT = 8181;
	private static ClientEndpointConfig cec = null;
    private static ClientManager client = null;
    private static ThreadPoolConfig workerThreadPoolConfig = null;
    private static URI TestURI = null;
    
	private static Server jettyServer;

	private CountDownLatch messageLatch;
	@Autowired
    private static WebApplicationContext wac;
   
    
    @BeforeClass
	public static void globalSetup() throws Exception {
		System.out.println("Setup");

		setupWebsocketClient();
		setupJettyServlet();
		
		/*MockServletContext sc = new MockServletContext("");
        ServletContextListener listener = new ContextLoaderListener(wac);
        ServletContextEvent event = new ServletContextEvent(sc);
        listener.contextInitialized(event);*/
		TestURI = new URI("ws://localhost:"+SERVER_PORT+TEST_CONTEXT+WebSocketInitializer.WEBSOCKET_ENDPOINT);
		System.out.println("TestURI: "+TestURI);

	}
    
    private static void setupJettyServlet() throws Exception {
		
		// Webserver setup
		jettyServer = new Server(SERVER_PORT);
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath(TEST_CONTEXT);
		//context.setAttribute("org.eclipse.jetty.server.webapp.WebInfIncludeJarPattern", ".*/spring-security[^/]*\\.jar$|.*/spring-web[^/]*\\.jar$|.*/wol[^/]*\\.jar$|.*/classes/.*");
		
		String currentDir = System.getProperty("user.dir");
		System.out.println("Working Directory = " + currentDir);
		context.setBaseResource(Resource.newResource(currentDir + "/src/test/resources"));
		context.setInitParameter("contextConfigLocation", "context.xml");
		
		context.addEventListener(new ContextLoaderListener(wac));
	
		jettyServer.setHandler(context);
		context.addEventListener(new WebSocketInitializer());
		WebSocketServerContainerInitializer.configureContext(context);
		jettyServer.start();
	}
    
    private static void setupWebsocketClient() {
		// Websocket client setup
		cec = ClientEndpointConfig.Builder.create().build();
		client = ClientManager.createClient(JdkClientContainer.class.getName());
		
		workerThreadPoolConfig = ThreadPoolConfig.defaultConfig();					
		workerThreadPoolConfig.setDaemon(false);
		workerThreadPoolConfig.setMaxPoolSize(4);
		workerThreadPoolConfig.setCorePoolSize(3);

		client.getProperties().put(ClientProperties.SHARED_CONTAINER, false);
		client.getProperties().put(ClientProperties.WORKER_THREAD_POOL_CONFIG, workerThreadPoolConfig);
	}
   
	private  Session openNewSession(Endpoint endpoint) throws DeploymentException, IOException {
		  return client.connectToServer(endpoint, cec, TestURI);
	  }
    
    @Test
	 public void testConnection() throws DeploymentException, IOException, URISyntaxException, InterruptedException {
    	System.out.println("Test Connection "+TestURI.toString());

			messageLatch = new CountDownLatch(1);
			Endpoint endpoint =new MessageLatchTestClientEndpoint(messageLatch);
			try {
				client.connectToServer(endpoint, cec, TestURI);
			}catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		    boolean mesageReceivedByClient = messageLatch.await(30, TimeUnit.SECONDS);
		    Assert.assertTrue("Time lapsed before message was received by client.", mesageReceivedByClient);
	 }
    
    @Test
	 public void testStartSessionEndpoint() throws DeploymentException, IOException, URISyntaxException, InterruptedException {
			System.out.println("Test Endpoint");
			ClientEndpoint4Test endpoint =new ClientEndpoint4Test();
			openNewSession(endpoint);
			endpoint.send("xToken:DuyniivSfVj6vEddcxRTIp1ddZmBDBjIcEV0LlX3ao3J/XuYkTuwX/8DfZFGfzPXpG5grn4TP/91Gj3HUpHzie52WlUV5bj6q2NAdx2n04sJrcJy1SUwEa28DhlRqaQSSX6RhwCVNIFozYQmgOUcohnguf58CMrUoiezRkJnHTxIU69sUvTMKZcSeWpI2QsS9c9gZPUZOhcJ2TFvsKWqi2GVacd5538vIcSKcHxqeoJrcuVe2+l5s5Pk+pz88KFS5G4VhEhv7rt75tvCT6x5Yvu2BRebcpOy5coX//Y171Xn9MZZbE7xoj3rPENREs3bKCBYV8yCaogxATiEllOcBw==");
			if(endpoint.messages.size()==0){
				synchronized (endpoint.messages){
					endpoint.messages.wait(3000);
				}
			}
			Assert.assertTrue("Messaggio non ricevuto", endpoint.messages.size()==1);
	 }
}
