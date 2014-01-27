/* 
 * This function will delete a file or a directory.
 * 
 */
function projectFileDelete()
{
    if( $node_selected === null )
    {
        notification( "Please, select a directory or a file!" , "warn" );
        return ;
    }
    // Não pode permitir que a pasta ROOT seja deletada
    // TODO: como excluir um projeto?!
    else if( $node_selected.attr( 'id' ) === 'root' )
    {
        notification( "You cannot delete the project!" , "error" );
        return ;
    }
    
    // Confirmar se deseja mesmo deletar o arquivo
    var option = confirm( "Do you want remove this file/directory?" );
    if( option === false )
    {
        notification( "User cancelled the action!" , "warn" );
        return ;
    }
    
    var path = $node_selected.children( 'a' ).attr( 'href' );
    var isDir = tree_isDirectory( $node_selected );
    
    // Atualizar no servidor o nome do arquivo
    $.ajax
    ( {
        async: false ,
        type : 'POST' ,
        url  : "/IDEA4WSN/storage/" + $project_storage + "/file/remove" ,
        data : { "path" : path , "directory" : isDir } ,
        
        error : function ( httpRequest , textStatus , errorThrown )
        {
            var text = "ERROR: File/Directory didn't delete.\n" +
                       "Reason: " + errorThrown;
            
            notification( text , "error" );
        } ,
        
        success : function ( data , textStatus , httpRequest ) 
        { 
            $( '#jstree_project' ).jstree( "delete_node" , $node_selected );
            notification( "File/Directory was deleted!" , "success" );
        }
    } );
}