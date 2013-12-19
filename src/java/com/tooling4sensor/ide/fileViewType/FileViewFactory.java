package com.tooling4sensor.ide.fileViewType;

import com.tooling4sensor.ide.storage.types.StorageFile;
import java.util.LinkedList;
import java.util.List;
import org.springframework.ui.Model;

/**
 *
 * @author avld
 */
public class FileViewFactory
{
    private static FileViewFactory instance;
    private List<FileView> viewList;
    private FileView defaultView;
    
    private FileViewFactory()
    {
        init();
    }
    
    private void init()
    {
        viewList = new LinkedList<>();
        viewList.add( new NescFileView() );
        viewList.add( new ImageFileView() );
        // PUT HERE OTHERS FILE VIEWS!
        
        defaultView = new DefaultFileView();
    }
    
    public String process( StorageFile file , Model model ) throws Exception
    {
        for( FileView view : viewList )
        {
            if( view.isItExtension( file.getExtension() ) )
            {
                view.setAttribute( file , model );
                return view.getJSP();
            }
        }
        
        defaultView.setAttribute( file , model );
        return defaultView.getJSP();
    }
    
    // -------------------
    // -------------------
    // -------------------
    
    public static FileViewFactory getInstance()
    {
        if( instance == null )
        {
            instance = new FileViewFactory();
        }
        
        return instance;
    }
}
