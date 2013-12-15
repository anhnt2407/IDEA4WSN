package com.tooling4sensor.ide.storage.types;

import com.tooling4sensor.ide.storage.types.local.LocalStorageType;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author avld
 */
public class StorageFactory
{
    private static StorageFactory isntance;
    private Map<Long,StorageType> typeMap;
    
    private StorageFactory()
    {
        init();
    }
    
    private void init()
    {
        typeMap = new HashMap<>();
        
        addStorageType( new LocalStorageType() );
    }
    
    public void addStorageType( StorageType type )
    {
        typeMap.put( type.getId() , type );
    }
    
    public StorageType get( long storageTypeId )
    {
        return typeMap.get( storageTypeId );
    }
    
    // -----------------------
    // -----------------------
    // -----------------------
    
    public static StorageFactory getInstance()
    {
        if( isntance == null )
        {
            isntance = new StorageFactory();
        }
        
        return isntance;
    }
    
}
