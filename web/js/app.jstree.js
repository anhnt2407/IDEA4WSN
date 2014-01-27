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

/**
 * Esta função cria um menu de acordo com o elemento clicado.
 * 
 * @param {node} $node
 * @returns {menu}
 */
function tree_context_menu( $node )
{
    // Cria o menu comum a todos os Nodes
    var items =
            {
                "Open":
                {
                    "label": "Open" ,
                    "_disabled" : false ,
                    "action": function ( obj ) 
                              {
                                  var name = obj.context.text;
                                  var path = obj.context.pathname;
                                  
                                  // Nao permitir que pastas sejam abertas nas abas
                                  if( !tree_isDirectory( obj ) )
                                  {
                                     openFile( name , path );
                                  }
                              }
                } ,
                // -------------- Insert a separator before the item
                "Cut":
                {
                    "label": "Cut" ,
                    "separator_before"  : true ,
                    "action": function ( obj ) 
                              {
                                  $copy_or_cut = 2;
                                  this.cut( obj ); 
                              }
                } ,
                "Copy":
                {
                    "label": "Copy",
                    "action": function ( obj ) 
                              { 
                                  $copy_or_cut = 1;
                                  this.copy( obj ); 
                              }
                } ,
                "Paste":
                {
                    "label": "Paste",
                    "action": function ( obj ) 
                              {
                                  this.paste( obj ); 
                              }
                } ,
                "Duplicate":
                {
                    "label": "Duplicate" ,
                    "action": function ( obj ) 
                              {
                                  projectFileDuplicate();
                              }
                } ,
                // -------------- Insert a separator before the item
                "Create":
                {
                    "label": "Create a file" ,
                    "separator_before"  : true ,
                    "action": function ( obj ) 
                              { 
                                  projectFileCreate( false ); 
                              }
                } ,
                "Create_Dir":
                {
                    "label": "Create a Diretory",
                    "action": function ( obj ) 
                              { 
                                  projectFileCreate( true ); 
                              }
                } ,
                "Rename":
                {
                    "label": "Rename",
                    "action": function ( obj ) 
                              { 
                                  projectFileRename(); 
                              }
                } ,
                "Delete":
                {
                    "label": "Delete",
                    "action": function ( obj ) 
                              { 
                                  projectFileDelete();
                              }
                } ,
                // -------------- Insert a separator before the item
                "Download":
                {
                    "label": "Donwload" ,
                    "separator_before"  : true ,
                    "action": function ( obj ) 
                              { 
                                  var $URL_DOWN = "/IDEA4WSN/storage/" + $project_storage + "/file/download";
                                  $URL_DOWN = $URL_DOWN + "?path=" + obj.find( "a" ).attr( "href" );
                                  
                                  window.open( $URL_DOWN );
                              }
                } ,
                "Upload":
                {
                    "label": "Upload" ,
                    "action": function ( obj ) 
                              {
                                  var PROP_URL = "/IDEA4WSN/storage/" + $project_storage + "/file/upload";
                                  PROP_URL = PROP_URL + "?path=" + obj.find( "a" ).attr( "href" );
                                  
                                  showDialogUrl( PROP_URL );
                              }
                } ,
                // -------------- Insert a separator before the item
                "Properties":
                {
                    "label": "Properties" ,
                    "separator_before"  : true ,
                    "action": function ( obj ) 
                              {
                                  var PROP_URL = "/IDEA4WSN/storage/" + $project_storage + "/file/properties";
                                  PROP_URL = PROP_URL + "?path=" + obj.find( "a" ).attr( "href" );
                                  
                                  showDialogUrl( PROP_URL );
                              }
                }
            };
            
    // ----------------------------------------------------------
    // Remove/desabilita um elemento do Menu de acordo com o tipo 
    // do Node.
    // ----------------------------------------------------------
    var node_jQuery = $( $node );
    
    if ( tree_isDirectory( node_jQuery ) )
    {
        items.Open._disabled = true;
        
        //TODO: futuramente o servidor ira copiar uma pasta!
        items.Copy._disabled = true;
        items.Duplicate._disabled = true;
        
        //TODO: futuramente o servidor ira fazer o download de uma pasta!
        items.Download._disabled = true;
    }
    else if ( tree_isProject( node_jQuery ) )
    {
        items.Open._disabled = true;
        items.Download._disabled = true;
        
        items.Cut._disabled  = true;
        items.Copy._disabled = true;
        items.Paste._disabled = ( $copy_or_cut === 0 );
        items.Duplicate._disabled = true;
        
        items.Rename._disabled = true;
        items.Delete._disabled = true;
    }
    else
    {
        items.Paste._disabled = ( $copy_or_cut === 0 );
        
        items.Create._disabled = true;
        items.Create_Dir._disabled = true;
        items.Upload._disabled = true;
    }
    
    // --------------- Select the node
    $( ".jstree-clicked" ).removeClass( "jstree-clicked" );
    $( $node ).children( 'a' ).addClass( "jstree-clicked" );
    
    $node_selected = $( $node );
    
    return items;
};

