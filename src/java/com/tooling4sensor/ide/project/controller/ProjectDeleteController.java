package com.tooling4sensor.ide.project.controller;

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
public class ProjectDeleteController
{
    private ProjectDAO dao;
    
    public ProjectDeleteController()
    {
        // do nothing
    }
    
    @RequestMapping( value = "/project/modify/{projectId}" )
    public void delete( long projectId , HttpServletRequest request , HttpServletResponse response )
    {
        //Recuperar o usuário da Session
        Account user = (Account) request.getSession().getAttribute( "user" );
        
        //Deletar projeto no banco de dados
        dao.delete( projectId , user.getUserId() );
        
        response.setStatus( HttpServletResponse.SC_OK );
    }
    
}
