package com.tooling4sensor.ide.fileViewType;

import com.tooling4sensor.ide.storage.types.StorageFile;
import java.util.HashSet;
import java.util.Set;
import org.springframework.ui.Model;

/**
 *
 * @author avld
 */
public class ImageFileView implements FileView
{
    private Set<String> extSet;
    
    public ImageFileView()
    {
        extSet = new HashSet<>();
        extSet.add( ".jpeg" );
        extSet.add( ".jpg" );
        extSet.add( ".gif" );
        extSet.add( ".png" );
    }
    
    @Override
    public boolean isItExtension( String ext )
    {
        if( ext == null ? true : ext.isEmpty() )
        {
            return false;
        }
        
        String e = ext.toLowerCase();
        return extSet.contains( e );
    }

    @Override
    public void setAttribute( StorageFile sf , Model model ) throws Exception
    {
        model.addAttribute( "filename" , sf.getName() );
        model.addAttribute( "data" , "Not Supported Yet." );
    }

    @Override
    public String getJSP()
    {
        return "project/view/default";
    }
    
}
