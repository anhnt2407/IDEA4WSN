package com.tooling4sensor.ide.user.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author avld
 */
@Controller
public class LogoutController
{
    
    public LogoutController()
    {
        // do nothing
    }
    
    @RequestMapping( value = "/logout" )
    public String logout( HttpServletRequest request )
    {
        request.getSession().invalidate();
        return "redirect:/login";
    }
}
