package com.tooling4sensor.ide.storage.types;

/**
 *
 * @author avld
 */
public class StorageValidate
{
    
    public static void path( String path ) throws Exception
    {
        if( path == null ? true : path.isEmpty() )
        {
            throw new Exception( "Path is null or empty." );
        }
        else if( path.indexOf( "../" ) > -1 )
        {
            throw new Exception( "Path is illegal. It has '../'." );
        }
        else if( path.endsWith( "/.." ) )
        {
            throw new Exception( "Path is illegal. It end with '/..'." );
        }
    }
    
}
