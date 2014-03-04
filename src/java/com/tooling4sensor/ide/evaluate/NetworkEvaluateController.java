package com.tooling4sensor.ide.evaluate;

import br.cin.ufpe.sensor2model.Sensor2Model;
import br.cin.ufpe.wsn2cpn.Topology;
import com.tooling4sensor.ide.project.Project;
import com.tooling4sensor.ide.project.dao.ProjectDAO;
import com.tooling4sensor.ide.storage.StorageAccount;
import com.tooling4sensor.ide.storage.dao.StorageDAO;
import com.tooling4sensor.ide.storage.types.StorageFactory;
import com.tooling4sensor.ide.storage.types.StorageFile;
import com.tooling4sensor.ide.storage.types.StorageType;
import com.tooling4sensor.ide.user.Account;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author avld
 */
@Controller
public class NetworkEvaluateController
{
    private ProjectDAO projectDao;
    private StorageDAO storageDao;
    
    @Autowired
    public NetworkEvaluateController( ProjectDAO projectDao , StorageDAO storageDao )
    {
        this.projectDao = projectDao;
        this.storageDao = storageDao;
    }
    
    @RequestMapping( value = "/project/{id}/evaluate/network" , method = RequestMethod.GET )
    public String page( @PathVariable Long id
                    , Model model 
                    , HttpServletRequest request ) throws Exception
    {
        Account user = (Account) request.getSession().getAttribute( "user" );
        Project project = projectDao.get( id , user.getUserId() );
        
        if( project == null )
        {
            return "redirect:project/index";
        }
        
        StorageAccount storage = storageDao.get( project.getStorageId() , user.getUserId() );
        
        StorageType type = StorageFactory.getInstance().get( storage.getType() );
        type.connect( storage );
        StorageFile file = type.open( project.getDirectory() );
        
        model.addAttribute( "fileList" , getFileList( file ) );
        
        return "";
    }
    
    @RequestMapping( value = "/project/{id}/evaluate/network" , method = RequestMethod.POST )
    public void evaluate( @PathVariable Long id 
                        , HttpServletRequest request 
                        , HttpServletResponse response ) throws Exception
    {
        String reliability = request.getParameter( "reliability" );
        String networkFile = request.getParameter( "file" );
        String modelFile   = networkFile.replace( ".wsn" , ".cpn" );
        
        // ------------------------- evaluate
        
        Sensor2Model translator = new Sensor2Model();
        List<Topology> list = translator.evaluateNetwork( networkFile , modelFile );
        
        if( "true".equalsIgnoreCase( reliability ) )
        {
            translator.networkReliability( list );
        }
        
        // ------------------------- result
        
        
        response.setStatus( HttpServletResponse.SC_OK );
    }
    
    // -------------------------------
    // -------------------------------
    // -------------------------------
    
    private List<String> getFileList( StorageFile file )
    {
        List<String> list = new ArrayList<>();
        
        for( StorageFile child : file.getChildren() )
        {
            if( child.isDirectory() )
            {
                List<String> childList = getFileList( child );
                list.addAll( childList );
            }
            else if( "wsn".equalsIgnoreCase( child.getExtension() ) )
            {
                list.add( child.getPath() );
            }
        }
        
        return list;
    }
    
}
