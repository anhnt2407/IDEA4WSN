package com.tooling4sensor.ide.user.controller;

import com.tooling4sensor.ide.user.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author avld
 */
@Controller
public class ResetPasswordController
{
    private UserDAO dao;
    
    @Autowired
    public ResetPasswordController( UserDAO dao )
    {
        this.dao = dao;
    }
    
    public String reset( String email )
    {
        return "redirect:login";
    }
}
