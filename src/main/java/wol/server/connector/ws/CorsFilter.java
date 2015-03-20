package wol.server.connector.ws;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "CorsFilter",
urlPatterns = {"/*"},
dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD,DispatcherType.ASYNC,DispatcherType.INCLUDE},
//servletNames = {"AtmosphereServlet"},
asyncSupported = true,
initParams = {
    @WebInitParam(name = "mood", value = "awake")})
public class CorsFilter implements Filter{
	 private FilterConfig config = null;
	 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {      
        HttpServletRequest req = (HttpServletRequest)request;                                   
        HttpServletResponse res = (HttpServletResponse)response;  

        if(req.getHeader("Origin") != null){
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Expose-Headers", "X-Cache-Date, X-Atmosphere-tracking-id");
        }

        if("OPTIONS".equals(req.getMethod())){
            res.addHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST");
            res.addHeader("Access-Control-Allow-Headers",
                    "Origin, Content-Type, X-Atmosphere-Framework, X-Cache-Date, X-Atmosphere-tracking-id, X-Atmosphere-Transport");
            res.addHeader("Access-Control-Max-Age", "-1");
        }                                              
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() { 
    	if(this.config!=null){
    	 config.getServletContext().log("Destroying CORS Filter");
    	 }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { 
    	this.config = config;
    	
    	if(this.config!=null){
    		 config.getServletContext().log("Initializing CORS Filter for develop purpose");
    	}
       
    }   
} 
