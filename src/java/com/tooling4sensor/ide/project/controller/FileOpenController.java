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
    
    @RequestMapping( value = "/project/{id}/file" )
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
        model.addAttribute( "id" , gerarHash( sf.getName() , "MD5" ) );
        return FileViewFactory.getInstance().process( sf , model );
    }
    
    public static byte[] gerarHash(String frase, String algoritmo)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update( frase.getBytes() );
            return md.digest();
        }
        catch ( NoSuchAlgorithmException e )
        {
            return null;
        }
    }
    
}
