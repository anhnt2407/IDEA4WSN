package com.tooling4sensor.ide.evaluate.controller;

import br.cin.ufpe.evaluationManager.client.ManagerClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import com.tooling4sensor.ide.project.Project;
import com.tooling4sensor.ide.project.dao.ProjectDAO;
import com.tooling4sensor.ide.storage.StorageAccount;
import com.tooling4sensor.ide.storage.dao.StorageDAO;
import com.tooling4sensor.ide.storage.types.StorageFactory;
import com.tooling4sensor.ide.storage.types.StorageType;
import com.tooling4sensor.ide.user.Account;
import java.io.IOException;
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
public class BothEvaluateController
{
    private ProjectDAO projectDao;
    private StorageDAO storageDao;
    private NetworkEvaluateController netController;
    private ApplicationEvaluateController appController;
    
    @Autowired
    public BothEvaluateController( ProjectDAO projectDao 
                                 , StorageDAO storageDao 
                                 , NetworkEvaluateController netController
                                 , ApplicationEvaluateController appController )
    {
        this.projectDao = projectDao;
        this.storageDao = storageDao;
        this.netController = netController;
        this.appController = appController;
    }
    
    @RequestMapping( value = "/project/{id}/evaluate/both" , method = RequestMethod.GET )
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
        
        model.addAttribute( "functionsMap" , appController.process( project , type ) );
        model.addAttribute( "fileList"     , netController.process( project , type ) );
        model.addAttribute( "storageId"    , project.getStorageId() );
        
        return "project/evaluate/both";
    }
    
    // --------------------------------
    // --------------------------------
    // --------------------------------
    
    @RequestMapping( value = "/storage/{id}/evaluate/both" , method = RequestMethod.POST )
    public void evaluate( @PathVariable int id
                        , HttpServletRequest request 
                        , HttpServletResponse response ) throws IOException, Exception
    {
        // ------------- GET PARAMETERS
        Account user = (Account) request.getSession().getAttribute( "user" );
        
        
        // -------------- SET PROPERTIES
        EvaluationConf conf = new EvaluationConf();
        conf.setEvaluateId            (                       0                   ); // it will be setted by Evaluator Manager
        conf.setUserId                ( user.getUserId()                          );
        conf.getConfiguration().put   ( "both_evaluate"         , "true"          );
        conf.getConfiguration().putAll( appController.getConfiguration( request ) );
        conf.getConfiguration().putAll( netController.getConfiguration( request ) );
        
        // -------------- SENT TO MANAGER
        ManagerClient manager = (ManagerClient) request.getAttribute( "EvaluatorManagerClient" );
        manager.add( conf );
        
        response.setStatus( HttpServletResponse.SC_OK );
    }
    
}
