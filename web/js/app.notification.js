// site do plugin: http://notifyjs.com/

// ---------------------------------------------------
// --------------------------------------------------- FUNCOES
// ---------------------------------------------------

/**
 * Ira mostrar uma notificação que ainda nao foi implementado.
 * 
 */
function notification_notImplementYet()
{
    notification( "Not Implement Yet!" , "warn" );
}

/**
 * Notificação do sistema
 * 
 * @param {String} msg      The message that will be showed
 * @param {String} type     success, info, warn, error
 */
function notification( msg , type )
{
    var option = {
        clickToHide: true,        // whether to hide the notification on click
        autoHide: true,           // whether to auto-hide the notification
        autoHideDelay: 5000,      // if autoHide, hide after milliseconds
        arrowShow: true,          // show the arrow pointing at the element
        arrowSize: 5,             // arrow size in pixels
        elementPosition: 'top left',   // default positions
        globalPosition: 'bottom right',
        style: 'bootstrap',       // default style
        className: type,          // default class (string or [string])
        showAnimation: 'slideDown',   // show animation
        showDuration: 400,        // show animation duration
        hideAnimation: 'slideUp', // hide animation
        hideDuration: 200,        // hide animation duration
        gap: 2                    // padding between element and notification
      };
    
    $.notify( msg , option );
}