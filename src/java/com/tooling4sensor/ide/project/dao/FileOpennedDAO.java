package com.tooling4sensor.ide.project.dao;

import com.tooling4sensor.ide.project.FileOpenned;
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
public class FileOpennedDAO
{
    private EntityManagerFactory factory;
    
    public FileOpennedDAO()
    {
        factory = Persistence.createEntityManagerFactory( "idea4wsn" );
    }
    
    public long add( FileOpenned fo )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            manager.getTransaction().begin();    
            manager.persist( fo );
            manager.getTransaction().commit();
        }
        finally
        {
            manager.close();
        }
        
        return fo.getFileOpennedId();
    }
    
    public void delete( FileOpenned fo )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            manager.getTransaction().begin();    
            manager.remove( fo );
            manager.getTransaction().commit();
        }
        finally
        {
            manager.close();
        }
    }
    
    public List<FileOpenned> list( long projectId )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            String sql = "SELECT * FROM idea4wsn.Project_file_openned WHERE project_id = " + projectId + " LIMIT 1000";
            
            Query query = manager.createNativeQuery( sql , FileOpenned.class );
            return query.getResultList();
        }
        finally
        {
            manager.close();
        }
    }
    
}
