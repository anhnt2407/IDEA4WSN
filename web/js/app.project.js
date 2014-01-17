var $project_current = 0;
var $project_storage = 0;

function openProjectWindow( id )
{
    window.open( '/IDEA4WSN/project/' + id , "_blank" );
    $( "#dialog" ).dialog( "close" );
}


