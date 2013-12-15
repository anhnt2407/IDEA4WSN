package com.tooling4sensor.ide.project;

import java.io.Serializable;
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
@Table( name="Project_file_openned" , schema="idea4wsn" )
public class FileOpenned implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileOpennedId;
    
    private long projectId;
    private long storageId;
    private String file;
    
    public FileOpenned()
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

    public long getStorageId()
    {
        return storageId;
    }

    public void setStorageId( long storageId )
    {
        this.storageId = storageId;
    }

    public String getFile()
    {
        return file;
    }

    public void setFile( String file )
    {
        this.file = file;
    }

    public Long getFileOpennedId() {
        return fileOpennedId;
    }

    public void setFileOpennedId(Long fileOpennedId) {
        this.fileOpennedId = fileOpennedId;
    }
    
}
