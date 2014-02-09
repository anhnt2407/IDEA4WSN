<div id="main" style="width:100%; height: 92%;">
    
      <div id="MySplitter">
          
          <div>
              <div align="right">
                  <span class="glyphicon glyphicon-refresh" id="buttonReloadProject" onclick="openProject( ${projectId} )"></span>
              </div>
              
              <hr style="margin: 1px 0;" />
              <div id="jstree_project" class="demo" style="height: 92%;">
                  ${projectJsTree}
              </div>
          </div>
          
          <div id="tabs" class="tabbable">
              <ul id="myTab" class="nav nav-tabs">
              </ul>
              <!-- Tab panes -->
              <div class="tab-content">
                <div class="tab-pane active" id="home"></div>
              </div>
                
          </div>
          
      </div>
              
</div>
