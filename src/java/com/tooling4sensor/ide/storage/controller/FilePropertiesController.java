package com.tooling4sensor.ide.storage.controller;

import com.tooling4sensor.ide.storage.StorageAccount;
import com.tooling4sensor.ide.storage.dao.StorageDAO;
import com.tooling4sensor.ide.storage.types.StorageFactory;
import com.tooling4sensor.ide.storage.types.StorageFile;
import com.tooling4sensor.ide.storage.types.StorageType;
import com.tooling4sensor.ide.user.Account;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author avld
 */
@Controller
public class FilePropertiesController 
{
    private StorageDAO storageDAO;
    
    @Autowired
    public FilePropertiesController( StorageDAO s )
    {
        this.storageDAO = s;
    }
    
    @RequestMapping( value = "/storage/{id}/file/properties" )
    public String open( @PathVariable Long id , String path , HttpServletRequest request , Model model ) throws Exception
    {
        if( path == null ? true : path.isEmpty() )
        {
            throw new Exception( "Path is illegal, because it is null or empty." );
        }
        
        Account user = (Account) request.getSession().getAttribute( "user" );
        StorageAccount storage = storageDAO.get( id , user.getUserId() );
        
        StorageType type = StorageFactory.getInstance().get( storage.getType() );
        type.connect( storage );
        StorageFile f = type.open( path );
        
        model.addAttribute( "propertiesMap" , f.getProperties() );
        return "project/fileProperties";
    }
    
}
