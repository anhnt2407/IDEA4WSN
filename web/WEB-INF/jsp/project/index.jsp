<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    
  <head>
    <title>[ IDEA4WSN ] Project :: Index</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="description" content="IDEA4WSN" />
    <meta name="author" content="UFPE - Centro de Informatica (CIn)" />
    <link rel="shortcut icon" href="favicon.png" />
    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="IDEA4WSN/js/jquery.min.js"></script>
    <script src="IDEA4WSN/js/jquery-migrate.min.js"></script>
    <script src="IDEA4WSN/js/jquery.cookie.js"></script>
    <script src="IDEA4WSN/js/jquery.hotkeys.js"></script>
    <script src="IDEA4WSN/js/jquery.splitter.js"></script>
    <script src="IDEA4WSN/js/jquery.jstree.js"></script>
    
    <!-- Bootstrap -->
    <script src="IDEA4WSN/js/bootstrap.min.js"></script>
    <script src="IDEA4WSN/js/bootstrap-dropdown.js"></script>
    <script src="IDEA4WSN/js/bootstrap-tab.js"></script>
    <script src="IDEA4WSN/js/bootstrap-tabdrop.js"></script>
    
    <!-- Application -->
    <script src="IDEA4WSN/js/app.project.js"></script>
    
    <link href="IDEA4WSN/css/bootstrap.min.css" rel="stylesheet" />

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    
  </head>
  <body style="">
      <!--------------------- MENU ---------------------------->
      <!--------------------- MENU ---------------------------->
      <!--------------------- MENU ---------------------------->
      
      <div class="navbar navbar-default" role="navigation" style="margin-bottom: 2px;">
      <div class="container">
        
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/IDEA4WSN/project/index">IDEA4WSN</a>
        </div>
        
        <div class="navbar-collapse collapse">
          
          <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">
				  <span class="glyphicon glyphicon-user"></span> 
				  ${username} <b class="caret"></b>
				  </a>
              <ul class="dropdown-menu">
                <li><a href="#">Setting</a></li>
                <li class="divider"></li>
                <li><a href="/IDEA4WSN/logout">Logout</a></li>
              </ul>
            </li>
          </ul>
          
        </div><!--/.nav-collapse -->
      </div>
    </div>
      
      <!--------------------- BODY ---------------------------->
      <!--------------------- BODY ---------------------------->
      <!--------------------- BODY ---------------------------->
      
    <div class="container form-signin">
        <h2 class="form-signin-heading">Projects</h2>
        <!-- ACOES - ex. criar projeto -->
        <a href="">new Project</a>
        
        <hr />
        
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
    
  </body>
</html>
