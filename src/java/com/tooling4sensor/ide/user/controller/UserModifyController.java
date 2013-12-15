package com.tooling4sensor.ide.user.controller;

import com.tooling4sensor.ide.user.Account;
import com.tooling4sensor.ide.user.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author avld
 */
@Controller
public class UserModifyController
{
    private UserDAO dao;
    
    @Autowired
    public UserModifyController( UserDAO dao )
    {
        this.dao = dao;
    }
    
    @RequestMapping( value = "/user/update" , method = RequestMethod.POST )
    public String modify( Account user , String pwdOld , String pwdConf )
    {
        return "redirect:project/index";
    }
}