function tree_file_open( event )
{
    event.preventDefault();

    var name = event.target.text;
    var path = event.target.pathname;
    var parent = $( event.target ).parent();
    
    // Nao permitir que pastas sejam abertas nas abas
    if( !tree_isDirectory( parent ) )
    {
        openFile( name , path );
    }
}

//////////////////////////////////////////////////////////////

function tree_file_move( node , newPath )
{
    
}

/*********************************************************************
 * *******************************************************************
 * 
 *                          COMMONS
 * 
 * *******************************************************************
 *********************************************************************/

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
 * Verifica se o elemento no JS Tree é um diretorio.
 * 
 * @param {Node} element
 * @returns {Boolean}
 */
function tree_isDirectory( element )
{
    return element.attr( 'rel' ) === 'directory';
}

/**
 * Verifica se o elemento no JS Tree é um projeto.
 * 
 * @param {Node} element
 * @returns {Boolean}
 */
function tree_isProject( element )
{
    return element.attr( 'rel' ) === 'project';
}

/**
 * Abre um projeto no JS Tree
 * 
 * @param {String} project_id
 * @returns
 * @deprecated NAO É USADO!
 */
function tree_project_open( project_id )
{
    $project_current = project_id;    // app.project.js
    
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

/**
 * Irá orderar de A-Z os elementos na JSTree. Além disso, As pastas 
 * ficarão em cima dos arquivos.
 * 
 * @param {node} a
 * @param {node} b
 * @returns {Number}
 */
function tree_sort( a , b )
{
    var jq_a = $( a );
    var jq_b = $( b );
    
    if( tree_isDirectory( jq_a ) && !tree_isDirectory( jq_b ) )
    {
        return -1;
    }
    else if( !tree_isDirectory( jq_a ) && tree_isDirectory( jq_b ) )
    {
        return 1;
    }
    else
    {
        return this.get_text(a) > this.get_text(b) ? 1 : -1 ;
    }
}

/*********************************************************************
 * *******************************************************************
 * 
 *                          INIT
 * 
 * *******************************************************************
 *********************************************************************/

$node_selected = null;
$copy_or_cut = 0;      //NOTHING = 0 , COPY = 1 e CUT = 2

// When the project is initilize
$( function ()
{
    var jsTreeProject = $( "#jstree_project" );
    
    if( jsTreeProject.length === 0 )
    {
        return ;
    }
    
    jsTreeProject.jstree({
        "core"   : { "initially_open" : [ "root" ] } ,
        "themes" : { "theme" : "apple", "dots" : true, "icons" : true  } ,
        "unique" : { "error_callback" : notAcceptDuplicateNode } ,
        //"types"  : js_tree_types ,
        "contextmenu" : {items : tree_context_menu } ,
        "sort"   : tree_sort ,
        
        // the `plugins` array allows you to configure the active plugins on this instance
        "plugins" : [ "themes" , "html_data", "ui" , "crrm" 
                    , "hotkeys" , "contextmenu" , "unique" 
                    , "sort" ]
    });
    
    var rootNode = $( "#root" );
    
    $project_current = rootNode.attr( "project" );  // encontrar o identificador do projeto
    $project_storage = rootNode.attr( "storage" );  // encontrar o identificador do storage
    
    jsTreeProject.delegate( "a" , "dblclick" , tree_file_open ); // abrir um arquivo
    //jsTreeProject.bind( "rename.jstree" , tree_file_rename );
    //jsTreeProject.bind( "remove.jstree" , tree_file_remove );
    //jsTreeProject.bind( "create.jstree" , tree_file_new );
    
    jsTreeProject.bind( "paste.jstree" , function ( e , d ) 
    { 
        //$( "#home" ).html( print(  ) );
        var dir = $( d.rslt.obj );
        
        for( var i = 0 ; i < d.rslt.nodes.length ; i++ )
        {
            var file = $( d.rslt.nodes[i] );
            projectFileMove( $copy_or_cut , dir , file );
        }
        
        $copy_or_cut = 0;
    } );
    
    jsTreeProject.click( function ()
    {
        $node_selected = $( ".jstree-clicked" ).parent();
    });
    
    // -----------------------------------
    //                MENU
    
    $( "#fileNew" ).click( function () 
    { 
        projectFileCreate( false ); 
    });
    
    $( "#dirNew" ).click( function () 
    { 
        projectFileCreate( true ); 
    });
});