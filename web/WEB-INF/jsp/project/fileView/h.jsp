<div id="${id}" file="${path}" storage="${storage}" class="editor">${data}</div>

<script>
    $( function ()
    {
        var editor = ace.edit( "${id}" );
       // editor.setTheme( "ace/theme/monokai" );
        editor.getSession().setMode("ace/mode/c");

        var valueOld = hex_md5( editor.getSession().getValue() );

        $( "#${id}" ).bind( "isValueChanged" , function ( e )
        {
            var valueNew = hex_md5( editor.getSession().getValue() );
            e.result = ( valueOld !== valueNew );
        });

        $( "#${id}" ).bind( "saveFile" , function ( e )
        {
            var data = editor.getSession().getValue();
            saveFile( e.path , data );
        });
        
        $( "#${id}" ).children().bind( 'keydown' , 'alt+s' , saveTabSelected );
    });
</script>