var topologyStorage = {};
var network_nodeDefault = 
        {
            id      : 1      ,
            battery : 100    ,
            nodeType: "NODE" ,
            X       : 0      ,
            Y       : 0      ,
            range   : 50     
        };

jQuery.fn.extend (
    {
    wsn : function ()
              {
                  network_init( this );
              } ,
    addNode : function( e )
              {
                network_addNode( this , e );
              } ,
    setTopology : function( top )
              {
                return network_setTopology( this , top );
              } ,
    getTopology : function()
              {
                return network_getTopology( this );
              }
    } );

// ----------------------------------
// ----------------------------------
// ----------------------------------

function network_init( el )
{
    var elJQuery     = $( el );
    var canvasJQuery = elJQuery.find( "canvas" );
    
    var value        = elJQuery.find( "input:hidden[name=id]" ).val();
    topologyStorage[ value ] = { properties : {} 
                               , nodes : {} 
                               , counter : 0 
                               , nodeDefault : network_nodeDefault
                               };
    
    canvasJQuery.dblclick( function ( e )
    {
        elJQuery.addNode( e );
    });
    
    elJQuery.find( "input:button[name=delete]" ).click( function () {
                var nodeId   = elJQuery.find( "input:text[name=nodeId]" ).val();
                var wsnId    = elJQuery.find( "input:hidden[name=id]" ).val();
                var topology = topologyStorage[ wsnId ];
                
                canvasJQuery.removeLayer( "node" + nodeId );
                canvasJQuery.setLayer( "range" , { visible : false } );
                canvasJQuery.drawLayers();
                
                delete topology.nodes[ "node" + nodeId ];
            });
            
    elJQuery.find( "input:button[name=save]" ).click( function () {
                var nodeId   = elJQuery.find( "input:text[name=nodeId]" ).val();
                var wsnId    = elJQuery.find( "input:hidden[name=id]" ).val();
                var allInput = elJQuery.find( "tbody ").find( "input" );
                
                var topology = topologyStorage[ wsnId ];
                var node     = topology.nodes[ "node" + nodeId ];
                
                for( var i = 0 ; i < allInput.length ; i++ )
                {
                    var inputJQuery = $( allInput[ i ] );
                    
                    var inputName  = inputJQuery.attr( 'name' );
                    var inputValue = inputJQuery.val();
                    
                    node[ inputName ] = inputValue;
                }
                
                var layer = canvasJQuery.getLayer( "node" + nodeId );
                canvasJQuery.animateLayer( layer , {
                        fillStyle : network_color( node.nodeType ) ,
                        x : parseInt( node.X ) ,
                        y : parseInt( node.Y )
                      });
                      
                canvasJQuery.setLayer( "range" , { x : parseInt( node.X ) 
                                                 , y : parseInt( node.Y )
                                                 , radius : parseInt( node.range ) 
                                                 } );
            });
            
    // ---------------------------- Draw a range
    canvasJQuery.drawArc( {
                name : "range"
              , fillStyle: 'lightgray'
              , x : 50
              , y : 50
              , radius : 50
              , draggable: false
              , visible : false
              , layer : true
            } );
}

// ----------------------------------
// ----------------------------------
// ----------------------------------

function network_addNode( el , e )
{
    var elJQuery     = $( el );
    var canvasJQuery = elJQuery.find( "canvas" );
    
    var wsnId = elJQuery.find( "input:hidden[name=id]" ).val();
    var type  = elJQuery.find( "input:radio[name=action]:checked" ).val();
    var offset    = canvasJQuery.offset();
    var relativeX = ( e.pageX - offset.left );
    var relativeY = ( e.pageY - offset.top  );
    
    var topology   = topologyStorage[ wsnId ];
    topology.counter++;
    var nodeId     = "node" + topology.counter;
    
    topology.nodes[ nodeId ]      = $.extend( {} , topology.nodeDefault );
    topology.nodes[ nodeId ].id   = topology.counter;
    topology.nodes[ nodeId ].nodeType = type;
    topology.nodes[ nodeId ].X    = relativeX;
    topology.nodes[ nodeId ].Y    = relativeY;
    
    network_drawNode( el , canvasJQuery , topology.nodes[ nodeId ] );
}

