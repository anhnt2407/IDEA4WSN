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

function tree_file_rename( event , data )
{
    event.preventDefault();

    var old  = data.rslt.old_name;
    var name = data.rslt.new_name;
    var path = $( data.rslt.obj ).children( 'a' ).attr( 'href' );
    
    // Não pode permitir que a pasta ROOT seja alterada
    if( data.rslt.obj.attr( 'id' ) === 'root' )
    {
        $.jstree.rollback( data.rlbk );
        notification( "You can not rename the root folder!" , "error" );
        return ;
    }
    
    // Atualizar no servidor o nome do arquivo
    $.ajax
    ( {
        async: false ,
        type : 'POST' ,
        url  : "/IDEA4WSN/storage/" + $project_storage + "/file/rename" ,
        data : { "path" : path , "name" : name } ,
        success : postResult
    } );
    
    // Atualizar o HREF para o novo caminho do arquivo ou pasta
    var pathNew = path.substr( 0 , path.length - old.length ) + name;
    $( data.rslt.obj ).children( 'a' ).attr( 'href' , pathNew );
    
    // se for uma pasta, é necessário atualizar os seus filhos
    if( tree_isDirectory( data.rslt.obj ) )
    {
        _tree_rename_children( path , pathNew , data.rslt.obj );
    }
    
    notification( "File/Directory renamed!" , "success" );
    
    //TODO: quando uma aba esta aberta do arquivo?
}

function _tree_rename_children( path , pathNew , element )
{
    var $children = element.children( 'ul' ).children( 'li' );
    alert( "Lenght: " + $children.length );

    $.each( $children , function ( index , value )
    {
        var $value = $( value );
        var a = $value.children( 'a' );
        var a_href = a.attr( 'href' );
        
        if( startsWith( a_href , path ) )
        {
            var a_href_new = pathNew + a_href.substr( path.length , a_href.length - path.length );
            a.attr( 'href' , a_href_new );
        }
        
        if( tree_isDirectory( $value ) )
        {
            _tree_rename_children( path , pathNew , value );
        }
    });
}

//////////////////////////////////////////////////////////////

function tree_file_remove( event , data )
{
    event.preventDefault();

    // Confirmar se deseja mesmo deletar o arquivo
    var option = confirm( "Do you want remove this file/directory?" );
    if( option === false )
    {
        $.jstree.rollback( data.rlbk );
        notification( "User cancelled the action!" , "error" );
        return ;
    }

    var path = $( data.rslt.obj ).children( 'a' ).attr( 'href' );
    var isDir = tree_isDirectory( data.rslt.obj );
    
    // Não pode permitir que a pasta ROOT seja alterada
    if( data.rslt.obj.attr( 'id' ) === 'root' )
    {
        $.jstree.rollback( data.rlbk );
        notification( "You can not remove the root folder!" , "error" );
        return ;
    }
    
    // Atualizar no servidor o nome do arquivo
    $.ajax
    ( {
        async: false ,
        type : 'POST' ,
        url  : "/IDEA4WSN/storage/" + $project_storage + "/file/remove" ,
        data : { "path" : path , "directory" : isDir } ,
        success : postResult
    } );
    
    notification( "File/Directory removed!" , "success" );
}

//////////////////////////////////////////////////////////////

function tree_file_move( node , newPath )
{
    
}

function tree_file_new( event , data )
{
    //$( "#demo" ).jstree( "create" , null , "last" , { "attr" : { "rel" : "default" } });
    //$( "#demo" ).jstree( "create" , null , "last" , { "attr" : { "rel" : "folder" } });
    
    event.preventDefault();
    
    var name   = data.rslt.name;
    var parent = $( data.rslt.obj ).parent().parent();
    var path   = parent.children( 'a' ).attr( 'href' );
    path = path + "/" + name;
    
    // Criar o arquivo no servidor
    $.ajax
    ( {
        async: false ,
        type : 'POST' ,
        url  : "/IDEA4WSN/storage/" + $project_storage + "/file/new" ,
        data : { "path" : path , "data" : "" , "dir" : false } ,
        success : postResult
    } );
    
    $( data.rslt.obj ).children( 'a' ).attr( 'href' , path );
    
    openFile( name , path );
    notification( "File created!" , "success" );
}

/*********************************************************************
 * *******************************************************************
 * 
 *                          APPLICATION
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

function postResult( r )
{
    if( !r.status )
    {
        $.jstree.rollback( data.rlbk );
    }
}

function tree_isDirectory( element )
{
    return element.attr( 'rel' ) === 'directory';
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
        //"contextmenu" : tree_context_menu ,
        
        // the `plugins` array allows you to configure the active plugins on this instance
        "plugins" : [ "themes" , "html_data", "ui" , "crrm" , "hotkeys" , "contextmenu" , "unique" ]
    });
    
    var rootNode = $( "#root" );
    $project_current = rootNode.attr( "project" );  // encontrar o identificador do projeto
    $project_storage = rootNode.attr( "storage" );  // encontrar o identificador do storage
    
    jsTreeProject.delegate( "a" , "dblclick" , tree_file_open ); // abrir um arquivo
    jsTreeProject.bind( "rename.jstree" , tree_file_rename );
    jsTreeProject.bind( "remove.jstree" , tree_file_remove );
    jsTreeProject.bind( "create.jstree" , tree_file_new );
});