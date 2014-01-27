package com.tooling4sensor.ide.storage.controller;

import com.tooling4sensor.ide.storage.StorageAccount;
import com.tooling4sensor.ide.storage.dao.StorageDAO;
import com.tooling4sensor.ide.storage.types.StorageFactory;
import com.tooling4sensor.ide.storage.types.StorageFile;
import com.tooling4sensor.ide.storage.types.StorageType;
import com.tooling4sensor.ide.user.Account;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author avld
 */
@Controller
public class FileDownloadController
{
    private StorageDAO storageDAO;
    
    @Autowired
    public FileDownloadController( StorageDAO s )
    {
        this.storageDAO = s;
    }
    
    @RequestMapping( value = "/storage/{id}/file/download" , method = RequestMethod.GET )
    public HttpEntity<byte[]> open( @PathVariable Long id , String path , HttpServletRequest request ) throws Exception
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
        
        // ---------------------------- download
        byte[] documentBody = sf.getData();
        String name = sf.getName().replace( " " , "_" );

        HttpHeaders header = new HttpHeaders();
        header.setContentType( getMediaType( sf ) );
        header.set("Content-Disposition", "attachment; filename=" + name );
        header.setContentLength( documentBody.length );

        //TODO: quando for uma pasta, deve zipa-la!
        
        return new HttpEntity<byte[]>(documentBody, header);
    }
    
    private MediaType getMediaType( StorageFile sf )
    {
        if( "png".equalsIgnoreCase( sf.getExtension() ) )
        {
            return MediaType.IMAGE_PNG;
        }
        else if( "jpg".equalsIgnoreCase( sf.getExtension() ) )
        {
            return MediaType.IMAGE_JPEG;
        }
        else if( "gif".equalsIgnoreCase( sf.getExtension() ) )
        {
            return MediaType.IMAGE_GIF;
        }
        else if( "pdf".equalsIgnoreCase( sf.getExtension() ) )
        {
            return new MediaType( "application" , "pdf" );
        }
        else if( "txt".equalsIgnoreCase( sf.getExtension() ) )
        {
            return MediaType.TEXT_PLAIN;
        }
        else if( "xml".equalsIgnoreCase( sf.getExtension() ) )
        {
            return MediaType.TEXT_XML;
        }
        else if( "html".equalsIgnoreCase( sf.getExtension() ) 
                || "htm".equalsIgnoreCase( sf.getExtension() ) )
        {
            return MediaType.TEXT_HTML;
        }
        else
        {
            return MediaType.TEXT_PLAIN;
        }
    }
    
}
