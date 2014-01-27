package com.tooling4sensor.ide.storage.controller;

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
public class FileSaveController
{
    private StorageDAO storageDAO;
    
    @Autowired
    public FileSaveController( StorageDAO s )
    {
        this.storageDAO = s;
    }
    
    @RequestMapping( value = "/storage/{id}/file/save" , method = RequestMethod.POST )
    public void open( @PathVariable Long id , String file , String data , HttpServletRequest request , HttpServletResponse response ) throws Exception
    {
        if( file == null ? true : file.isEmpty() )
        {
            throw new Exception( "File is illegal, because it is null or empty." );
        }
        
        // ---------------------------- Abrir o Arquivo
        Account user = (Account) request.getSession().getAttribute( "user" );
        StorageAccount storage = storageDAO.get( id , user.getUserId() );
        
        StorageType type = StorageFactory.getInstance().get( storage.getType() );
        type.connect( storage );
        type.setData( file , data.getBytes() );
        
        // ---------------------------- retorna para o usuário que deu tudo certo
        response.setStatus( HttpServletResponse.SC_OK );
    }
    
}
