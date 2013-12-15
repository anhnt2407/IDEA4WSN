package com.tooling4sensor.ide.user.controller;

import com.tooling4sensor.ide.user.Account;
import com.tooling4sensor.ide.user.dao.UserDAO;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author avld
 */
@Controller
public class LoginController
{
    private UserDAO dao;
    
    @Autowired
    public LoginController( UserDAO dao )
    {
        this.dao = dao;
    }
    
    @RequestMapping( value = "/login" , method = RequestMethod.GET )
    public String login()
    {
        return "login";
    }
    
    @RequestMapping( value = "/login" , method = RequestMethod.POST )
    public String login( String email , String pwd , HttpServletRequest request )
    {
        Account account = dao.login( email , pwd );
        
        if( account == null )
        {
            return "login";
        }
        else
        {
            request.getSession().setAttribute( "user" , account );
            return "redirect:project.index";
        }
    }
}
