package com.tooling4sensor.ide.storage.controller;

import com.tooling4sensor.ide.fileViewType.FileViewFactory;
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
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author avld
 */
@Controller
public class FileOpenController
{
    private StorageDAO storageDAO;
    
    @Autowired
    public FileOpenController( StorageDAO s )
    {
        this.storageDAO = s;
    }
    
    @RequestMapping( value = "/storage/{id}/file" , method = RequestMethod.GET )
    public String open( @PathVariable Long id , String path , HttpServletRequest request, Model model ) throws Exception
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
        
        StorageFile sf = type.open( path );
        
        if( sf.isDirectory() )
        {
            throw new Exception( "It is a directory." );
        }
        
        // ---------------------------- Mostrar para o usuario o dado do arquivo
        String pathId = createDataId( path );
        
        model.addAttribute( "id" , "tab_" + pathId + "_data" );
        model.addAttribute( "path" , sf.getPath() );
        model.addAttribute( "storage" , id );
        
        return FileViewFactory.getInstance().process( sf , model );
    }
    
    private String createDataId( String path )
    {
        String id = "";
        
        for( Character c : path.toCharArray() )
        {
            if( c == '/' )
            {
                id += "dDd";
            }
            else if( c == '.' )
            {
                id += "eEe";
            }
            else if( Character.isLetterOrDigit( c ) )
            {
                id += c;
            }
        }
        
        return id;
    }
}
