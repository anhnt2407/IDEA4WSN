package com.tooling4sensor.ide.storage.controller;

import com.tooling4sensor.ide.storage.StorageAccount;
import com.tooling4sensor.ide.storage.dao.StorageDAO;
import com.tooling4sensor.ide.storage.types.StorageFactory;
import com.tooling4sensor.ide.storage.types.StorageFile;
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
public class FileMoveController
{
    private StorageDAO storageDAO;
    
    @Autowired
    public FileMoveController( StorageDAO s )
    {
        this.storageDAO = s;
    }
    
    @RequestMapping( value = "/storage/{id}/file/move" )
    public void open( @PathVariable Long id , String pathOld, String pathNew , int type , HttpServletRequest request, HttpServletResponse response ) throws Exception
    {
        if( pathOld == null ? true : pathOld.isEmpty() )
        {
            throw new Exception( "Path Old is illegal, because it is null or empty." );
        }
        else if( pathNew == null ? true : pathNew.isEmpty() )
        {
            throw new Exception( "Path New is illegal, because it is null or empty." );
        }
        
        // ---------------------------- Abrir o Arquivo
        Account user = (Account) request.getSession().getAttribute( "user" );
        StorageAccount storage = storageDAO.get( id , user.getUserId() );
        
        StorageType stType = StorageFactory.getInstance().get( storage.getType() );
        stType.connect( storage );
        
        StorageFile sf = stType.open( pathOld );
        
        if( type == 1 ) // copy
        {
            stType.setData( pathNew , sf.getData() );
        }
        else            // cut
        {
            stType.move( pathOld , pathNew );
        }
        
        response.setStatus( HttpServletResponse.SC_OK );
    }
    
}
