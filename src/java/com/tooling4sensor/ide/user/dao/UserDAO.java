package com.tooling4sensor.ide.user.dao;

import com.tooling4sensor.ide.user.Account;
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
public class UserDAO
{
    private EntityManagerFactory factory;
    
    public UserDAO()
    {
        factory = Persistence.createEntityManagerFactory( "idea4wsn" );
    }
    
    public long add( Account user )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            manager.getTransaction().begin();    
            manager.persist( user );
            manager.getTransaction().commit();
        }
        finally
        {
            manager.close();
        }
        
        return user.getUserId();
    }
    
    public void modify( Account user )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            manager.getTransaction().begin();    
            manager.merge( user );
            manager.getTransaction().commit();
        }
        finally
        {
            manager.close();
        }
    }
    
    public Account login( String email , String pwd )
    {
        EntityManager manager = factory.createEntityManager();

        try
        {
            String sql = "SELECT * FROM idea4wsn.Account WHERE email = ? AND password = ?";
            Query query = manager.createNativeQuery( sql , Account.class );
            query.setParameter( 1 , email );
            query.setParameter( 2 , pwd   );
            
            List<Account> list = query.getResultList();
            
            if( list.isEmpty() )
            {
                return null;
            }
            else
            {
                return list.get( 0 );
            }
        }
        finally
        {
            manager.close();
        }
    }
    
}
