package com.tooling4sensor.ide.fileViewType;

import com.tooling4sensor.ide.storage.types.StorageFile;
import org.springframework.ui.Model;

/**
 *
 * @author avld
 */
public interface FileView
{
    public boolean isItExtension( String ext );
    public void setAttribute( StorageFile sf , Model model ) throws Exception;
    public String getJSP();
}
