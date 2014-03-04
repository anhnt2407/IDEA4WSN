<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    
  <head>
    <title>[ IDEA4WSN ] Project :: ${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="description" content="IDEA4WSN" />
    <meta name="author" content="UFPE - Centro de Informatica (CIn)" />
    <link rel="shortcut icon" href="favicon.png" />
    
    <link href="/IDEA4WSN/css/jquery-ui.css" rel="stylesheet">
    <link href="/IDEA4WSN/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/IDEA4WSN/css/jquery.splitter.css" rel="stylesheet" />
    <link href="/IDEA4WSN/js/themes/default/style.min.css" rel="stylesheet" />
    <!-- <link href="/IDEA4WSN/css/tabdrop.css" rel="stylesheet" /> -->
    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="/IDEA4WSN/js/jquery.min.js"></script>
    <script src="/IDEA4WSN/js/jquery-migrate.min.js"></script>
    <script src="/IDEA4WSN/js/jquery-ui.js"></script>
    <script src="/IDEA4WSN/js/jquery.cookie.js"></script>
    <script src="/IDEA4WSN/js/jquery.hotkeys.js"></script>
    <script src="/IDEA4WSN/js/jquery.splitter.js"></script>
    <script src="/IDEA4WSN/js/jquery.jstree.js"></script>
    <script src="/IDEA4WSN/js/notify.min.js"></script>
    <script src="/IDEA4WSN/js/plupload.full.min.js"></script> <!-- PluUpload -->
    <script src="/IDEA4WSN/js/moxie.min.js"></script>
    <script src="/IDEA4WSN/js/jcanvas.min.js"></script>
    
    <!-- Bootstrap -->
    <script src="/IDEA4WSN/js/bootstrap.min.js"></script>
    <!-- script src="/IDEA4WSN/js/bootstrap-dropdown.js"></script -->
    <!-- script src="/IDEA4WSN/js/bootstrap-tab.js"></script -->
    <!-- script src="/IDEA4WSN/js/bootstrap-tabdrop.js"></script -->
        
    <!-- Application -->
    <script src="/IDEA4WSN/js/project/file.create.js"></script>
    <script src="/IDEA4WSN/js/project/file.rename.js"></script>
    <script src="/IDEA4WSN/js/project/file.delete.js"></script>
    <script src="/IDEA4WSN/js/project/file.move.js"></script>
    <script src="/IDEA4WSN/js/project/network.js"></script>
    <script src="/IDEA4WSN/js/project/deployment.js"></script>
    
    <script src="/IDEA4WSN/js/app.util.js"></script>
    <script src="/IDEA4WSN/js/app.notification.js"></script>
    <script src="/IDEA4WSN/js/app.project.js"></script>
    <script src="/IDEA4WSN/js/app.tab.js"></script>
    <script src="/IDEA4WSN/js/type/project.type.js"></script>
    <script src="/IDEA4WSN/js/type/directory.type.js"></script>
    <script src="/IDEA4WSN/js/app.jstree.js"></script>
    <script src="/IDEA4WSN/js/hash.md5.js"></script>
    
    <script src="/IDEA4WSN/js/ace/ace.js" type="text/javascript" charset="utf-8"></script>
     <script src="/IDEA4WSN/js/ace/theme-monokai.js" type="text/javascript" charset="utf-8"></script>
         <script src="/IDEA4WSN/js/ace/theme-nesc.js" type="text/javascript" charset="utf-8"></script>

    
    <script>
        jQuery( function( $ )
        {
           $( '#MySplitter' )
           .width( $( '#main' ).width() )
           .height( $( '#main' ).height() )
           .split( { orientation:'vertical' , limit:100 , position:'20%' } );

           //$( '.nav-pills, .nav-tabs' ).tabdrop();
           
           $("#dialog").dialog(
            {
                autoOpen: false,
                modal: true,
                width: 600,
                height: 300,
                buttons:
                {
                    "Close": function()
                    {
                            $( this ).dialog( "close" );
                    }
                }
            });
            
            $( ".dialogify" ).on("click", function(e) {
                    e.preventDefault();
                    $("#dialog").html("");
                    $("#dialog").dialog("option", "title", "Loading...").dialog("open");
                    $("#dialog").load(this.href, function() {
                            $(this).dialog( "option" , "title" , $(this).find("h2").text() );
                            $(this).find("h2").remove();
                    });
            });
        });
    </script>

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

      .editor {
        position: absolute;
        width: 100%;
        height: 93%;
      }
      
      #composeButton ,
      #buttonSaveFile ,
      #buttonOpenProject ,
      #buttonReloadProject ,
      #buttonEvaluate
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
        
        canvas
        {
            border: #000 solid thin;
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
          <a class="navbar-brand" href="/IDEA4WSN/project/index">IDEA4WSN</a>
        </div>
        
        <c:import url="${menu}.jsp" />
          
        <div class="navbar-collapse collapse">
          
          <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">
				  <span class="glyphicon glyphicon-user"></span> 
				  ${username} <b class="caret"></b>
				  </a>
              <ul class="dropdown-menu">
                <li><a href="#">Settings</a></li>
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
    
    <c:import url="${body}.jsp" />
    
    <div id="dialog"></div>
    
    <div id="dialog-tabClose" title="Confirm">
        <p>
            <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
            Do you want save and/or close this tab?
        </p>
    </div>
    
  </body>
</html>
