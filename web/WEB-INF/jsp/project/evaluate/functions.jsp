<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Evaluate two or more functions</h2>

<form id="formEvaluate">
    <div>
        Please, select two or more functions: <br />

        <c:forEach items="${functionsMap}" var="entry">
            <fieldset>
                <legend>${entry.key}</legend>
                
                <c:forEach items="${entry.value}" var="function">
                    <label>
                        <input type="checkbox" value="${function}" file="${entry.key}" />
                        ${function}
                    </label> <br />
                </c:forEach>
            </fieldset>
        </c:forEach>
    </div>
    
    <input type="button" name="submit" value="Evaluate" />
</form>

<script>
    $( function() {
        $( "#formEvaluate input:button" ).click( function () {
            var func = $( "#formEvaluate input:checked" );
            
            if( func.length <= 1 )
            {
                notification( "You need select two or more functions." , "error" );
                return ;
            }
            
            var funcObj = [];
            for( var i = 0 ; i < func.length ; i++ )
            {
                var item = $( func[ i ] );
                var itemObj = { fileName     : item.attr( "file" ) 
                              , functionName : item.val()          };
                          
                funcObj.push( itemObj );
            }
            
            console.log( funcObj );
            
            $.post( "/IDEA4WSN/storage/${storageId}/evaluate/function" 
                  , {functions : funcObj 
                    , length   : funcObj.length } 
                  )
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
