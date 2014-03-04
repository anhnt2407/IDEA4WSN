<div id="${id}" file="${path}" storage="${storage}">
    <img src="/IDEA4WSN/storage/${storage}/file/download?path=${path}" />
</div>

<script>
    $( function ()
    {
        $( "#${id}" ).bind( "isValueChanged" , function ( e )
        {
            e.result = false;
        });

        $( "#${id}" ).bind( "saveFile" , function ( e )
        {
            // do nothing
        });
    });
</script>