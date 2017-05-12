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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.ContextLoaderListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestContextConfiguration.class})
@WebAppConfiguration
//@ContextConfiguration(initializers = {TestAppContextInitializer.class})
public class WebsocketClientTest {
	private static Server jettyServer;
	private CountDownLatch messageLatch;
    private static final String TEST_CONTEXT = "/websocket-test";
    private static final int SERVER_PORT = 8080;
    private static ClientEndpointConfig cec = null;
    private static ClientManager client = null;
    private static ThreadPoolConfig workerThreadPoolConfig = null;
    private static URI TestURI = null;
    
    @BeforeClass
	public static void globalSetup() throws Exception {
		System.out.println("Setup");

		setupWebsocketClient();
		setupJettyServlet();
		
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
		
		context.addEventListener(new ContextLoaderListener());
	
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
			System.out.println("Test Connection");

			messageLatch = new CountDownLatch(1);
			Endpoint endpoint =new MessageLatchTestClientEndpoint(messageLatch);
			try {
				//client.connectToServer(endpoint, cec, TestURI);
			}catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		    //boolean mesageReceivedByClient = messageLatch.await(30, TimeUnit.SECONDS);
		    //Assert.assertTrue("Time lapsed before message was received by client.", mesageReceivedByClient);
	 }
    
    @Test
	 public void testEndpoint() throws DeploymentException, IOException, URISyntaxException, InterruptedException {
			System.out.println("Test Endpoint");
			TestClientEndpoint endpoint =new TestClientEndpoint();
			//openNewSession(endpoint);
			//endpoint.send("xToken");
	 }
}
