/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function projectFileRename()
{
    if( $node_selected === null )
    {
        notification( "Please, select a directory or a file!" , "warn" );
        return ;
    }
    // Não pode permitir que a pasta ROOT seja alterada
    else if( $node_selected.attr( 'id' ) === 'root' )
    {
        notification( "You cannot rename the project name!" , "error" );
        return ;
    }
    
    // -------- Qual é o nome do arquivo
    var old = $node_selected.children( 'a' )[0].innerText.trim();
    var name = prompt( "Rename" , old );
    
    if ( name === null || name === old )             // cancelled!
    {
        notification( "User cancelled the action of to rename a file/directory!" , "warn" );
        return ;
    }
    
    var path = $node_selected.children( 'a' ).attr( 'href' );
    
    // Atualizar no servidor o nome do arquivo
    $.ajax
    ( {
        async: false ,
        type : 'POST' ,
        url  : "/IDEA4WSN/storage/" + $project_storage + "/file/rename" ,
        data : { "path" : path , "name" : name } ,
        
        error : function ( httpRequest , textStatus , errorThrown )
        {
            var text = "ERROR: File/Directory didn't rename.\n" +
                       "Reason: " + errorThrown;
            
            notification( text , "error" );
        } ,
        
        success : function ( data , textStatus , httpRequest ) 
        {
            $( '#jstree_project' ).jstree( "set_text" , $node_selected , name );
            
            // Atualizar o HREF para o novo caminho do arquivo ou pasta
            var pathNew = path.substr( 0 , path.length - old.length ) + name;
            $node_selected.children( 'a' ).attr( 'href' , pathNew );

            // se for uma pasta, é necessário atualizar os seus filhos
            if( tree_isDirectory( $node_selected ) )
            {
                _tree_rename_children( path , pathNew , $node_selected );
            }

            notification( "File/Directory was renamed!" , "success" );
            //TODO: quando uma aba esta aberta do arquivo?
        }
    } );
}

function _tree_rename_children( path , pathNew , element )
{
    var $children = element.children( 'ul' ).children( 'li' );

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