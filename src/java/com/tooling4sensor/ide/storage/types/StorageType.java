package com.tooling4sensor.ide.storage.types;

import com.tooling4sensor.ide.storage.StorageAccount;

/**
 * Esta interface represeenta todas as ações possiveis em um Storage
 * 
 * @author avld
 */
public interface StorageType
{
    // -------------------- Geral
    public long getId();
    public String getName();
    public void connect( StorageAccount storage ) throws Exception;
    public void desconnect() throws Exception;
    
    public StorageFile open( String path ) throws Exception;
    public void rename( String originalName , String newName ) throws Exception;
    public void delete( String path ) throws Exception;
    public void move( String pathOriginal , String pathNew ) throws Exception;
    
    // -------------------- File
    public void createFile( String path ) throws Exception;
    public byte[] getData( String path ) throws Exception;
    public void setData( String path , byte[] data ) throws Exception;
    
    // -------------------- Diretory
    public void createDir( String path ) throws Exception;
}
