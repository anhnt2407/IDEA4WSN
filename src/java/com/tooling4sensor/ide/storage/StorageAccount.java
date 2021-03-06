package com.tooling4sensor.ide.storage;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.Column;
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
@Table( name="Storage" , schema="idea4wsn" )
public class StorageAccount implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long storageId;
    
    @Column
    private long userId;
    
    @Column
    private int type;
    
    @Column
    private String name;
    
    @Column
    private String login;
    
    @Column
    private String password;
    
    @Column
    private String information;
    
    public StorageAccount()
    {
        // do nothing
    }

    public long getStorageId()
    {
        return storageId;
    }

    public void setStorageId( long storageId )
    {
        this.storageId = storageId;
    }

    public int getType()
    {
        return type;
    }

    public void setType( int type )
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin( String login )
    {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId( long userId )
    {
        this.userId = userId;
    }

    public String getInformation()
    {
        return information;
    }

    public void setInformation( String information )
    {
        this.information = information;
    }
    
    public Map<String, String> getInformationMap()
    {
        return null;
    }

}
