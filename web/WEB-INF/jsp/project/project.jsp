<!DOCTYPE html>
<html>
    
  <head>
    <title>[ IDEA4WSN ] Project</title>
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
    <script src="IDEA4WSN/js/app.tab.js"></script>
    <script src="IDEA4WSN/js/type/project.type.js"></script>
    <script src="IDEA4WSN/js/type/directory.type.js"></script>
    <script src="IDEA4WSN/js/app.jstree.js"></script>
    
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
    <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
    
    <script>
        jQuery( function( $ )
        {
           $( '#MySplitter' )
           .width( $( '#main' ).width() )
           .height( $( '#main' ).height() )
           .split( { orientation:'vertical' , limit:100 , position:'20%' } );

           $( '.nav-pills, .nav-tabs' ).tabdrop();
        });
    </script>
	
    <link href="IDEA4WSN/css/bootstrap.min.css" rel="stylesheet" />
    <link href="IDEA4WSN/css/jquery.splitter.css" rel="stylesheet" />
    <link href="IDEA4WSN/css/app.tree.css" rel="stylesheet" />
    <link href="IDEA4WSN/css/tabdrop.css" rel="stylesheet" />
    
    <style type="text/css">

      /* Sticky footer styles
      -------------------------------------------------- */

      html,
      body
      {
        height: 100%;
        /* The html and body elements cannot have any padding or margin. */
      }


      /* Wrapper for page content to push down footer */
      #wrap
      {
        min-height: 100%;
        height: auto !important;
        height: 100%;
        
        /* Negative indent footer by it's height */
        margin: 0 auto -60px;
      }

      #composeButton ,
      #buttonOpenProject ,
      #buttonReloadProject
      {
          cursor: pointer;
      }
	  
      .nav-tabs > li .close {
            margin: -2px 0 0 10px;
            font-size: 18px;
        }
        .marginBottom {
            margin-bottom :1px !important;
        }
        .operationDiv {
            padding:5px 10px 5px 5px;
        }
        .operationDivWrapper {
            margin-top:-1px;
        }
        .leftMenu {
            height :70%;
            background-color: #E6E6E6;
            border-right: 2px solid #BFBFBF;
        }
    </style>

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
          <a class="navbar-brand" href="/IDEA4WSN/project/index" target="_blank">IDEA4WSN</a>
        </div>
        
        <div class="navbar-collapse collapse">
            
          <ul class="nav navbar-nav">
            <li><a id="composeButton">New</a></li>
            <li><a id="buttonOpenProject">Open Project</a></li>
            <li><a href="#contact">Evaluate</a></li>
          </ul>
          
          <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">
				  <span class="glyphicon glyphicon-user"></span> 
				  Antônio Dâmaso <b class="caret"></b>
				  </a>
              <ul class="dropdown-menu">
                <li><a href="#">Setting</a></li>
                <li class="divider"></li>
                <li><a href="#">Logout</a></li>
              </ul>
            </li>
          </ul>
          
        </div><!--/.nav-collapse -->
      </div>
    </div>

	<!--------------------- BODY ---------------------------->
	<!--------------------- BODY ---------------------------->
	<!--------------------- BODY ---------------------------->

	<div id="main" style="width:100%; height: 92%;">

      <div id="MySplitter">
          
          <div>
              <div align="right">
                  <span class="glyphicon glyphicon-refresh" id="buttonReloadProject"></span>
              </div>
              
              <hr style="margin: 1px 0;" />
              <div id="jstree_project" class="demo" style="height: 92%;">
                  ${projectJsTree}
              </div>
          </div>
          
          <div id="tabs" class="tabbable">
              
              <ul id="myTab" class="nav nav-tabs">
                  
                <li class="active">
                    <a href="#home" data-toggle="tab">
                        <button class="close closeTab" type="button" >×</button>
                        Home
                    </a>
                </li>
                
              </ul>

              <!-- Tab panes -->
              <div class="tab-content">
                <div class="tab-pane active" id="home">...</div>
              </div>
                
          </div>
		  
	  </div>

    </div>
    
  </body>
</html>
