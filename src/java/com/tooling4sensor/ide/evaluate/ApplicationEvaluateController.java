package com.tooling4sensor.ide.evaluate;

import com.tooling4sensor.ide.project.dao.ProjectDAO;
import com.tooling4sensor.ide.storage.dao.StorageDAO;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author avld
 */
@Controller
public class ApplicationEvaluateController
{
    private ProjectDAO projectDao;
    private StorageDAO storageDao;
    
    @Autowired
    public ApplicationEvaluateController( ProjectDAO projectDao , StorageDAO storageDao )
    {
        this.projectDao = projectDao;
        this.storageDao = storageDao;
    }
    
    @RequestMapping( value = "/evaluate/application" )
    public String create( HttpServletRequest request , Model model ) throws IOException
    {
        return null;
    }
}
