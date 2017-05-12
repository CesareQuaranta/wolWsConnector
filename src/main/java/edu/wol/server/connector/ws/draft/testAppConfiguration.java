package edu.wol.server.connector.ws.draft;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
/*
 * Date 28/apr/2017
 * @Author Cesare Quaranta
 * 
 * Web App initializzer sperimentale
 * Scopo: nella fase di test come war questa classe si occupa di effettuare lo startup dell'applicazione configurare le servlet del pacchetto wol.server attraverso le annotation
 */
public class testAppConfiguration{ //implements WebApplicationInitializer {
/*
	@Override
	public void onStartup(ServletContext context) throws ServletException {
		final AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();

        root.scan("wol.server");

        context.addListener(new ContextLoaderListener(root));

        final ServletRegistration.Dynamic appServlet = context.addServlet(
            "appServlet",
            new DispatcherServlet(new GenericWebApplicationContext()));
        appServlet.setAsyncSupported(true);
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/*");
	}*/

}
