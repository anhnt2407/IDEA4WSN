package com.tooling4sensor.ide.project.dao;

import com.tooling4sensor.ide.project.Project;
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
public class ProjectDAO
{
    private EntityManagerFactory factory;
    
    public ProjectDAO()
    {
        factory = Persistence.createEntityManagerFactory( "idea4wsn" );
    }
    
    public long add( Project project )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            manager.getTransaction().begin();
            manager.persist( project );
            manager.getTransaction().commit();
        }
        finally
        {
            manager.close();
        }
        
        return project.getUserId();
    }
    
    public void modify( Project project )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            manager.getTransaction().begin();    
            manager.merge( project );
            manager.getTransaction().commit();
        }
        finally
        {
            manager.close();
        }
    }
    
    public void delete( long projectId , long userId )
    {
        Project project = get( projectId , userId );
        
        if( project == null )
        {
            return ;
        }
        
        EntityManager manager = factory.createEntityManager();

        try
        {
            manager.getTransaction().begin();    
            manager.remove( project );
            manager.getTransaction().commit();
        }
        finally
        {
            manager.close();
        }
    }
    
    public Project get( long projectId , long userId )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            Project p = manager.find( Project.class, projectId );
            
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
    
    public List<Project> list( long userId )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            String sql = "SELECT * FROM idea4wsn.Project WHERE userid = " + userId;
            
            Query query = manager.createNativeQuery( sql , Project.class );
            return query.getResultList();
        }
        finally
        {
            manager.close();
        }
    }
    
}
