package com.tooling4sensor.ide.evaluate;

import br.cin.ufpe.evaluationManager.client.ManagerClient;
import com.tooling4sensor.ide.IDE;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 *
 * @author avld
 */
@WebFilter( "/storage/*" )
public class EvaluateFilter implements Filter
{
    private ManagerClient client;
    private EditorServiceImpl service;
    private Thread thread;
    
    public EvaluateFilter()
    {
        service = new EditorServiceImpl();
    }
    
    @Override
    public void init( FilterConfig filterConfig ) throws ServletException
    {
        try
        {
            client = new ManagerClient( service );
            
            thread = new Thread( new Runnable()
                                 {
                                    @Override
                                    public void run()
                                    {
                                        connected();
                                    }
                                });
        }
        catch( Exception err )
        {
            err.printStackTrace();
            throw new ServletException( err );
        }
    }

    private void connected()
    {
        while( true )
        {
            try
            {
                client.conect( IDE.MANAGER_IP , IDE.MANAGER_PORT );
                break ;
            }
            catch( Exception err )
            {
                try
                {
                    Thread.sleep( 1000 );
                }
                catch( Exception err2 )
                {
                    // do nothing
                }
            }
        }
    }
    
    @Override
    public void doFilter( ServletRequest request
                        , ServletResponse response
                        , FilterChain chain ) throws IOException, ServletException
    {
        request.setAttribute( "EvaluatorManagerClient" , client );
        chain.doFilter( request , response );
    }

    @Override
    public void destroy()
    {
        client.finalize();
        
        thread.interrupt();
        thread = null;
        
        client  = null;
        service = null;
    }
    
}
