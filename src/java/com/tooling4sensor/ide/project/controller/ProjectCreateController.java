package com.tooling4sensor.ide.project.controller;

import com.tooling4sensor.ide.project.Project;
import com.tooling4sensor.ide.project.dao.ProjectDAO;
import com.tooling4sensor.ide.storage.Storage;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author avld
 */
@Controller
public class ProjectCreateController
{
    private ProjectDAO projectDao;
    private StorageDAO storageDao;
    
    @Autowired
    public ProjectCreateController( ProjectDAO projectDao , StorageDAO storageDao )
    {
        this.projectDao = projectDao;
        this.storageDao = storageDao;
    }
    
    @RequestMapping( value = "/project/create" )
    public String create( HttpServletRequest request , Model model ) throws IOException
    {
        //Recuperar o usuário da Session
        Account user = (Account) request.getSession().getAttribute( "user" );
        
        model.addAttribute( "action_form" , "Create a Project" );
        
        // -----------------
        
        ProjectModel projectModel = new ProjectModel( model );
        projectModel.setUser( user );
        projectModel.setBody( "form" );
        projectModel.setTitle( "Create" );
        
        return projectModel.getJSP();
    }
    
    @RequestMapping( value = "/project/create" , method = RequestMethod.POST )
    public String create( Project project , HttpServletRequest request ) throws IOException, Exception
    {
        //Recuperar o usuário da Session
        Account user = (Account) request.getSession().getAttribute( "user" );
        
        //Criar projeto no banco de dados
        project.setUserId( user.getUserId() );
        projectDao.add( project );
        
        Storage storage = storageDao.get( project.getStorageId() , user.getUserId() );
        
        StorageType storageType = StorageFactory.getInstance().get( storage.getType() );
        storageType.connect( storage );
        
        storageType.createDir( project.getDirectory() );
        
        return "redirect:/project/index";
    }
    
    @RequestMapping( value = "/project/create/ajax" , method = RequestMethod.POST )
    public void create( Project project , HttpServletRequest request , HttpServletResponse response ) throws IOException, Exception
    {
        //Recuperar o usuário da Session
        Account user = (Account) request.getSession().getAttribute( "user" );
        
        //Criar projeto no banco de dados
        project.setUserId( user.getUserId() );
        long projectId = projectDao.add( project );
        
        Storage storage = storageDao.get( project.getStorageId() , user.getUserId() );
        
        StorageType storageType = StorageFactory.getInstance().get( storage.getType() );
        storageType.connect( storage );
        
        storageType.createDir( project.getDirectory() );
        
        response.getWriter().write( projectId + "" );
        response.setStatus( HttpServletResponse.SC_OK );
    }
    
}
