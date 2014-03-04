/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var deployment = 
{
    algorithms : {}
  , execute : function ( name , param )
              {
                  if( typeof param.number === 'undefined' ) param.number = 21;
                  
                  if( typeof param.sinkX === 'undefined' ) param.sinkX = 100;
                  if( typeof param.sinkY === 'undefined' ) param.sinkY = 100;
                  
                  if( typeof param.X === 'undefined' ) param.X = 0;
                  if( typeof param.Y === 'undefined' ) param.Y = 200;
                  
                  if( typeof param.width  === 'undefined' ) param.width  = 200;
                  if( typeof param.height === 'undefined' ) param.height = 200;
                  
                  //Todas as variaveis serao convertidas para INTEIRO!
                  for( var p in param )
                  {
                      if( p === "nodeDefault" )  //exceto essa!
                      {
                          continue ;
                      }
                      
                      param[ p ] = parseInt( param[ p ] );
                  }
                  
                  var nodes = this.algorithms[ name ].fn( param );
                  
                  nodes[ "node1" ] = $.extend( {} , param.nodeDefault );
                  nodes[ "node1" ].id       = 1;
                  nodes[ "node1" ].nodeType = "SINK";
                  nodes[ "node1" ].X = param.X + param.sinkX;
                  nodes[ "node1" ].Y = param.Y + param.sinkY;
                  
                  return nodes;
              } 
  , random  : function ( min , max )
              {
                  if( typeof min === 'undefined' ) min = 0;
                  if( typeof max === 'undefined' ) max = 200;
                  
                  return Math.floor( ( Math.random() * max ) + min );
              }
};

deployment.algorithms[ "manual" ] = 
        {
              param : { }
            , fn: function ( param )
                  {
                      return {};
                  }
        };

deployment.algorithms[ "random" ] = 
        {
              param : { }
            , fn: function ( param )
                  {
                     var nodes = {};
                     var minX  = param.X , maxX = param.X + param.width;
                     var minY  = param.Y , maxY = param.Y + param.height;
                     
                     for( var i = 2 ; i <= param.number ; i++ )
                     {
                         var n = $.extend( {} , param.nodeDefault );
                         
                         n.id       = i;
                         n.nodeType = "NODE";
                         n.X = deployment.random( minX , maxX );
                         n.Y = deployment.random( minY , maxY );
                         
                         nodes[ "node" + i ] = n;
                     }
                     
                     return nodes;
                  }
        };

// -------------------------------------------
// -------------------------------------------
// -------------------------------------------

