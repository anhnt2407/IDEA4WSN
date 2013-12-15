<div class="container">
    <h2>${action_form}</h2>
    <hr />

    <form class="form-horizontal" role="form" action="/IDEA4WSN/project/create" method="POST">
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">Name</label>
            
            <div class="col-sm-10">
                <input id="name" name="name" type="text" class="form-control" placeholder="Name" value="${project.name}" />
            </div>
        </div>

        <div class="form-group">
            <label for="storage" class="col-sm-2 control-label">Storage</label>
            
            <div class="col-sm-10">
                <select id="storage" name="storageId">
                    <option value="1">Local</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label for="path" class="col-sm-2 control-label">Path</label>
            
            <div class="col-sm-10">
                <input id="path" name="directory" type="text" class="form-control" placeholder="Path" value="${project.directory}" />
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <input type="submit" value="Create" class="btn btn-primary" />
                <button class="btn">Cancel</button>
            </div>
        </div>
    </form>
</div>