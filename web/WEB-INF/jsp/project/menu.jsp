<ul class="nav navbar-nav">
    <li class="dropdown">
        <a id="composeButton" class="dropdown-toggle" data-toggle="dropdown">
            New <b class="caret"></b>
        </a>
        
        <ul class="dropdown-menu">
            <li><a id="fileNew" href="#">File</a></li>
            <li><a id="dirNew" href="#">Directory</a></li>
        </ul>
    </li>
    
    <li>
        <a id="buttonOpenProject" class="dialogify" href="/IDEA4WSN/project/list">Open Project</a>
    </li>
    
    <li>
        <a id="buttonSaveFile">Save</a>
    </li>
    
    <li>
        <a id="buttonSuggestions">Suggestions</a>
    </li>
    
    <li>
        <a id="buttonEvaluate" class="dropdown-toggle" data-toggle="dropdown"> <!-- class="dialogify" href="/IDEA4WSN/project/evaluate" -->
            Evaluate <b class="caret"></b>
        </a>
        
        <ul class="dropdown-menu">
            <li><a id="menuEvaluateApp" 
                   class="dialogify"
                   href="/IDEA4WSN/project/${projectId}/evaluate/application">Application</a></li>
            <li><a id="menuEvaluateFunctions" 
                   class="dialogify" 
                   href="/IDEA4WSN/project/${projectId}/evaluate/function">Two or More Functions</a></li>
            <li><a id="menuEvaluateNet" 
                   class="dialogify" 
                   href="/IDEA4WSN/project/${projectId}/evaluate/network">Network</a></li>
            <li><a id="menuEvaluateBoth" 
                   class="dialogify" 
                   href="/IDEA4WSN/project/${projectId}/evaluate/both">Application + Network</a></li>
        </ul>
    </li>
</ul>