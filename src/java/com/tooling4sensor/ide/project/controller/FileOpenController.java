package com.tooling4sensor.ide.project.controller;

import com.tooling4sensor.ide.fileViewType.FileViewFactory;
import com.tooling4sensor.ide.project.Project;
import com.tooling4sensor.ide.project.dao.FileOpennedDAO;
import com.tooling4sensor.ide.project.dao.ProjectDAO;
import com.tooling4sensor.ide.storage.Storage;
import com.tooling4sensor.ide.storage.dao.StorageDAO;
import com.tooling4sensor.ide.storage.types.StorageFactory;
import com.tooling4sensor.ide.storage.types.StorageFile;
import com.tooling4sensor.ide.storage.types.StorageType;
import com.tooling4sensor.ide.user.Account;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private ProjectDAO projectDAO;
    private StorageDAO storageDAO;
    private FileOpennedDAO fileDAO;
    
    @Autowired
    public FileOpenController( ProjectDAO p , StorageDAO s , FileOpennedDAO f )
    {
        this.projectDAO = p;
        this.storageDAO = s;
        this.fileDAO = f;
    }
    
    @RequestMapping( value = "/project/{id}/file" , method = RequestMethod.GET )
    public String open( @PathVariable Long id , String file , HttpServletRequest request, Model model ) throws Exception
    {
        if( file == null ? true : file.isEmpty() )
        {
            throw new Exception( "File is illegal, because it is null or empty." );
        }
        
        // ---------------------------- Abrir o Arquivo
        Account user = (Account) request.getSession().getAttribute( "user" );
        Project project = projectDAO.get( id , user.getUserId() );
        Storage storage = storageDAO.get( project.getStorageId() , user.getUserId() );
        
        StorageType type = StorageFactory.getInstance().get( storage.getType() );
        type.connect( storage );
        
        StorageFile sf = type.open( file );
        
        if( sf.isDirectory() )
        {
            throw new Exception( "It is a directory." );
        }
        
        // ---------------------------- TODO: Salvar no banco que o arquivo esta aberto
        
        // ---------------------------- Mostrar para o usuario o dado do arquivo
        String pathId = createDataId( file );
        
        model.addAttribute( "id" , "tab_" + pathId + "_data" );
        model.addAttribute( "path" , sf.getPath() );
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
