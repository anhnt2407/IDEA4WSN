// --------------------------------- CRIAR O TIPO
var directory_type = 
    {
        "max_children"   : -1 ,     //-1 = unlimited , -2 = disable max_children
        "max_depth"      : -1 ,     //-1 = unlimited , -2 = disable max_children
        "icon" :
        {
            "image" : "img/type/directory.type.png"
        } ,
        
        "valid_children" : [ "default" , "directory" ] ,
        "hover_node" : false ,
        "select_node" : function () { return false; }
    };