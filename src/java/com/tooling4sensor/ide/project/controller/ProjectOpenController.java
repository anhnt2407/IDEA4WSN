package com.tooling4sensor.ide.project.controller;

import com.tooling4sensor.ide.project.Project;
import com.tooling4sensor.ide.project.dao.ProjectDAO;
import com.tooling4sensor.ide.storage.Storage;
import com.tooling4sensor.ide.storage.dao.StorageDAO;
import com.tooling4sensor.ide.storage.types.StorageFactory;
import com.tooling4sensor.ide.storage.types.StorageFile;
import com.tooling4sensor.ide.storage.types.StorageType;
import com.tooling4sensor.ide.user.Account;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author avld
 */
@Controller
public class ProjectOpenController
{
    private ProjectDAO projectDao;
    private StorageDAO storageDao;
    
    private long counter = 1;
    
    @Autowired
    public ProjectOpenController( ProjectDAO dao , StorageDAO storageDao )
    {
        this.projectDao = dao;
        this.storageDao = storageDao;
    }
    
    @RequestMapping( value = "/project/{id}" )
    public String open( @PathVariable Long id , Model model , HttpServletRequest request ) throws Exception
    {
        Account user = (Account) request.getSession().getAttribute( "user" );
        Project project = projectDao.get( id , user.getUserId() );
        
        if( project == null )
        {
            return "redirect:project/index";
        }
        
        Storage storage = storageDao.get( project.getStorageId() , user.getUserId() );
        
        StorageType type = StorageFactory.getInstance().get( storage.getType() );
        type.connect( storage );
        
        String xml = file2data( type.open( project.getDirectory() ) );
        
        model.addAttribute( "username"      , user.getName() );
        model.addAttribute( "projectJsTree" , xml            );
        
        ProjectModel projectModel = new ProjectModel( model );
        projectModel.setUser( user );
        projectModel.setBody( "project" );
        projectModel.setTitle( "index" );
        projectModel.setMenu( "menu" );
        
        return projectModel.getJSP();
    }
    
    @RequestMapping( value = "/project/open/{id}" ) //, method = RequestMethod.POST
    public void open( @PathVariable Long id , HttpServletRequest request , HttpServletResponse response ) throws Exception
    {
        Account user = (Account) request.getSession().getAttribute( "user" );
        Project project = projectDao.get( id , user.getUserId() );
        
        if( project == null )
        {
            response.setStatus( HttpServletResponse.SC_NOT_FOUND );
            return ;
        }
        
        Storage storage = storageDao.get( project.getStorageId() , user.getUserId() );
        
        StorageType type = StorageFactory.getInstance().get( storage.getType() );
        type.connect( storage );
        
        String xml = file2data( type.open( project.getDirectory() ) );

        response.setContentType( "text/html" );
        response.getWriter().write( xml );
        response.setStatus( HttpServletResponse.SC_OK );
    }
    
    //http://www.jstree.com/documentation/html_data
    public String file2data( StorageFile file )
    {
        String name = "<a href='#'>" + file.getName() + "</a> \n";
        
        StringBuilder builder = new StringBuilder();
        builder.append( "<li id='root' rel='project'> \n" );
        builder.append( name );
        builder.append( "<ul> \n" );
        
        List<StorageFile> lista = file.getChildren();
        for( int i = 0 ; i < lista.size() ; i++ )
        {
            boolean _last = ( i + 1 == lista.size() );
            StorageFile _file = lista.get( i );

            children( builder , _last , _file );
        }
        
        builder.append( "</ul> \n" );
        builder.append( "</li> \n" );
        
        return builder.toString();
    }
    
    private void children( StringBuilder builder , boolean last , StorageFile file )
    {
        String liId    = "Node_" + (counter++);
        String type    = getType( file );
        
        String liClass = file.isDirectory() ? "jstree-closed" : "jstree-leaf";
        liClass       += last ? " jstree-last" : "";
        
        String li   = "<li id='"+ liId +"' rel='"+ type +"'> \n";
        String name = "<a href='#'>" + file.getName() + "</a> \n";
        
        builder.append( li   );
        builder.append( name );
        
        //Tem que ser diretorio e ter filhos
        if( file.isDirectory() && file.hasChildren() )
        {
            builder.append( "<ul> \n" );
            
            List<StorageFile> lista = file.getChildren();
            for( int i = 0 ; i < lista.size() ; i++ )
            {
                boolean _last = ( i + 1 == lista.size() );
                StorageFile _file = lista.get( i );

                children( builder , _last , _file );
            }
            
            builder.append( "</ul> \n" );
        }
        
        builder.append( "</li> \n" );
        
        // Last ......: jstree-last
        // Directory .: jstree-closed
        // File ......: jstree-leaf
        // <li id="{id}" rel="{type}" class="{classes}">
        
        
        //<li id="node_134" rel="default" class="jstree-last jstree-leaf">
        // <ins class="jstree-icon">&nbsp;</ins>
        // <a href="#" class="jstree-clicked">
        //   <ins class="jstree-icon">&nbsp;</ins>
        //   NODE
        // </a>
        //</li>
    }
    
    private String getType( StorageFile file )
    {
        if( file.isDirectory() )
        {
            return "directory";
        }
        else
        {
            return "nesc";
        }
    }
    
}
