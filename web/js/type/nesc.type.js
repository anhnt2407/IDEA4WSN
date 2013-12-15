// --------------------------------- CRIAR O TIPO
var nesc_type = 
    {
        "max_children"   : -2 ,     //-1 = unlimited , -2 = disable max_children
        "max_depth"      : -2 ,     //-1 = unlimited , -2 = disable max_children
        "valid_children" : "none" , // all , none
        "icon" :
                {
                    "image" : "img/type/nesc_type.png"
                },

        // Bound functions - you can bind any other function here (using boolean or function)
        "select_node"    : true ,
        "open_node"      : true ,
        "close_node"     : true ,
        "create_node"    : true ,
        "delete_node"    : true
    };

// --------------------------------- ADICIONAR TIPO NO TYPE
addTreeType( "nesc" , nesc_type );


// --------------------------------- ADICIONAR NO CONTEXT MENU (como fazer isso?)