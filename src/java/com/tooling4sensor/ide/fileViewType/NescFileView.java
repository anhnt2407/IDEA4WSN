package com.tooling4sensor.ide.fileViewType;

import com.tooling4sensor.ide.storage.types.StorageFile;
import org.springframework.ui.Model;

/**
 *
 * @author avld
 */
public class NescFileView implements FileView
{

    public NescFileView()
    {
        // do nothing
    }
    
    @Override
    public boolean isItExtension( String ext )
    {
        return "nc".equalsIgnoreCase( ext );
    }

    @Override
    public void setAttribute( StorageFile sf , Model model ) throws Exception
    {
        model.addAttribute( "filename" , sf.getName() );
        model.addAttribute( "data" , new String( sf.getData() ) );
    }

    @Override
    public String getJSP()
    {
           return "project/fileView/nc";
    }
    
}
