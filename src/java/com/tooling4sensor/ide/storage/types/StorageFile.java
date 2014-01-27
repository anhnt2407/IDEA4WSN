package com.tooling4sensor.ide.storage.types;

import java.util.List;
import java.util.Map;

/**
 *
 * @author avld
 */
public abstract class StorageFile
{
    private StorageType type;
    
    public StorageFile()
    {
        // do nothing
    }
    
    public StorageType getStorageType()
    {
        return type;
    }
    
    public void setStorageType( StorageType type )
    {
        this.type = type;
    }
    
    public byte[] getData() throws Exception
    {
        if( isFile() )
        {
            return type.getData( getPath() );
        }
        else
        {
            throw new Exception( "It is a directory." );
        }
    }
    
    public void setData( byte[] data ) throws Exception
    {
        if( isFile() )
        {
            type.setData( getPath() , data );
        }
        else
        {
            throw new Exception( "It is a directory." );
        }
    }
    
    public String getExtension()
    {
        String name = getName();
        
        if( isDirectory() 
                || ( name == null ? true : name.isEmpty() ) )
        {
            return "";
        }
        else
        {
            int pos = name.lastIndexOf( "." );
            return name.substring( pos + 1 );
        }
    }
    
    // ----------------------
    // ----------------------
    // ----------------------
    
    public abstract String getName();
    public abstract String getPath();
    
    public abstract boolean isDirectory();
    public abstract boolean isFile();
    public abstract boolean hasChildren();
    
    public abstract Map<String,String> getProperties();
    
    public abstract List<StorageFile> getChildren();
}
