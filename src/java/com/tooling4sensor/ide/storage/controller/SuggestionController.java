package com.tooling4sensor.ide.storage.controller;

import br.cin.ufpe.nesc2cpn.suggestion.SuggestionMain;
import com.tooling4sensor.ide.storage.StorageAccount;
import com.tooling4sensor.ide.storage.dao.StorageDAO;
import com.tooling4sensor.ide.storage.types.StorageFactory;
import com.tooling4sensor.ide.storage.types.StorageType;
import com.tooling4sensor.ide.storage.types.local.LocalStorageFile;
import com.tooling4sensor.ide.user.Account;
import java.util.List;
import java.util.Map;
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
public class SuggestionController
{
    private StorageDAO storageDAO;
    
    @Autowired
    public SuggestionController( StorageDAO storageDAO )
    {
        this.storageDAO = storageDAO;
    }
    
    @RequestMapping( value = "/storage/{id}/file/suggestions" )
    public String page( @PathVariable int id
                        , String file
                        , HttpServletRequest request 
                        , Model model ) throws Exception
    {
        // ---------------------------- Abrir o Arquivo
        Account user = (Account) request.getSession().getAttribute( "user" );
        StorageAccount storage = storageDAO.get( id , user.getUserId() );
        
        StorageType type = StorageFactory.getInstance().get( storage.getType() );
        type.connect( storage );
        
        LocalStorageFile sf = (LocalStorageFile) type.open( file );
        
        model.addAttribute( "file" , file );
        model.addAttribute( "suggestionMap" , process( sf ) );
        
        return "project/suggestions";
    }
    
    public Map<Integer,List<String>> process( LocalStorageFile sf ) throws Exception
    {
        SuggestionMain sugs = new SuggestionMain();
        return sugs.processSuggestions( sf.getAbsolutePath() );
    }
    
}
