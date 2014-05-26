package com.tooling4sensor.ide;

import br.cin.ufpe.evaluationManager.remote.ManagerServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
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
@WebFilter( "/*" )
public class IDE implements Filter
{
    private static final String CONF_FILENAME = "{ContextPath}/WEB-INF/configuration.ini";
    
    public static String PROJECT_PATH = "/opt/idea4wsn/";       //Diretório onde todos os arquivos estão.
    public static String MANAGER_IP   = "127.0.0.1";
    public static int    MANAGER_PORT = ManagerServer.PORT;

    private File file;
    private Properties properties;
    
    public IDE()
    {
        // do nothing
    }
    
    @Override
    public void init( FilterConfig filterConfig ) throws ServletException
    {
        try
        {
            String realPath = filterConfig.getServletContext().getRealPath( "/" );
            String path     = CONF_FILENAME.replace( "{ContextPath}" , realPath );
            
            file = new File( path );
            init();
        }
        catch( Exception err )
        {
            err.printStackTrace();
            throw new ServletException( err );
        }
    }

    private void init() throws Exception
    {
        if( !file.exists() )
        {
            file.createNewFile();
        }
        
        open();
        setDefaultValues();
    }
    
    private Properties open() throws Exception
    {
        properties = new Properties();
        
        FileInputStream input = new FileInputStream( file );
        properties.load( input );
        input.close();
        input = null;
        
        return properties;
    }
    
    private void setDefaultValues() throws Exception
    {
        String ip   = properties.getProperty( "manager_ip"   , MANAGER_IP   );
        String port = properties.getProperty( "manager_port" , MANAGER_PORT + "" );
        String path = properties.getProperty( "project_path" , PROJECT_PATH );
        
        // -------------------------------- SET THE PROPERTIES
        properties.setProperty( "manager_ip"   , ip   );
        properties.setProperty( "manager_port" , port );
        properties.setProperty( "project_path" , path );
        
        // -------------------------------- SET THE CONFIGURATION
        MANAGER_IP   = ip;
        MANAGER_PORT = Integer.parseInt( port );
        PROJECT_PATH = path;
        
        // -------------------------------- SAVE THE PROPERTIES
        save( properties );
    }
    
    private void save( Properties p ) throws Exception
    {
        FileOutputStream output = new FileOutputStream( file );
        p.store( output , "" );
        output.close();
    }
    
    @Override
    public void doFilter( ServletRequest request
                        , ServletResponse response
                        , FilterChain chain) throws IOException, ServletException
    {
        chain.doFilter( request , response );
    }

    @Override
    public void destroy()
    {
        try
        {
            properties.setProperty( "manager_ip"   , MANAGER_IP   );
            properties.setProperty( "manager_port" , MANAGER_PORT + "" );
            properties.setProperty( "project_path" , PROJECT_PATH );
            
            save( properties );
        }
        catch( Exception ignore )
        {
            // do nothing
        }
    }
    
    
    
}
