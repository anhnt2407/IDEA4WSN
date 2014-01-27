/**
 * Esta funcao notifica o servidor que houve um arquivo ou pasta mudou de PATH.
 * Ele pode clonar um arquivo (type = 1) ou mudar completamente a pasta (type = 2).
 * 
 * @param {Number} type     1: COPY ... 2: CUT
 * @param {Node} dir        diretory
 * @param {Node} file       file
 */
function projectFileMove( type , dir , file )
{
    var file_A  = file.children( 'a' );
    var pathOld = file_A.attr( 'href' );
    var name    = file_A[0].innerText.trim();
    
    var pathNew = dir.children( 'a' ).attr( 'href' );
        pathNew = pathNew + "/" + name;
    
//    alert( "path: " + path );
//    $( "#home" ).html( print( file ) );
    
    $.ajax
    ( {
        async: false ,
        type : 'POST' ,
        url  : "/IDEA4WSN/storage/" + $project_storage + "/file/move" ,
        data : { "pathOld" : pathOld , "pathNew" : pathNew , "type" : type } ,
        
        error : function ( httpRequest , textStatus , errorThrown )
        {
            var text = "ERROR: File/Directory didn't copy in the server.\n" +
                       "Reason: " + errorThrown;

            notification( text , "error" );
        } ,
                
        success : function ( data , textStatus , httpRequest ) 
        {
            notification( "File/Directory was copy!" , "success" );
        }
    });
}

/**
 * Esta funcao duplica um arquivo dentro da mesma pasta.
 * 
 */
function projectFileDuplicate()
{
    if( $node_selected === null )
    {
        notification( "Please, select a directory!" , "warn" );
        return ;
    }
    
    // -------- Qual é o nome do arquivo
    var old  = $node_selected.children( 'a' )[0].innerText.trim();
    var name = prompt( "Rename" , old );
    
    if ( name === null )             // cancelled!
    {
        notification( "User cancelled the action of to duplicate a file/directory!" , "warn" );
        return ;
    }
    else if( name === old )
    {
        notification( "User cancelled the action when repeat the name!" , "warn" );
        return ;
    }
    
    var pathOld = $node_selected.children( 'a' ).attr( 'href' );
    var isDirectory = tree_isDirectory( $node_selected );
    
    var parent  = $node_selected.parent().parent();
    
    var pathNew = parent.children( 'a' ).attr( 'href' );
        pathNew = pathNew + "/" + name;
    
    // Criar o arquivo no servidor
    $.ajax
    ( {
        async: false ,
        type : 'POST' ,
        url  : "/IDEA4WSN/storage/" + $project_storage + "/file/move" ,
        data : { "pathOld" : pathOld , "pathNew" : pathNew , "type" : 1 } ,
        error : function ( httpRequest , textStatus , errorThrown )
        {
            var text = "ERROR: File/Directory didn't create.\n" +
                       "Reason: " + errorThrown;
            
            notification( text , "error" );
        } ,
        success : function ( data , textStatus , httpRequest ) 
                  {
                      var newNode = { data: name
                                      //, state : "open" 
                                      , "attr": { "rel" : isDirectory ? "directory" : "file" }
                                      };

                      $( '#jstree_project' ).jstree( "create"  // action
                                                      , parent   // parent
                                                      , 'inside' // "before", "after", "inside", "first", "last".
                                                      , newNode  // {attr: "" , state: "" , data: "" }
                                                      , function ( e )   // event
                                                      {
                                                          $( e ).children( 'a' ).attr( 'href' , pathNew );
                                                      }
                                                      , true    // is_loaded
                                                     );

                      notification( "File/Directory was duplicated!" , "success" );
                  }
    } );
}