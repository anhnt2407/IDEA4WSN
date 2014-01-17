package com.tooling4sensor.ide.project.controller;

import com.tooling4sensor.ide.storage.StorageAccount;
import com.tooling4sensor.ide.storage.dao.StorageDAO;
import com.tooling4sensor.ide.storage.types.StorageFactory;
import com.tooling4sensor.ide.storage.types.StorageType;
import com.tooling4sensor.ide.user.Account;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author avld
 */
@Controller
public class FileRenameController 
{
    private StorageDAO storageDAO;
    
    @Autowired
    public FileRenameController( StorageDAO s )
    {
        this.storageDAO = s;
    }
    
    @RequestMapping( value = "/storage/{id}/file/rename" )
    public void open( @PathVariable Long id , String path , String name , HttpServletRequest request, HttpServletResponse response ) throws Exception
    {
        if( path == null ? true : path.isEmpty() )
        {
            throw new Exception( "Path is illegal, because it is null or empty." );
        }
        else if( name == null ? true : name.isEmpty() )
        {
            throw new Exception( "The New Name is illegal, because it is null or empty." );
        }
        
        Account user = (Account) request.getSession().getAttribute( "user" );
        StorageAccount storage = storageDAO.get( id , user.getUserId() );
        
        StorageType type = StorageFactory.getInstance().get( storage.getType() );
        type.connect( storage );
        type.rename( path , name );
        
        response.setStatus( HttpServletResponse.SC_OK );
        
        System.out.println( "Renomeou com sucesso." );
    }
    
}
