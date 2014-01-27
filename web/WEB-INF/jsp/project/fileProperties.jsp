<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2 class="form-signin-heading">Properties</h2>

<!-- LISTA PROJETOS -->
<div class="table-responsive">
    <table class="table table-hover">

        <thead>
            <td>Name</td>
            <td>Value</td>
        </thead>

        <c:forEach items="${propertiesMap}" var="prop">
            <tr>
                <td>${prop.key}</td>
                <td>${prop.value}</td>
            </tr>
        </c:forEach>
    </table>
</div>