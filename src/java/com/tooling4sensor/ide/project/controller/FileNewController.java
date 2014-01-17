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
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author avld
 */
@Controller
public class FileNewController
{
    private StorageDAO storageDAO;
    
    @Autowired
    public FileNewController( StorageDAO s )
    {
        this.storageDAO = s;
    }
    
    @RequestMapping( value = "/storage/{id}/file/new" , method = RequestMethod.POST )
    public void open( @PathVariable Long id , String path , String data , boolean dir , HttpServletRequest request , HttpServletResponse response ) throws Exception
    {
        if( path == null ? true : path.isEmpty() )
        {
            throw new Exception( "File is illegal, because it is null or empty." );
        }
        
        // ---------------------------- Abrir o Arquivo
        Account user = (Account) request.getSession().getAttribute( "user" );
        StorageAccount storage = storageDAO.get( id , user.getUserId() );
        
        StorageType type = StorageFactory.getInstance().get( storage.getType() );
        type.connect( storage );
        
        if( dir )
        {
            type.createDir( path );
        }
        else
        {
            type.createFile( path );
            type.setData( path , data );
        }
        
        // ---------------------------- retorna para o usuário que deu tudo certo
        response.setStatus( HttpServletResponse.SC_OK );
    }
    
}
