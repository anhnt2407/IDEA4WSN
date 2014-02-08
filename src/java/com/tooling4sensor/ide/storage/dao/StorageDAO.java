package com.tooling4sensor.ide.storage.dao;

import com.tooling4sensor.ide.storage.StorageAccount;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author avld
 */
@Repository
public class StorageDAO
{
    private EntityManagerFactory factory;
    
    public StorageDAO()
    {
        factory = Persistence.createEntityManagerFactory( "idea4wsn" );
    }
    
    public long add( StorageAccount storage )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            manager.getTransaction().begin();    
            manager.persist( storage );
            manager.getTransaction().commit();
        }
        finally
        {
            manager.close();
        }
        
        return storage.getUserId();
    }
    
    public void modify( StorageAccount storage )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            manager.getTransaction().begin();    
            manager.merge( storage );
            manager.getTransaction().commit();
        }
        finally
        {
            manager.close();
        }
    }
    
    public void delete( long storageId , long userId ) throws Exception
    {
        StorageAccount storage = get( storageId , userId ); // A Logica nao deveria esta aqui!
        
        if( storage == null )
        {
            return ;
        }
        
        EntityManager manager = factory.createEntityManager();

        try
        {
            manager.getTransaction().begin();    
            manager.remove( storage );
            manager.getTransaction().commit();
        }
        finally
        {
            manager.close();
        }
    }
    
    public StorageAccount get( long storageId , long userId ) throws Exception
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            StorageAccount p = manager.find( StorageAccount.class, storageId );
           
            if( p == null )
            {
                throw new Exception( "Storage Account didn't find." );
            }
            else if( p.getUserId() != userId )
            {
                throw new Exception( "this Storage Account isn't yours." );
            }
            
           
            return p;
        }
        finally
        {
            manager.close();
        }
    }
    
    public List<StorageAccount> list( long userId )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            String sql = "SELECT * FROM idea4wsn.Storage WHERE userid = " + userId + " LIMIT 1000";
            
            Query query = manager.createNativeQuery( sql , StorageAccount.class );
            return query.getResultList();
        }
        finally
        {
            manager.close();
        }
    }
    
}
