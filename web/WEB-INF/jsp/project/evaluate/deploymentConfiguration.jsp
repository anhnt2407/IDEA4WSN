<div id="deployment" title="Deployment Configuration">
    <div id="app_layer" class="input-group">
        <span class="input-group-addon">Node No</span>
        <input type="text" name="number" value="21" class="form-control" />
    </div>
    
    <div id="app_layer" class="input-group">
        <span class="input-group-addon">Start Pos:</span>
        <input type="text" name="X" value="0" placeholder="X" class="form-control" />
        <input type="text" name="Y" value="0" placeholder="Y" class="form-control" />
    </div>
    
    <div id="app_layer" class="input-group">
        <span class="input-group-addon">Size:</span>
        <input type="text" name="width" value="200" placeholder="width" class="form-control" />
        <input type="text" name="height" value="200" placeholder="height" class="form-control" />
    </div>
    
    <div id="app_layer" class="input-group">
        <span class="input-group-addon">Sink Pos:</span>
        <input type="text" name="sinkX" value="100" placeholder="X" class="form-control" />
        <input type="text" name="sinkY" value="100" placeholder="Y" class="form-control" />
    </div>
    
    <input type="button" name="ok" value="OK" />
    <input type="button" name="cancel" value="Cancel" />
</div>

<script>
    var deploymentVar = {};
    
    $(function (){
        $( "#deployment" ).dialog( {
            autoOpen: false ,
            height  : 500  ,
            width   : 400  ,
            modal   : true 
        });
        
        $( "#deployment" ).find( "input:button[name=ok]" ).click( function(){
            deploymentVar = {};
            
            var allInputText = $( "#deployment" ).find( "input:text" );
            for( var i = 0 ; i < allInputText.length ; i++ )
            {
                var inputJQuery = $( allInputText[ i ] );
                var inputName   = inputJQuery.attr( 'name' );
                var inputValue  = inputJQuery.val();
                
                deploymentVar[ inputName ] = inputValue;
            }
            
            $( "#deployment" ).dialog( "close" );
        });
        
        $( "#deployment" ).find( "input:button[name=cancel]" ).click( function(){
            $( "#deployment" ).dialog( "close" );
        });
        
        $( "#sensorDeploy" ).find( "button" ).click( function(){
            $( "#deployment" ).dialog( "open" );
        });
        
        // ----------------------
        
        $( "#deployment" ).find( "input:button[name=ok]" ).click();
    });
</script>