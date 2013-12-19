/*********************************************************************
 * *******************************************************************
 * 
 *                               TYPE
 * 
 * *******************************************************************
 *********************************************************************/

var js_tree_types = new Object();
js_tree_types.valid_children   = [ "project" ];  // o primeiro nó tem que ser um projeto
js_tree_types.types = 
        { 
            project   : project_type   , 
            directory : directory_type 
        };
        
/**
 * Adiciona um novo tipo no JS Tree
 * 
 * @param {String} name    nome do tipo
 * @param {Object} value   configuração do tipo
 */
function addTreeType( name , value )
{
    js_tree_types.types[ name ] = value;
    
    var projectVC = js_tree_types.types.project.valid_children;
    projectVC.push( name );
    
    var dirVC = js_tree_types.types.directory.valid_children;
    dirVC.push( name );
};

/*********************************************************************
 * *******************************************************************
 * 
 *                          CONTEXT MENU
 * 
 * *******************************************************************
 *********************************************************************/



/*********************************************************************
 * *******************************************************************
 * 
 *                          APPLICATION
 * 
 * *******************************************************************
 *********************************************************************/

var current_project = "";

/**
 * Mostra para o usuário que ele tentou duplicar um node no JS Tree
 * 
 * @param {String} n      Qual foi o nó que ele tentou duplicar
 * @param {String} p      A onde ele tentou duplicar
 * @param {String} f      Qual foi a acão que ele tentou fazer
 */
function notAcceptDuplicateNode( n , p , f )
{
    alert( "Duplicate node `" + n + "`!" );
}

/**
 * Abre um projeto no JS Tree
 * 
 * @param {String} project_id
 * @returns
 */
function openProject( project_id )
{
    current_project = project_id;
    
    $( "#jstree_project" ).jstree({
        "core"   : { "initially_open" : [ "root" ] } ,
        "themes" : { "theme" : "apple", "dots" : true, "icons" : true  } ,
        "unique" : { "error_callback" : notAcceptDuplicateNode } ,
        "types"  : js_tree_types ,
        
        "html_data" :
        {
            "ajax" : { "url" : "/IDEA4WSN/project/open/" + project_id }
        } ,
        
        // the `plugins` array allows you to configure the active plugins on this instance
        "plugins" : [ "themes" , "html_data" , "ui" , "crrm" , "hotkeys" , "contextmenu" , "unique" ]
    });
    
    //document.title = '[IDEA4WSN] Project ' + project_id;
    
    // INSTANCES
    // 1) you can call most functions just by selecting the container and calling `.jstree("func",`
    setTimeout(function () { $("#jstree_project").jstree("set_focus"); } , 500 );
}

function initJsTree()
{
    $( "#jstree_project" ).jstree({
        "core"   : { "initially_open" : [ "root" ] } ,
        "themes" : { "theme" : "apple", "dots" : true, "icons" : true  } ,
        "unique" : { "error_callback" : notAcceptDuplicateNode } ,
        "types"  : js_tree_types ,
        
        // the `plugins` array allows you to configure the active plugins on this instance
        "plugins" : [ "themes" , "html_data", "ui" , "crrm" , "hotkeys" , "contextmenu" , "unique" ]
    });
}



/*********************************************************************
 * *******************************************************************
 * 
 *                          INIT
 * 
 * *******************************************************************
 *********************************************************************/

// When the project is initilize
$( function ()
{
    initJsTree();
    
//    $('#buttonOpenProject').click( function (e) {
//        openProject( "/home/avld/Aulas" );
//    });
    
//    $('#buttonReloadProject').click( function (e) {
//        if( current_project != null )
//        {
//            openProject( current_project );
//        }
//    });
});