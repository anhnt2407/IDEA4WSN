package com.tooling4sensor.ide.evaluate.controller;

import br.cin.ufpe.evaluationManager.client.ManagerClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import br.cin.ufpe.nesc2cpn.nescModule.Module;
import br.cin.ufpe.nesc2cpn.nescModule.ProjectFile;
import br.cin.ufpe.nesc2cpn.nescModule.instructions.Function;
import com.tooling4sensor.ide.project.Project;
import com.tooling4sensor.ide.project.dao.ProjectDAO;
import com.tooling4sensor.ide.storage.StorageAccount;
import com.tooling4sensor.ide.storage.dao.StorageDAO;
import com.tooling4sensor.ide.storage.types.StorageFactory;
import com.tooling4sensor.ide.storage.types.StorageType;
import com.tooling4sensor.ide.storage.types.local.LocalStorageFile;
import com.tooling4sensor.ide.user.Account;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class ApplicationEvaluateController
{
    private ProjectDAO projectDao;
    private StorageDAO storageDao;
    
    @Autowired
    public ApplicationEvaluateController( ProjectDAO projectDao , StorageDAO storageDao )
    {
        this.projectDao = projectDao;
        this.storageDao = storageDao;
    }
    
    @RequestMapping( value = "/project/{id}/evaluate/application" , method = RequestMethod.GET )
    public String page( @PathVariable int id
                        , HttpServletRequest request 
                        , Model model ) throws Exception
    {
        Account user = (Account) request.getSession().getAttribute( "user" );
        Project project = projectDao.get( id , user.getUserId() );
        
        if( project == null )
        {
            return "redirect:project/index";
        }
        
        StorageAccount storage = storageDao.get( project.getStorageId() , user.getUserId() );
        
        StorageType type = StorageFactory.getInstance().get( storage.getType() );
        type.connect( storage );
        
        model.addAttribute( "functionsMap" , process( project , type ) );
        model.addAttribute( "storageId"    , project.getStorageId() );
        
        return "project/evaluate/application";
    }
    
    public Map<String,List<String>> process( Project project , StorageType type ) throws Exception
    {
        LocalStorageFile makefile = (LocalStorageFile) type.open( project.getDirectory() + "/Makefile" );
        String component = getComponent( makefile );
        
        LocalStorageFile mainFile = (LocalStorageFile) type.open( project.getDirectory() + "/" + component + ".nc" );
        return getFunctionMap( project.getDirectory() , mainFile.getAbsolutePath() );
    }
    
    public String getComponent( LocalStorageFile makefileLsf ) throws Exception
    {
        String[] dataArray = new String( makefileLsf.getData() ).split( "\n" );
        for( String data : dataArray )
        {
            if( data == null 
                    ? true 
                    : data.trim().isEmpty() )
            {
                continue ;
            }
            else if( data.indexOf( "COMPONENT=" ) != -1 )
            {
                return data.substring( 10 );
            }
        }
        
        throw new Exception( "The Makefile doesn't have a COMPONENT element!" );
    }
    
    public Map<String,List<String>> getFunctionMap( String projectDir , String path ) throws Exception
    {
        File file = new File( path );

        ProjectFile projectFile = new ProjectFile();
        projectFile.addDiretory( file.getParent() );
        projectFile.processFile( file.getName() );
        
        Map<String,List<String>> moduleAndFunctionMap = new HashMap<>();
        
        for( Module module : projectFile.getModuleList() )
        {
            String ncFilename = projectDir + "/" + module.getName() + ".nc";
            moduleAndFunctionMap.put( ncFilename , new ArrayList<String>() );
            
            for( Function method : module.getFunctions() )
            {
                String functionName   = method.getFunctionName();
                
                if( method.getInterfaceNick() != null )
                {
                    functionName = method.getInterfaceNick() + "." + functionName;
                }
                
                moduleAndFunctionMap.get( ncFilename ).add( functionName );
            }
        }
        
        return moduleAndFunctionMap;
    }
    
    // --------------------------------
    // --------------------------------
    // --------------------------------
    
    @RequestMapping( value = "/storage/{id}/evaluate/application" , method = RequestMethod.POST )
    public void evaluate( @PathVariable int id
                        , HttpServletRequest request 
                        , HttpServletResponse response ) throws IOException, Exception
    {
        // ------------- GET PARAMETERS
        Account user = (Account) request.getSession().getAttribute( "user" );
        
        
        
        // -------------- SET PROPERTIES
        EvaluationConf conf = new EvaluationConf();
        conf.setEvaluateId            (                    0            ); // it will be setted by Evaluator Manager
        conf.setUserId                ( user.getUserId()                );
        conf.getConfiguration().put   ( "application_evaluate" , "true" );
        conf.getConfiguration().putAll( getConfiguration( request )     );
        
        
        // -------------- SENT TO MANAGER
        ManagerClient manager = (ManagerClient) request.getAttribute( "EvaluatorManagerClient" );
        manager.add( conf );
        
        response.setStatus( HttpServletResponse.SC_OK );
    }
    
    public Map<String,String> getConfiguration( HttpServletRequest request )
    {
        String application  = request.getParameter( "application" );
        String file         = request.getParameter( "app_file"    );
        String functionName = "true".equals( application ) 
                                ? null 
                                : request.getParameter( "functionName" );
        String modelName = file.replaceAll( ".nc" , "_" + functionName + ".cpn" );
        
        //TODO: não permitir que o file e o modelName tenham "../"!
        
        //------------------------ CONF
        
        Map<String,String> conf = new HashMap<>();
        
        conf.put( "application_path"       , file         );
        conf.put( "application_function"   , functionName );
        conf.put( "application_model_name" , modelName    );
        
        return conf;
    }
}
