/**
 * 
 * Código encontrado em: http://jsfiddle.net/vinodlouis/pb6EM/1/
 * 
 **/

var currentTab;
var composeCounter = 0;

//initilize tabs
$(function () {

    //when ever any tab is clicked this method will be call
    $("#myTab").on("click", "a", function (e) {
        e.preventDefault();

        $(this).tab('show');
        $currentTab = $(this);
    });


    registerComposeButtonEvent();
    registerCloseEvent();
});

//this method will demonstrate how to add tab dynamically
function registerComposeButtonEvent()
{
    /* just for this demo */
    $('#composeButton').click( function ( e ) 
            { 
                e.preventDefault(); 
                openFile( 'teste.txt' , '/Blink/teste.txt' );
            } );
}

// ---------------------------------------------------
// --------------------------------------------------- FUNCTIONS WITH FILE
// ---------------------------------------------------

function openFile( name , path )
{
    //composeCounter
    var tabId = "tab_" + changePathToId( path ); //this is id on tab content div where the 
    
    var tabTitle = '<li>';
    tabTitle += '<a href="#' + tabId + '">';
    tabTitle += '<button class="close closeTab" type="button" >×</button>'+ name +'</a></li>';

    $('.nav-tabs').append( tabTitle );
    $('.tab-content').append('<div class="tab-pane" id="' + tabId + '"></div>');

    var urlPath = document.URL;
    var hashIndex = document.URL.indexOf( '#' );
    
    if( hashIndex !== -1 )
    {
        urlPath = document.URL.substr( 0 , hashIndex );
    }

    craeteNewTabAndLoadUrl( "" , urlPath + "/file?file=" + path , "#" + tabId );

    $(this).tab( 'show' );
    showTab( tabId );
    registerCloseEvent();
};

function saveFile()
{
    
}

// ---------------------------------------------------
// --------------------------------------------------- FUNCTIONS WITH FILE
// ---------------------------------------------------

function changePathToId( path )
{
    var id = "";
    
    for( var number in path )
    {
        var c = path.charAt( number );
        
        if( c === '/' )
        {
            id += "dDd";
        }
        else if( c === '.' )
        {
            id += "eEe";
        }
        else if( isDigit( c ) || isLetter( c ) )
        {
            id += c;
        }
    }
    
    return id.replace( "." , "eEe" );
}

function isLetter( str )
{
  return str.length === 1 && (str.match(/[a-z]/i) || str.match(/[A-Z]/i));
}

function isDigit( str )
{
  return str.length === 1 && str.match(/[0-9]/i);
}

//this method will register event on close icon on the tab..
function registerCloseEvent()
{
    $(".closeTab").click(function () {
        //there are multiple elements which has .closeTab icon so close the tab whose close icon is clicked
        var tabContentId = $(this).parent().attr("href");
        $(this).parent().parent().remove(); //remove li of tab
        $('#myTab a:last').tab('show'); // Select first tab
        
        var tabConteudo = $( tabContentId + "_data" )[0].innerHTML;
        //TODO: salvar no servidor!
        
        $( tabContentId ).remove();         //remove respective tab content
    });
}

//shows the tab with passed content div id..paramter tabid indicates the div 
//where the content resides
function showTab( tabId )
{
    $('#myTab a[href="#' + tabId + '"]').tab( 'show' );
}
//return current active tab
function getCurrentTab()
{
    return currentTab;
}

//This function will create a new tab here and it will load the url content in tab content div.
function craeteNewTabAndLoadUrl(parms, url, loadDivSelector)
{
    $("" + loadDivSelector).load(url, function (response, status, xhr) {
        if (status == "error") {
            var msg = "Sorry but there was an error getting details ! ";
            $("" + loadDivSelector).html(msg + xhr.status + " " + xhr.statusText);
            $("" + loadDivSelector).html("Load Ajax Content Here...");
        }
    });
}

//this will return element from current tab
//example : if there are two tabs having  textarea with same id or same class name 
//          then when $("#someId") whill return both the text area from both tabs
//to take care this situation we need get the element from current tab.
function getElement( selector )
{
    var tabContentId = $currentTab.attr( "href" );
    return $("" + tabContentId).find( "" + selector );

}


function removeCurrentTab()
{
    var tabContentId = $currentTab.attr( "href" );
    
    $currentTab.parent().remove();      //remove li of tab
    $('#myTab a:last').tab( 'show' );   // Select first tab
    
    var tabContentData = $( tabContentId );
    
    alert( "OK!" );
    $( "#home" ).append( print( tabContentData ) );
    tabContentData.remove();         //remove respective tab content
}
