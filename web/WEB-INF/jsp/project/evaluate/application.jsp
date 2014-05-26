<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Evaluate a Application</h2>

<form id="formEvaluate">
    <c:import url="application_form.jsp" />
    
    <input type="button" name="submit" value="Evaluate" />
</form>

<script>
    $( function() {
        $( "#formEvaluate input:button" ).click( function () {
            var app  = $( "#formEvaluate input:checked" ).val();
            var func = $( "#formEvaluate select" ).val();
            var file = $( "#formEvaluate select option:selected" ).attr( "file" );
            
            $.post( "/IDEA4WSN/storage/${storageId}/evaluate/application" 
                  , { application  : app 
                    , app_file     : file
                    , functionName : func } )
             .done(function() { 
                 notification( "Your application will be evaluated. Wait a minute." , "success" ); 
                })
             .fail(function() { 
                 notification( "We had a error with your evaluation, sorry." , "error" ); 
                });
            
            $( "#dialog" ).dialog( "close" );
        });
    });
</script>
