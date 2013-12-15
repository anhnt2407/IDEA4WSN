package com.tooling4sensor.ide.project.controller;

import com.tooling4sensor.ide.project.Project;
import com.tooling4sensor.ide.project.dao.ProjectDAO;
import com.tooling4sensor.ide.user.Account;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
    private ProjectDAO dao;
    
    public ProjectListController()
    {
        // do nothing
    }
    
    @RequestMapping( value = "/project/list" )
    public String list( Model model , HttpServletRequest request )
    {
        //Recuperar o usuário da Session
        Account user = (Account) request.getSession().getAttribute( "user" );
        
        //Recuperar os projetos relacionados com o usuário no banco de dados
        List<Project> projectList = dao.list( user.getUserId() );
        
        //Colocar a lista de projectos na página
        model.addAttribute( "projectList" , projectList );
        
        //Retornar para o usuário
        return "projectList";
    }
    
}