function network_drawNode( el , canvasJQuery , node )
{
    var nodeId = "node" + node.id;
    
    canvasJQuery.drawArc({
                layer: true ,
                fillStyle : network_color( node.nodeType ) ,
                x : parseInt( node.X )     ,
                y : parseInt( node.Y )     ,
                radius    : 10    ,
                draggable : true  ,
                name : nodeId     ,
                mousedown : function( event )
                        {
                            network_selectNode( el , node );
                        } ,
                dragstop :  function( layer )
                        {
                            node.X = layer.x;
                            node.Y = layer.y;
                            
                            network_selectNode( el , node );
                        } ,
                drag: function( layer )
                        {
                            canvasJQuery.setLayer( "range" , { 
                                          x : parseInt( layer.x )
                                        , y : parseInt( layer.y )
                                    } );
                        }
           });
}

function network_selectNode( el , node )
{
    var elJQuery = $( el );
    elJQuery.find( "input:text[name=nodeId]" ).val( node.id );
    
    var tableBody = "";
    for( var propertyName in node )
    {
        if( propertyName === "id" )
        {
            continue ;
        }
        
        var propertyValue = node[ propertyName ];
        
        tableBody += "<tr>" 
                  +  "<td>"+ propertyName +"</td>"
                  +  "<td>"
                  +  "<input type='text' " +
                             "name='"+ propertyName +"'" +
                             "value='"+ propertyValue +"' />"
                  +  "</td>"
                  +  "</tr>";
    }
    
    elJQuery.find( "tbody" ).html( tableBody );
    
    // ----------------------- show a range
    var canvasJQuery = elJQuery.find( "canvas" );
    canvasJQuery.setLayer( "range" , { x : parseInt( node.X )
                                     , y : parseInt( node.Y )
                                     , radius : parseInt( node.range )
                                     , visible: true
                                     } );
}

// ----------------------------------
// ----------------------------------
// ----------------------------------

function network_setTopology( el , top )
{
    var elJQuery = $( el );
    var canvasJQuery = elJQuery.find( "canvas" );
    canvasJQuery.removeLayers();
    
    canvasJQuery.drawArc( {
                name : "range"
              , fillStyle: 'lightgray'
              , x : 50
              , y : 50
              , radius : 50
              , draggable: false
              , visible : false
              , layer : true
            } );
    
    var wsnId    = elJQuery.find( "input:hidden[name=id]" ).val();
    topologyStorage[ wsnId ] = top;
    
    for( var nodeId in top.nodes )
    {
        network_drawNode( el , canvasJQuery , top.nodes[ nodeId ] );
    }
}

function network_getTopology( el )
{
    var elJQuery = $( el );
    var wsnId    = elJQuery.find( "input:hidden[name=id]" ).val();
    var topology = topologyStorage[ wsnId ];
    
    // ---------------------------
    
    var txt = "<topology>";
    txt += "<configurations>";
    for( var name in topology.properties )
    {
        var value = topology.properties[ name ];
        txt += "<property name=\""+ name +"\" value=\""+ value +"\" />";
    }
    txt += "</configurations>";
    
    // ---------------------------
    
    txt += "<nodes>";
    for( var nodeId in topology.nodes )
    {
        var node = topology.nodes[ nodeId ];
        txt += "<node id=\""+ node.id +"\">";
        
        for( var name in node )
        {
            if( name === "id" )
            {
                continue ;
            }
            else
            {
                var value = node[ name ];
                txt += "<property name=\""+ name +"\" value=\""+ value +"\" />";
            }
        }
        
        txt += "</node>";
    }
    txt += "</nodes>";
    
    txt += "</topology>";
    
    return txt;
}

// ----------------------------------
// ----------------------------------
// ----------------------------------

function network_color( type )
{
    return type === "NODE" ? '#000' : '#F00';
}