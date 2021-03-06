<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Evaluate a Network</h2>

<form id="formEvaluate">
    <c:import url="network_form.jsp" />
    
    <input type="button" name="submit" value="Evaluate" />
</form>

<script>
    $( function() {
        $( "#formEvaluate input:button" ).click( function() {
            var stop  = $( "#StopCriteria" ).val();
            var extra = $( "#StopCriteriaValue" ).val();
            var file  = $( "#networkFile" ).val();
            var reliability = $( "#reliability" ).prop('checked');
            
            // ----------
            
            $.post( "/IDEA4WSN/storage/${storageId}/evaluate/network" 
                  , { stop_criteria       : stop 
                    , stop_criteria_value : extra 
                    , net_file            : file 
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