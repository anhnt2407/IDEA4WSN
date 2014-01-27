package com.tooling4sensor.ide.storage.types.local;

import com.tooling4sensor.ide.storage.types.StorageFile;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta classe representa um arquivo no padrão StorageFile.
 * 
 * @author avld
 */
public class LocalStorageFile extends StorageFile
{
    private File file;          //Arquivo no servidor
    private String omit;        //Parte do caminho que deve ser omitido
    
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

    /**
     * retorna o caminho do arquivo, ocultando o endereço real na máquina.
     * 
     * @return 
     */
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

    /**
     * Retorna o caminho real da pasta ou arquivo.
     * 
     * @return 
     */
    public String getAbsolutePath()
    {
        return file.getAbsolutePath();
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
    
    @Override
    public Map<String,String> getProperties()
    {
        Map<String,String> map = new HashMap<>();
        map.put( "name" , file.getName() );
        map.put( "extension" , getExtension() );
        map.put( "size" , file.length() + "" );
        map.put( "Path" , getPath() );
        map.put( "Last Modification" , new Date( file.lastModified() ).toString() );
        
        return map;
    }
}
