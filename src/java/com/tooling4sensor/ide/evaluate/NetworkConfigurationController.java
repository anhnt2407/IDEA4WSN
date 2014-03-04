package com.tooling4sensor.ide.evaluate;

import br.cin.ufpe.wsn2cpn.layer.LayerConfiguration;
import br.cin.ufpe.wsn2cpn.layer.LayerContainer;
import br.cin.ufpe.wsn2cpn.layer.LayerProperty;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author avld
 */
@Controller
public class NetworkConfigurationController
{
    
    public NetworkConfigurationController()
    {
        // do nothing
    }
    
    @RequestMapping( value = "/project/configuration/network" )
    public String configuration( String divId , Model model ) throws Exception
    {
        LayerContainer.PATH = "/home/avld/NetBeansProjects/Doutorado/wsn2cpn/layers/";
        
        confLayer( "application" , model );
        confLayer( "network"     , model );
        confLayer( "mac"         , model );
        
        model.addAttribute( "divId" , divId );
        
        return "project/evaluate/networkConfiguration";
    }
    
    private void confLayer( String layerName , Model model ) throws Exception
    {
        List<String> nameList = LayerContainer.getInstance().getLayerList( layerName );
        model.addAttribute( layerName + "s" , nameList );
        model.addAttribute( layerName + "Var" , getLayerVar( layerName , nameList ) );
    }
    
    private String getLayerVar( String layerName , List<String> protocolList ) throws Exception
    {
        String txt = layerName + " : {";
        for( String p : protocolList )
        {
            LayerConfiguration conf = LayerContainer.getInstance().getConfiguration( layerName , p );
            
            txt += p + " : { ";
            txt += " global : { ";
            for( LayerProperty l : conf.getVariableList() )
            {
                txt += l.getName() + " : " + l.getDefaultValue() + " , ";
            }
            
            txt += "} , node : { ";
            for( LayerProperty l : conf.getNodeProperties() )
            {
                txt += l.getName() + " : " + l.getDefaultValue() + " , ";
            }
            
            txt += "} } , ";
        }
        txt += "}";
        
        return txt.replaceAll( ", }" , " }" );
    }
    
}
