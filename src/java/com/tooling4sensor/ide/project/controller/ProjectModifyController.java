package com.tooling4sensor.ide.project.controller;

import com.tooling4sensor.ide.project.Project;
import com.tooling4sensor.ide.project.dao.ProjectDAO;
import com.tooling4sensor.ide.user.Account;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author avld
 */
@Controller
public class ProjectModifyController
{
    private ProjectDAO dao;
    
    public ProjectModifyController()
    {
        // do nothing
    }
    
    @RequestMapping( value = "/project/modify" )
    public void modify( Project project , HttpServletRequest request , HttpServletResponse response )
    {
        //Recuperar o usuário da Session
        Account user = (Account) request.getSession().getAttribute( "user" );
        
        //Deletar projeto no banco de dados
        project.setUserId( user.getUserId() );
        dao.modify( project );
        
        response.setStatus( HttpServletResponse.SC_OK );
    }
}
