package com.tooling4sensor.ide.evaluate.controller;

import br.cin.ufpe.evaluationManager.client.ManagerClient;
import br.cin.ufpe.evaluationManager.model.EvaluationConf;
import com.tooling4sensor.ide.user.Account;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
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
public class FunctionEvaluateController
{
    private ApplicationEvaluateController appController;
    
    @Autowired
    public FunctionEvaluateController( ApplicationEvaluateController appController )
    {
        this.appController = appController;
    }
    
    @RequestMapping( value = "/project/{id}/evaluate/function" , method = RequestMethod.GET )
    public String page( @PathVariable int id
                        , HttpServletRequest request 
                        , Model model ) throws Exception
    {
        appController.page( id , request , model );
        return "project/evaluate/functions";
    }
    
    // --------------------------------
    // --------------------------------
    // --------------------------------
    
    @RequestMapping( value = "/storage/{id}/evaluate/function" , method = RequestMethod.POST )
    public void evaluate( @PathVariable int id
                        , HttpServletRequest request 
                        , HttpServletResponse response ) throws IOException, Exception
    {
        // ------------- GET PARAMETERS
        Account user          = (Account) request.getSession().getAttribute( "user" );
        ManagerClient manager = (ManagerClient) request.getAttribute( "EvaluatorManagerClient" );
        
        int length = Integer.parseInt( request.getParameter( "length" ) );
        for( int i = 0 ; i < length ; i++ )
        {
            String fileName     = request.getParameter( "functions["+ i +"][fileName]"     );
            String functionName = request.getParameter( "functions["+ i +"][functionName]" );
            
            createEvaluateConf( user , fileName , functionName , manager );
        }
        
        response.setStatus( HttpServletResponse.SC_OK );
    }
    
    public void createEvaluateConf( Account user , String fileName , String functionName , ManagerClient manager )
    {
        String modelName = fileName.replaceAll( ".nc" , "_" + functionName + ".cpn" );
        
        EvaluationConf conf = new EvaluationConf();
        conf.setEvaluateId         (                       0                 ); // it will be setted by Evaluator Manager
        conf.setUserId             ( user.getUserId()                        );
        conf.getConfiguration().put( "application_evaluate"   , "true"       );
        conf.getConfiguration().put( "application_path"       , fileName     );
        conf.getConfiguration().put( "application_function"   , functionName );
        conf.getConfiguration().put( "application_model_name" , modelName    );
        
        // -------------- SENT TO MANAGER
        manager.add( conf );
    }
    
}
