package com.tooling4sensor.ide.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author avld
 */
public class UserInterceptor extends HandlerInterceptorAdapter
{

    public UserInterceptor()
    {
        // do nothing
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request,
                              HttpServletResponse response,
                              Object controller) throws Exception
    {
        HttpSession session = request.getSession();
        
        String URI = request.getRequestURI();
        
        if( session.getAttribute( "user" ) != null )
        {
            return true;
        }
        else if( URI.startsWith( "/IDEA4WSN/login" ) 
                || URI.startsWith( "/IDEA4WSN/js" )
                || URI.startsWith( "/IDEA4WSN/css" )
                || URI.startsWith( "/IDEA4WSN/img" ) )
        {
            return true;
        }
        else
        {
            response.sendRedirect( "/IDEA4WSN/login" );
            return false;
        }
    }

}
