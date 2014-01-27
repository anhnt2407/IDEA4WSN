<!-- http://www.plupload.com/ -->
<h2>Upload</h2>

<p>Path: ${path}</p>

<div id="filelist">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>
<br />
 
<div id="container">
    <a id="pickfiles" href="javascript:;">[Select files]</a>
    <a id="uploadfiles" href="javascript:;">[Upload files]</a>
</div>
 
<br />
<pre id="console"></pre>

<script type="text/javascript">
    
var uploader = new plupload.Uploader({
    runtimes : 'html5,flash,silverlight,html4',
     
    browse_button : 'pickfiles', // you can pass in id...
    container: document.getElementById('container'), // ... or DOM Element itself
     
    url : "/IDEA4WSN/storage/${storage_id}/file/upload?path=${path}",
     
    filters : {
        max_file_size : '10mb',
//        mime_types: [
//            {title : "Image files", extensions : "jpg,gif,png"},
//            {title : "Zip files", extensions : "zip"}
//        ]
    },
 
    // Flash settings
    flash_swf_url : 'http://rawgithub.com/moxiecode/moxie/master/bin/flash/Moxie.cdn.swf',
 
    // Silverlight settings
    silverlight_xap_url : 'http://rawgithub.com/moxiecode/moxie/master/bin/silverlight/Moxie.cdn.xap',
     
 
    init: {
        PostInit: function() {
            document.getElementById('filelist').innerHTML = '';
 
            document.getElementById('uploadfiles').onclick = function() {
                uploader.start();
                return false;
            };
        },
 
        FilesAdded: function(up, files) {
            plupload.each(files, function(file) {
                document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
            });
        },
 
        UploadProgress: function(up, file) {
            document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
            
            // Adiciona um NODE no JSTree
            var position = 'inside';
            var parent = $( '#jstree_project' ).jstree( 'get_selected' );
            var newNode = { data: file.name
                          //, state : "open" 
                          //, "attr": { "rel" : "folder" }
                          };
            $( '#jstree_project' ).jstree( "create" , parent , position , newNode , false , false );
        },
 
        Error: function(up, err) {
            document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
        }
    }
});
 
uploader.init();

</script>