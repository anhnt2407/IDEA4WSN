<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Suggestions to File ${file}</h2>
<div>
    <table>
    
        <thead>
            <tr>
                <td>Line</td>
                <td>Suggestion</td>
            </tr>
        </thead>
        
        <tbody>
            <c:choose>
                <c:when test="${not suggestionMap.isEmpty()}">
                    <c:forEach items="${suggestionMap}" var="entry">
                        <tr>
                            <td>Line ${entry.key}</td>
                            <td>
                                <c:forEach items="${entry.value}" var="value">
                                    ${value}<br />
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                        
                <c:otherwise>
                        <tr>
                            <td colspan="2">No suggestions.</td>
                        </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
        
    </table>
</div>