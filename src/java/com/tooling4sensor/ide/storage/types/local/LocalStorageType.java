package com.tooling4sensor.ide.storage.types.local;

import com.tooling4sensor.ide.storage.Storage;
import com.tooling4sensor.ide.storage.types.StorageFile;
import com.tooling4sensor.ide.storage.types.StorageType;
import com.tooling4sensor.ide.storage.types.StorageValidate;
import java.io.File;
import java.io.FileInputStream;

/**
 *
 * @author avld
 */
public class LocalStorageType implements StorageType
{
    public static String PATH = "/opt/idea4wsn/";
    private Storage storage;
    private File rootDir;
    
    public LocalStorageType()
    {
        // do nothing
    }
    
    @Override
    public long getId()
    {
        return 1;
    }

    @Override
    public String getName()
    {
        return "Local";
    }

    @Override
    public void connect( Storage storage ) throws Exception
    {
        if( storage == null )
        {
            throw new Exception( "Storage is null." );
        }
        else if( storage.getUserId() < 1 )
        {
            throw new Exception( "User is not valid." );
        }
        else if( storage.getType() != getId() )
        {
            throw new Exception( "Type is different." );
        }
        
        this.storage = storage;
        
        rootDir = new File( PATH + this.storage.getUserId() );
        
        if( !rootDir.exists() )
        {
            rootDir.mkdirs();
        }
    }

    @Override
    public void desconnect() throws Exception
    {
        this.storage = null;
        this.rootDir = null;
    }

    // -----------------------------
    
    @Override
    public StorageFile open( String path ) throws Exception
    {
        File f = file( path );
        
        if( !f.exists() )
        {
            throw new Exception( "This file/directory doesn't exist." );
        }
        
        StorageFile sf = new LocalStorageFile( f , rootDir.getPath() );
        sf.setStorageType( this );
        
        return sf;
    }

    @Override
    public void rename( String path , String newName ) throws Exception
    {
        File f = file( path );
        f.renameTo( new File( f.getParent() + "/" + newName ) );
        
        f = null;
    }

    @Override
    public void delete( String path ) throws Exception
    {
        File f = file( path );
        delete( f );
        
        f = null;
    }
    
    private void delete( File f ) throws Exception
    {
        if( f.isDirectory() )
        {
            deleteDir( f );
        }
        else
        {
            deleteFile( f );
        }
    }
    
    @Override
    public void move( String pathOriginal , String pathNew ) throws Exception
    {
        StorageValidate.path( pathOriginal );
        StorageValidate.path( pathNew );
        
        File f = file( pathOriginal );
        f.renameTo( file( pathNew ) );
        
        f = null;
    }
    
    // -------------------------
    // -------------------------  FILE
    // -------------------------
    
    @Override
    public void createFile( String path ) throws Exception
    {
        File f = file( path );
        f.createNewFile();
        
        f = null;
    }

    @Override
    public byte[] getFile( String path ) throws Exception
    {
        File f = file( path );
        
        if( f.isDirectory() )
        {
            throw new Exception( "It is a directory." );
        }
        else if( f.length() > 1024 * 1024 * 10 ) // (10 Mb)
        {
            throw new Exception( "This file size is more than 10 Mb." );
        }
        
        byte[] data = new byte[ (int) f.length() ];
        
        FileInputStream fos = new FileInputStream( f );
        int size = fos.read( data );
        
        return data;
    }

    private void deleteFile( File f ) throws Exception
    {
        f.delete();
    }

    // -------------------------
    // -------------------------  DIRECTORY
    // -------------------------
    
    @Override
    public void createDir( String path ) throws Exception
    {
        File f = file( path );
        f.mkdirs();
        
        f = null;
    }

    private void deleteDir( File dir ) throws Exception
    {
        if( dir.list() == null ? false : dir.list().length > 0 )
        {
            for( File f : dir.listFiles() )
            {
                delete( f );
            }
        }
        
        dir.delete();
    }
    
    // ----------------------------
    
    private File file( String path ) throws Exception
    {
        StorageValidate.path( path );
        
        if( storage == null )
        {
            throw new Exception( "It is necessary connect first." );
        }
        
        String f = path.startsWith( rootDir.getPath() ) 
                        ? path 
                        : rootDir.getPath() + path;
        
        return new File( f );
    }
    
}
