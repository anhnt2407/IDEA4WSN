<div id="${id}" file="${path}" storage="${storage}" class="container">
    <div class="row" style="margin-bottom: 5px; margin-top: 10px;">
        <div class="col-xs-6 col-md-4">
            <input type="button" class="btn btn-default" name="conf" value="Configuration" />
            <input type="hidden" name="id" value="topology01" />
        </div>

        <div class="col-xs-6 col-md-4 btn-group" data-toggle="buttons">
            <label class="btn btn-primary active">
                <input type="radio" name="action" value="NODE" checked />
                Sensor node
            </label>

            <label class="btn btn-primary">
                <input type="radio" name="action" value="SINK" />
                Base Station
            </label>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-9 col-md-7">
            <canvas width="600" height="400">
                Yout browers doest not supported canvas tag!
            </canvas>
        </div>

        <div class="col-xs-3 col-md-3">

            <div class="input-group">
                <span class="input-group-addon">Node ID</span>
                <input type="text" name="nodeId" class="form-control" readonly />
            </div>

            <table class="table table-condensed">
                <thead>
                    <tr>
                        <th>Porperty Name</th>
                        <th>Value</th>
                    </tr>
                </thead>

                <tbody>
                    <!-- DATA -->
                </tbody>
            </table>

            <div>
                <input type="button" name="delete" value="Delete" class="btn btn-danger" />
                <input type="button" name="save" value="Save"  class="btn btn-success" />
            </div>
        </div>
    </div>

</div>

<div id="${id}_cd" title="Configuration"></div>

<script>
    $( function ()
    {
        var idJQuery = $( "#${id}" );
        idJQuery.wsn();
        idJQuery.setTopology( ${data} );
       
        // -------------------------------------
 
        var valueOld = hex_md5( idJQuery.getTopology() );

        idJQuery.bind( "isValueChanged" , function ( e )
        {
            var valueNew = hex_md5( idJQuery.getTopology() );
            e.result = ( valueOld !== valueNew );
        });

       idJQuery.bind( "saveFile" , function ( e )
       {
           var data = idJQuery.getTopology();
           saveFile( e.path , data );
       });
        
       idJQuery.children().bind( 'keydown' , 'alt+s' , saveTabSelected );
       
       // -------------------------------------
       
       $( "#${id}_cd" ).dialog( {
            autoOpen: false ,
            height  : 600  ,
            width   : 550  ,
            modal   : true 
        });
        
        idJQuery.find( "input:button[name=conf]" ).click( function () {
            $( "#${id}_cd" ).dialog( "open" );
            $( "#${id}_cd" ).html( "" );
            $( "#${id}_cd" ).load( "/IDEA4WSN/project/configuration/network?divId=${id}" , function() {} );
        });
    });
</script>