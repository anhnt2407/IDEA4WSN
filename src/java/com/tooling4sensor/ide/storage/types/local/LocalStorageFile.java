package com.tooling4sensor.ide.storage.types.local;

import com.tooling4sensor.ide.storage.types.StorageFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author avld
 */
public class LocalStorageFile extends StorageFile
{
    private File file;
    private String omit;
    
    public LocalStorageFile( File file , String omit )
    {
        this.file = file;
        this.omit = omit;
    }
    
    @Override
    public String getName()
    {
        return file.getName();
    }

    @Override
    public String getPath()
    {
        String path = file.getAbsolutePath();
        
        if( omit == null 
                ? false 
                : path.startsWith( omit ) )
        {
            return path.substring( omit.length() );
        }
        else
        {
            return path;
        }
    }

    @Override
    public boolean isDirectory()
    {
        return file.isDirectory();
    }

    @Override
    public boolean isFile()
    {
        return file.isFile();
    }

    @Override
    public boolean hasChildren()
    {
        String[] l = file.list();
        return l == null ? false : l.length > 0;
    }

    @Override
    public List<StorageFile> getChildren()
    {
        List<StorageFile> l = new ArrayList<>();
        for( File f : file.listFiles() )
        {
            StorageFile sf = new LocalStorageFile( f , omit );
            sf.setStorageType( getStorageType() );
            
            l.add( sf );
        }
        
        return l;
    }
    
}
