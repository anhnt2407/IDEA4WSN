package com.tooling4sensor.ide.project.controller;

import com.tooling4sensor.ide.project.Project;
import com.tooling4sensor.ide.project.dao.ProjectDAO;
import com.tooling4sensor.ide.user.Account;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author avld
 */
@Controller
public class ProjectCreateController
{
    private ProjectDAO dao;
    
    public ProjectCreateController()
    {
        // do nothing
    }
    
    @RequestMapping( value = "/project/create" )
    public void create( @Valid Project project , HttpServletRequest request , HttpServletResponse response ) throws IOException
    {
        //Recuperar o usuário da Session
        Account user = (Account) request.getSession().getAttribute( "user" );
        
        //Criar projeto no banco de dados
        project.setUserId( user.getUserId() );
        long projectId = dao.add( project );
        
        response.getWriter().write( projectId + "" );
        response.setStatus( HttpServletResponse.SC_OK );
    }
    
}
