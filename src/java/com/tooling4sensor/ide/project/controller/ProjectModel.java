package com.tooling4sensor.ide.project.controller;

import com.tooling4sensor.ide.user.Account;
import org.springframework.ui.Model;

/**
 *
 * @author avld
 */
public class ProjectModel
{
    private Model model;
    
    public ProjectModel( final Model model )
    {
        this.model = model;
        init();
    }
    
    private void init()
    {
        model.addAttribute( "username" , "" );
        model.addAttribute( "title" , "" );
        model.addAttribute( "body" , "null" );
        model.addAttribute( "menu" , "null" );
    }
    
    public void setUser( Account user )
    {
        model.addAttribute( "username" , user.getName() );
    }
    
    public void setTitle( String title )
    {
        model.addAttribute( "title" , title );
    }
    
    public void setMenu( String menu )
    {
        model.addAttribute( "menu" , menu );
    }
    
    public void setBody( String body )
    {
        model.addAttribute( "body" , body );
    }
    
    // ----------------------
    
    public String getJSP()
    {
        return "project/default";
    }
    
}
