package com.tooling4sensor.ide.project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author avld
 */
@Entity
@Table( name="Project" , schema="idea4wsn" )
public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long projectId;
    private long storageId;
    private long userId;
    
    private String name;
    private String directory;
    
    public Project()
    {
        // do nothing
    }

    public long getProjectId()
    {
        return projectId;
    }

    public void setProjectId( long projectId )
    {
        this.projectId = projectId;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId( long userId )
    {
        this.userId = userId;
    }

    public long getStorageId()
    {
        return storageId;
    }

    public void setStorageId( long storageId )
    {
        this.storageId = storageId;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getDirectory()
    {
        return directory;
    }

    public void setDirectory( String directory )
    {
        this.directory = directory;
    }
    
}
