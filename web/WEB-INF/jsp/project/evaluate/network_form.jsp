<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <label>
        Stop Criteria:
        <select id="StopCriteria" name="stop_criteria">
            <option value="FND" extra="">First Node Dead</option>
            <option value="HND" extra="">Half Node Dead</option>
            <option value="LND" extra="">Last Node Dead</option>
            <option value="PND" extra="20">Porcentage Node Dead (%)</option>
            <option value="time" extra="100">Time (seconds)</option>
        </select>
    </label>
    
    <label>
        Extra:
        <input id="StopCriteriaValue" type="text" name="stop_criteria_value" value="" />
    </label>
    
    <hr />
    
    <label>
        Network File (.wsn):
        <select id="networkFile" name="file">
            <c:forEach items="${fileList}" var="file">
                <option value="${file}">${file}</option>
            </c:forEach>
        </select>
    </label>
    
    <label>
        <input id="reliability" type="checkbox" name="reliability" value="true" checked /> 
        Evaluate Reliability
    </label>
    
    <script>
      $( function() {
        $( "#StopCriteria" ).change( function() {
             var extra = $( "#StopCriteria option:selected" ).attr( "extra" );
             $( "#StopCriteriaValue" ).val( extra );
        });
      });
    </script>