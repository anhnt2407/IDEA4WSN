<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form>
    <label>
        Network File
        <select name="file" size="3">
            <c:forEach items="${fileList}" var="file">
                <option value="${file}">${file}</option>
            </c:forEach>
        </select>
    </label>
    
    <label>
        <input type="checkbox" name="reliability" value="true" checked /> 
        Evaluate Reliability
    </label>
    
    <input type="button" value="Evaluate" />
</form>