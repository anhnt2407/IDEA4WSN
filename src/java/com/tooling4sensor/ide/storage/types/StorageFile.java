package com.tooling4sensor.ide.storage.types;

import java.util.List;

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
            return type.getFile( getPath() );
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
                || ( name != null ? true : name.isEmpty() ) )
        {
            return "";
        }
        else
        {
            int pos = name.lastIndexOf( "." );
            return name.substring( pos );
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
    
    public abstract List<StorageFile> getChildren();
}
