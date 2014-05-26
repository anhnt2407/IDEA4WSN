<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Evaluate Application and Network</h2>

<form id="formEvaluate">
    <c:import url="application_form.jsp" />
    <hr />
    
    <c:import url="network_form.jsp" />
    
    <input type="button" name="submit" value="Evaluate" />
</form>

<script>
    $( function() {
        $( "#formEvaluate input:button" ).click( function() {
            var app      = $( "#formEvaluate input[name='all_app']:checked" ).val();
            var func     = $( "#formAppFunction" ).val();
            var app_file = $( "#formAppFunction option:selected" ).attr( "file" );
            
            var stop  = $( "#StopCriteria" ).val();
            var extra = $( "#StopCriteriaValue" ).val();
            var net_file    = $( "#networkFile" ).val();
            var reliability = $( "#reliability" ).prop('checked');
            
            // ----------
            
            $.post( "/IDEA4WSN/storage/${storageId}/evaluate/both" 
                  , { application  : app
                    , app_file     : app_file
                    , functionName : func
                    , stop_criteria       : stop 
                    , stop_criteria_value : extra 
                    , net_file            : net_file   
                    , reliability         : reliability } )
             .done(function() { 
                 notification( "Your network will be evaluated. Wait a minute." , "success" ); 
                })
             .fail(function() { 
                 notification( "We had a error with your evaluation, sorry." , "error" ); 
                });
            
            $( "#dialog" ).dialog( "close" );
        });
    });
</script>