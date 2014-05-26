<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <div>
        Evaluate all application? <br />
        <label>
            <input type="radio" name="all_app" value="true" checked />
            Yes!
        </label>
        
        <label>
            <input type="radio" name="all_app" value="false" />
            No, I want to evaluate a function!
        </label>
    </div>
    
    <div id="evaluateFunction">
        <label>
            Function name:
            <select id="formAppFunction" name="function" disabled>
                <c:forEach items="${functionsMap}" var="entry">
                    <optgroup label="${entry.key}">

                        <c:forEach items="${entry.value}" var="function">
                            <option value="${function}" file="${entry.key}">
                                ${function}
                            </option>
                        </c:forEach>
                    </optgroup>
                </c:forEach>
            </select>
        </label>
    </div>
    
    <script>
     $( function() {
        $( "#formEvaluate input:radio" ).change( function () {
            var value = $( "#formEvaluate input[name='all_app']:checked" ).val();
            var status = value === "true" ? true : false;
            
            $( "#formAppFunction" ).prop( 'disabled' , status );
        });
     });
    </script>
