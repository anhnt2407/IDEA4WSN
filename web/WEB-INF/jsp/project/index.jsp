<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <h2 class="form-signin-heading">Projects</h2>
    <!-- ACOES - ex. criar projeto -->
    <a href="/IDEA4WSN/project/create">new Project</a>

    <!-- LISTA PROJETOS -->
    <div class="table-responsive">
        <table class="table table-hover">

            <thead>
                <td>#</td>
                <td>Name</td>
                <td>Storage</td>
                <td>Path</td>
            </thead>

            <c:forEach items="${projectList}" var="project">
                <tr style="cursor: pointer;" onclick="openProjectWindow(${project.projectId});">
                    <td>${project.projectId}</td>
                    <td>${project.name}</td>
                    <td>${storageMap.get(project.storageId).name}</td>
                    <td>${project.directory}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
