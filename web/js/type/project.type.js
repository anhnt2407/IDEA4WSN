// --------------------------------- CRIAR O TIPO
var project_type = 
    {
        "max_children"   : -1 ,     //-1 = unlimited , -2 = disable max_children
        "max_depth"      : -1 ,     //-1 = unlimited , -2 = disable max_children
        "icon" :
        {
            "image" : "img/type/project_type.png"
        } ,
        
        "valid_children" : [ "default" ] ,
        "hover_node" : false ,
        "select_node" : function () { return false; }
    };