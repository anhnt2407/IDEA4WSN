package com.tooling4sensor.ide.project.controller;

import com.tooling4sensor.ide.project.Project;
import com.tooling4sensor.ide.project.dao.ProjectDAO;
import com.tooling4sensor.ide.storage.Storage;
import com.tooling4sensor.ide.storage.dao.StorageDAO;
import com.tooling4sensor.ide.user.Account;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author avld
 */
@Controller
public class ProjectListController
{
    private ProjectDAO projectDao;
    private StorageDAO storageDao;
    
    @Autowired
    public ProjectListController( ProjectDAO dao , StorageDAO storageDao )
    {
        this.projectDao = dao;
        this.storageDao = storageDao;
    }
    
    @RequestMapping( value = "/project/index" )
    public String index( HttpServletRequest request , Model model )
    {
        Account user = (Account) request.getSession().getAttribute( "user" );
        List<Project> projectList = projectDao.list( user.getUserId() );
        List<Storage> storageList = storageDao.list( user.getUserId() );
        
        Map<Long,Storage> map = new HashMap<>();
        for( Storage s : storageList )
        {
            map.put( s.getStorageId() , s );
        }
        
        model.addAttribute( "projectList" , projectList );
        model.addAttribute( "storageMap" , map );
        
        // -----------------
        
        ProjectModel projectModel = new ProjectModel( model );
        projectModel.setUser( user );
        projectModel.setBody( "index" );
        projectModel.setTitle( "index" );
        
        return projectModel.getJSP();
    }
    
    @RequestMapping( value = "/project/list" )
    public String list( HttpServletRequest request , Model model )
    {
        index( request , model );
        return "project/list";
    }
    
}
