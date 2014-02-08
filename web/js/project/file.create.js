/* 
 * Esta função irá criar um arquivo ou uma pasta.
 * 
 */
function projectFileCreate( isDirectory )
{
    if( $node_selected === null )
    {
        notification( "Please, select a directory!" , "warn" );
        return ;
    }
    if( !tree_isDirectory( $node_selected ) && !tree_isProject( $node_selected ) )
    {
        notification( "Please, select a directory, not a file!" , "warn" );
        return ;
    }
        
    // -------- Qual é o nome do arquivo
    var filename = prompt( "Name" , "" );
    if ( filename === null )             // cancelled!
    {
        notification( "User cancelled the action of to create a file/directory!" , "warn" );
        return ;
    }
    
    var path   = $node_selected.children( 'a' ).attr( 'href' );
    path = path + "/" + filename;
    
    // Criar o arquivo no servidor
    $.ajax
    ( {
        async: false ,
        type : 'POST' ,
        url  : "/IDEA4WSN/storage/" + $project_storage + "/file/new" ,
        data : { "path" : path , "data" : "" , "dir" : isDirectory } ,
        error : function ( httpRequest , textStatus , errorThrown )
        {
            var text = "ERROR: File/Directory didn't create.\n" +
                       "Reason: " + errorThrown;
            
            notification( text , "error" );
        } ,
        success : function ( data , textStatus , httpRequest ) 
                  {
                      var newNode = { data: filename
                                      //, state : "open" 
                                      , "attr": { "rel" : isDirectory ? "directory" : "file" }
                                      };

                      $( '#jstree_project' ).jstree( "create"  // action
                                                      , $node_selected   // parent
                                                      , 'inside' // "before", "after", "inside", "first", "last".
                                                      , newNode  // {attr: "" , state: "" , data: "" }
                                                      , function ( e )   // event
                                                      {
                                                          $( e ).children( 'a' ).attr( 'href' , path );
                                                      }
                                                      , true    // is_loaded
                                                     );

                      // abrir o arquivo
                      if( !isDirectory )
                      {
                        openFile( filename , path );
                      }

                      notification( "File/Directory was created!" , "success" );
                  }
    } );
}
