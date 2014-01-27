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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author avld
 */
@Controller
public class FileUploadController
{
    private StorageDAO storageDAO;
    
    @Autowired
    public FileUploadController( StorageDAO s )
    {
        this.storageDAO = s;
    }
    
    @RequestMapping( value = "/storage/{id}/file/upload" , method = RequestMethod.GET )
    public String upload( @PathVariable Long id , String path 
                        , HttpServletRequest request, Model model ) throws Exception
    {
        if( path == null ? true : path.isEmpty() )
        {
            throw new Exception( "Path is illegal, because it is null or empty." );
        }
        
        // ---------------------------- Abrir o Arquivo
        Account user = (Account) request.getSession().getAttribute( "user" );
        StorageAccount storage = storageDAO.get( id , user.getUserId() );
        
        StorageType type = StorageFactory.getInstance().get( storage.getType() );
        type.connect( storage );
        
        StorageFile sf = type.open( path );
        
        if( !sf.isDirectory() )
        {
            throw new Exception( "It isn't a directory." );
        }
        
        // ---------------------------- Mostrar para o usuario o dado do arquivo
        
        model.addAttribute( "path" , sf.getPath() );
        model.addAttribute( "storage_id" , id );
        return "project/fileUpload";
    }
    
    @RequestMapping( value = "/storage/{id}/file/upload" , method = RequestMethod.POST )
    public void save( @PathVariable Long id , String path , MultipartFile file
                    , HttpServletRequest request , HttpServletResponse response ) throws Exception
    {
        if( path == null ? true : path.isEmpty() )
        {
            throw new Exception( "Path is illegal, because it is null or empty." );
        }
        
        // ---------------------------- Abrir o Arquivo
        Account user = (Account) request.getSession().getAttribute( "user" );
        StorageAccount storage = storageDAO.get( id , user.getUserId() );
        
        StorageType type = StorageFactory.getInstance().get( storage.getType() );
        type.connect( storage );
        
        StorageFile sf = type.open( path );
        
        if( !sf.isDirectory() )
        {
            throw new Exception( "It isn't a directory." );
        }
        
        //Salva o arquivo com o mesmo nome!
        type.setData( path + "/" + file.getOriginalFilename() , file.getBytes() );
        
        //Responde ao usuário que ocorreu tudo certo!
        response.setStatus( HttpServletResponse.SC_OK );
    }
}
