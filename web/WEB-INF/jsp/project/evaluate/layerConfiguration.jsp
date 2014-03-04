<div id="layerConf" title="Layer Configuration" >
    <div class="row">
        
        <fieldset class="col-xs-7 col-md-5">
            <legend>Global</legend>
            
            <table id="global" class="table">
                <thead>
                    <tr>
                        <th>Property</th>
                        <th>Value</th>
                    </tr>
                </thead>

                <tbody>
                    <!-- DATA -->
                </tbody>
            </table>
        </fieldset>
    
        <div class="col-xs-1 col-md-1">
            
        </div>
        
        <fieldset class="col-xs-7 col-md-5">
            <legend>Node</legend>
            
            <table id="node" class="table">
                <thead>
                    <tr>
                        <th>Property</th>
                        <th>Value</th>
                    </tr>
                </thead>

                <tbody>
                    <!-- DATA -->
                </tbody>
            </table>
        </fieldset>
        
    </div>
    
    <input type="hidden" name="layer" value="" />
    <input type="button" id="btn_lc_ok" value="OK" />
    <input type="button" id="btn_lc_cn" value="Cancel" />
</div>

<script>
    var saveVar  = {};
    
    $(function (){
        $( "#layerConf" ).dialog( {
            autoOpen: false ,
            height  : 400  ,
            width   : 700  ,
            modal   : true 
        });
        
        var layerVar = { ${applicationVar} 
                       , ${networkVar}
                       , ${macVar} 
                       };
        
        var appValue = $( "#app_layer" ).find( "select" ).val();
        var netValue = $( "#net_layer" ).find( "select" ).val();
        var macValue = $( "#mac_layer" ).find( "select" ).val();
        
        saveVar  = { application : layerVar.application[ appValue ]  
                   , network : layerVar.network[ netValue ] 
                   , mac : layerVar.mac[ macValue ] 
                   };
        
        // -------------------------------------- //
        
        $( "#app_layer" ).find( "select" ).change( function(){
            var value  = $( "#app_layer" ).find( "select" ).val();
            var object = layerVar.application[ value ];
            
            saveVar.application = object;
        });
        
        $( "#net_layer" ).find( "select" ).change( function(){
            var value  = $( "#net_layer" ).find( "select" ).val();
            var object = layerVar.network[ value ];
            
            saveVar.network = object;
        });
        
        $( "#mac_layer" ).find( "select" ).change( function(){
            var value  = $( "#mac_layer" ).find( "select" ).val();
            var object = layerVar.mac[ value ];
            
            saveVar.mac = object;
        });
        
        // -------------------------------------- //
        
        $( "#app_layer" ).find( "button" ).click( function(){            
            openDialog( "application" , saveVar.application );
        });
        
        $( "#net_layer" ).find( "button" ).click( function(){
            openDialog( "network" , saveVar.network );
        });
        
        $( "#mac_layer" ).find( "button" ).click( function(){
            console.log( saveVar[ "mac" ] );
            openDialog( "mac" , saveVar.mac );
        });
        
        // -------------------------------------- //
        
        $( "#btn_lc_ok" ).click( function(){
            var layer        = $( "#layerConf" ).find( "input:hidden" ).val();
            saveVar[ layer ] = { global : {} , node : {} };
            
            var allGlobalVar = $( "#layerConf" ).find( "#global tbody input" );
            for( var i = 0 ; i < allGlobalVar.length ; i++ )
            {
                var global = $( allGlobalVar[ i ] );
                var inputName  = global.attr( "name" );
                var inputValue = global.val();
                
                saveVar[ layer ].global[ inputName ] = inputValue;
            }
            
            var allNodeVar   = $( "#layerConf" ).find( "#node tbody input" );
            for( var i = 0 ; i < allNodeVar.length ; i++ )
            {
                var global = $( allNodeVar[ i ] );
                var inputName  = global.attr( "name" );
                var inputValue = global.val();
                
                saveVar[ layer ].node[ inputName ] = inputValue;
            }
            
            $( "#layerConf" ).dialog( "close" );
        });
        
        $( "#btn_lc_cn" ).click( function(){
            $( "#layerConf" ).dialog( "close" );
        });
    });
    
    function openDialog( name , object )
    {
        $( "#layerConf" ).find( "input:hidden[name=layer]" ).val( name );
        
        $( "#layerConf" ).find( "#global tbody" ).html( tableFill( object.global ) );
        $( "#layerConf" ).find( "#node tbody" ).html( tableFill( object.node ) );
        $( "#layerConf" ).dialog( "open" );
    }
    
    function tableFill( object )
    {
        var txt = "";
        
        for( var name in object )
        {
            var value = object[ name ];
            
            txt += "<tr>";
            txt += "<td>"+ name +"</td>";
            txt += "<td>";
            txt += "<input type='text' name='"+ name +"' value='"+ value +"' />";
            txt += "</td>";
            txt += "</tr>";
        }
        
        return txt;
    }
</script>