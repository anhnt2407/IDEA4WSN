<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    
    <fieldset>
        <legend>Layers</legend>

        <div id="app_layer" class="input-group">
          <span class="input-group-addon">Application Layer</span>

          <select name="application" class="form-control">
              <c:forEach items="${applications}" var="layer">
                  <option value="${layer}">${layer}</option>
              </c:forEach>
            <option>FROM CODE</option>
          </select>

          <span class="input-group-btn">
            <button class="btn btn-default" type="button">Properties</button>
          </span>
        </div>

        <div id="net_layer" class="input-group">
          <span class="input-group-addon">Network Layer</span>

          <select name="network" class="form-control">
            <c:forEach items="${networks}" var="layer">
                  <option value="${layer}">${layer}</option>
             </c:forEach>
          </select>

          <span class="input-group-btn">
            <button class="btn btn-default" type="button">Properties</button>
          </span>
        </div>

        <div id="mac_layer" class="input-group">
          <span class="input-group-addon">Link Layer</span>

          <select name="mac" class="form-control">
              <c:forEach items="${macs}" var="layer">
                  <option value="${layer}">${layer}</option>
              </c:forEach>
          </select>

          <span class="input-group-btn">
            <button class="btn btn-default" type="button">Properties</button>
          </span>
        </div>

    </fieldset>

    <br />
    
    <fieldset>
        <legend>Others</legend>

        <div id="sensorDeploy" class="input-group">
            <span class="input-group-addon">Deployment</span>

            <select name="deployment" class="form-control">
              <option value="random">Random</option>
              <option value="manual">None</option>
            </select>

            <span class="input-group-btn">
              <button class="btn btn-default" type="button">Properties</button>
            </span>
          </div>

    </fieldset>

    <input type="button" id="networkCreate" value="Create" />
    <input type="button" id="networkCancel" value="Cancel" />
</div>

<c:import url="layerConfiguration.jsp" />
<c:import url="deploymentConfiguration.jsp" />

<script>
    
    $( function (){
        
        $( "#networkCreate" ).click( function(){
            $( "#${divId}" ).setTopology( network_createTopology() );
            $( "#${divId}_cd" ).dialog( "close" );
        });

        $( "#networkCancel" ).click( function(){
            $( "#${divId}_cd" ).dialog( "close" );
        });
        
    });
    
    function network_createTopology()
    {
        var deploy = $( "#sensorDeploy" ).find( "select" ).val();
        deploymentVar.nodeDefault = layer_getNodeDefault();
        
        return { properties  : layer_getProperties() ,
                 variables   : layer_getVariable()   ,
                 nodes       : deployment.execute( deploy , deploymentVar ) ,
                 nodeDefault : deploymentVar.nodeDefault ,
                 counter     : deploymentVar.number
               };
    }
    
    function layer_getNodeDefault()
    {
        var nodeDefault = {};
        
        for( var p in saveVar.application.node )
        {
            nodeDefault[ p ] = saveVar.application.node[ p ];
        }
        
        for( var p in saveVar.network.node )
        {
            nodeDefault[ p ] = saveVar.network.node[ p ];
        }
        
        for( var p in saveVar.mac.node )
        {
            nodeDefault[ p ] = saveVar.mac.node[ p ];
        }
        
        return nodeDefault;
    }
    
    function layer_getVariable()
    {
        var global = {};
        
        for( var p in saveVar.application.global )
        {
            global[ p ] = saveVar.application.global[ p ];
        }
        
        for( var p in saveVar.network.global )
        {
            global[ p ] = saveVar.network.global[ p ];
        }
        
        for( var p in saveVar.mac.global )
        {
            global[ p ] = saveVar.mac.global[ p ];
        }
        
        return global;
    }
    
    function layer_getProperties()
    {
        var properties = $.extend( {} , layer_getProtocols() );
        properties[ "size" ] = 1;
        //TODO: configuracao da confiabilidade da rede
        
        return properties;
    }
    
    function layer_getProtocols()
    {
        return {
                 "application_layer" : $( "#app_layer" ).find( "select" ).val() ,
                 "network_layer"     : $( "#net_layer" ).find( "select" ).val() ,
                 "mac_layer"         : $( "#mac_layer" ).find( "select" ).val()
               };
    }
    
</script>