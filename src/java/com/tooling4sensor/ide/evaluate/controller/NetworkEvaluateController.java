package com.tooling4sensor.ide.evaluate.controller;

import br.cin.ufpe.evaluationManager.client.ManagerClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import com.tooling4sensor.ide.project.Project;
import com.tooling4sensor.ide.project.dao.ProjectDAO;
import com.tooling4sensor.ide.storage.StorageAccount;
import com.tooling4sensor.ide.storage.dao.StorageDAO;
import com.tooling4sensor.ide.storage.types.StorageFactory;
import com.tooling4sensor.ide.storage.types.StorageFile;
import com.tooling4sensor.ide.storage.types.StorageType;
import com.tooling4sensor.ide.user.Account;
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
public class NetworkEvaluateController
{
    private ProjectDAO projectDao;
    private StorageDAO storageDao;
    
    @Autowired
    public NetworkEvaluateController( ProjectDAO projectDao , StorageDAO storageDao )
    {
        this.projectDao = projectDao;
        this.storageDao = storageDao;
    }
    
    @RequestMapping( value = "/project/{id}/evaluate/network" , method = RequestMethod.GET )
    public String page( @PathVariable Long id
                    , Model model 
                    , HttpServletRequest request ) throws Exception
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
        
        model.addAttribute( "fileList"  , process( project , type ) );
        model.addAttribute( "storageId" , project.getStorageId()    );
        
        return "project/evaluate/network";
    }
    
    public List<String> process( Project project , StorageType type ) throws Exception
    {
        StorageFile file = type.open( project.getDirectory() );
        return getFileList( file );
    }
    
    public List<String> getFileList( StorageFile file )
    {
        List<String> list = new ArrayList<>();
        
        for( StorageFile child : file.getChildren() )
        {
            if( child.isDirectory() )
            {
                List<String> childList = getFileList( child );
                list.addAll( childList );
            }
            else if( "wsn".equalsIgnoreCase( child.getExtension() ) )
            {
                list.add( child.getPath() );
            }
        }
        
        return list;
    }
    
    // ----------------------------
    // ----------------------------
    // ----------------------------
    
    @RequestMapping( value = "/storage/{id}/evaluate/network" , method = RequestMethod.POST )
    public void evaluate( @PathVariable Long id 
                        , HttpServletRequest request 
                        , HttpServletResponse response ) throws Exception
    {
        // ----------------- GET PARAMETERS
        Account user = (Account) request.getSession().getAttribute( "user" );
        
        // ----------------- SET CONFIGURATION
        EvaluationConf conf = new EvaluationConf();
        conf.setEvaluateId            (              0                      );  // it will be setted by Evaluator Manager
        conf.setUserId                ( user.getUserId()                    );
        conf.getConfiguration().put   ( "network_evaluate"         , "true" );
        conf.getConfiguration().putAll( getConfiguration( request )         );
        
        
        // ------------------------- SEND TO EVALUATE
        ManagerClient manager = (ManagerClient) request.getAttribute( "EvaluatorManagerClient" );
        manager.add( conf );
        
        response.setStatus( HttpServletResponse.SC_OK );
    }
    
    public Map<String,String> getConfiguration( HttpServletRequest request )
    {
        String stopCriteria      = request.getParameter( "stop_criteria"       );
        String stopCriteriaValue = request.getParameter( "stop_criteria_value" );
        String reliability       = request.getParameter( "reliability"         );
        String networkFile       = request.getParameter( "net_file"            );
        String modelName         = networkFile.replace ( ".wsn"   ,   ".cpn"   );
        
        //------------------------ CONF
        
        Map<String,String> conf = new HashMap<>();
        
        conf.put( "network_path"        , networkFile );
        conf.put( "network_reliability" , reliability );
        conf.put( "network_model_name"  , modelName   );
        
        conf.put( "criteria_stop"       , stopCriteria      ); //ex. time
        conf.put( "criteria_stop_value" , stopCriteriaValue ); //ex. 10
        
        return conf;
    }
    
}
