/**
 * Este aqui possui funções que são usadas por outros arquivos (ex. JS Tree)
 */

/*********************************************************************
 * *******************************************************************
 *                          STRING
 * *******************************************************************
 *********************************************************************/

function startsWith( str , suffix )
{
    if( typeof str === 'undefined' )
    {
        return false;
    }
    else if( typeof suffix === 'undefined' )
    {
        return false;
    }

    return str.substr( 0 , suffix.length ) === suffix;
}

/**
 * Verifica se o 'suffix' esta no final do 'str'.
 * Por exemplo, str: "teste.txt" ... suffix: ".txt". retorna TRUE;
 * 
 * @param {String} str
 * @param {String} suffix
 * @returns {Boolean}
 */
function endsWith( str , suffix )
{
    if( typeof str === 'undefined' )
    {
        return false;
    }
    else if( typeof suffix === 'undefined' )
    {
        return false;
    }
    
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}

/**
 * Imprimir as propriedades de um objeto.
 * 
 * @param {Object} o
 * @returns {String}
 */
function print( o )
{
    var str= '';

    for( var p in o )
    {
        if( typeof o[p] === 'function' )
        {
            // do nothing
        }
        else if( typeof o[p] === 'string' )
        {
            str += p + ': ' + o[p]+'; <br />\n';
        }
        else
        {
            str += p + ': { <br />\n';
            
            for( var p2 in o[p] )
            {
                str += "            " + p2 + ': ' + o[p][p2]+'; <br />\n';
            }
            
            str += '}<br />\n';
        }
    }

    return str;
}




/*********************************************************************
 * *******************************************************************
 *                            CHAR
 * *******************************************************************
 *********************************************************************/

/**
 * Verifica se 'str' é uma letra
 * 
 * @param {type} str
 * @returns {Boolean}
 */
function isLetter( str )
{
  return str.length === 1 && (str.match(/[a-z]/i) || str.match(/[A-Z]/i));
}

/**
 * Verifica se 'str' é uma número
 * 
 * @param {type} str
 * @returns {Boolean}
 */
function isDigit( str )
{
  return str.length === 1 && str.match(/[0-9]/i);
}
