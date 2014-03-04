package com.tooling4sensor.ide.fileViewType;

import br.cin.ufpe.wsn2cpn.Node;
import br.cin.ufpe.wsn2cpn.Topology;
import br.cin.ufpe.wsn2cpn.WsnFile;
import com.tooling4sensor.ide.storage.types.StorageFile;
import com.tooling4sensor.ide.storage.types.local.LocalStorageFile;
import java.util.Map.Entry;
import org.springframework.ui.Model;

/**
 *
 * @author avld
 */
public class WsnFileView implements FileView
{

    public WsnFileView()
    {
        // do nothing
    }
    
    @Override
    public boolean isItExtension( String ext )
    {
        return "wsn".equalsIgnoreCase( ext );
    }

    @Override
    public void setAttribute( StorageFile sf , Model model ) throws Exception
    {
        model.addAttribute( "filename" , sf.getName() );
        
        if( sf.getData().length < 10 )
        {
            model.addAttribute( "data" , "{properties: {} "
                                       + ", nodes: {}"
                                       + ", nodeDefault: network_nodeDefault"
                                       + ", counter: 0}" );
        }
        else
        {
            //TODO: depois deixar isso aqui mais generico!
            String data = xmlToObject( ((LocalStorageFile) sf).getAbsolutePath() );
            model.addAttribute( "data" , data );
        }
    }

    @Override
    public String getJSP()
    {
        return "project/fileView/wsn";
    }
    
    // ---------------------------------------
    // ---------------------------------------
    // ---------------------------------------
    
    private String xmlToObject( String path ) throws Exception
    {
        String txt   = "{ properties: { ";
        Topology top = WsnFile.open( path );
        
        for( Entry<String,String> entry : top.getConfigurationMap().entrySet() )
        {
            if( entry.getKey().indexOf( "." ) > 0 )
            {
                continue ;
            }
            
            txt += entry.getKey() + " : '" + entry.getValue() + "'\n";
            txt += ", ";
        }
        txt += "} , nodes: { ";
        
        int counter = 0;
        Node nodeDefault = null;
        for( Node node : top.getNodeMap().values() )
        {
            if( nodeDefault == null )
            {
                nodeDefault = node;
            }
            
            if( counter < node.getId() )
            {
                counter = node.getId();
            }
            
            txt += "node" + node.getId();
            txt += nodeToObject( node );
            txt += ", ";
        }
        
        txt += "} , nodeDefault" + ( nodeDefault == null ? ": network_nodeDefault" : nodeToObject( nodeDefault ) );
        txt += " , counter : " + counter + " }";
        
        return txt.replaceAll( ", }" , " }" );
    }
    
    private String nodeToObject( Node node )
    {
        if( node == null )
        {
            return "";
        }
        
        String txt = " : { id : " + node.getId();
        
        for( Entry<String,String> entry : node.getProperties().entrySet() )
        {
            txt += ", " + entry.getKey() + " : '" + entry.getValue() + "'\n";
        }

        txt += " }\n";
        
        return txt;
    }
    
}
