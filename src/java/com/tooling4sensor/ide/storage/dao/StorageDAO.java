package com.tooling4sensor.ide.storage.dao;

import com.tooling4sensor.ide.storage.Storage;
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
    
    public long add( Storage storage )
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
    
    public void modify( Storage storage )
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
    
    public void delete( long storageId , long userId )
    {
        Storage storage = get( storageId , userId ); // A Logica nao deveria esta aqui!
        
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
    
    public Storage get( long projectId , long userId )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            Storage p = manager.find( Storage.class, projectId );
            
            if( p.getUserId() != userId )
            {
                return null;
            }
            
            return p;
        }
        finally
        {
            manager.close();
        }
    }
    
    public List<Storage> list( long userId )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            String sql = "SELECT * FROM idea4wsn.Storage WHERE userid = " + userId;
            
            Query query = manager.createNativeQuery( sql , Storage.class );
            return query.getResultList();
        }
        finally
        {
            manager.close();
        }
    }
    
}
