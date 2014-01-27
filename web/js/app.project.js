var $project_current = 0;
var $project_storage = 0;

function openProjectWindow( id )
{
    window.open( '/IDEA4WSN/project/' + id , "_blank" );
    $( "#dialog" ).dialog( "close" );
}


function showDialogUrl( $URL )
{
    $("#dialog").html("");
    $("#dialog").dialog("option", "title", "Loading...").dialog( "open" );
    $("#dialog").load( $URL , function() {
         $(this).dialog( "option" , "title" , $(this).find("h2").text() );
         $(this).find("h2").remove();
    });
}