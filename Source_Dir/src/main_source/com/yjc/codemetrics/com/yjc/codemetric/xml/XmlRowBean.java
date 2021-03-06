package com.yjc.codemetrics.com.yjc.codemetric.xml;

import com.intellij.openapi.vfs.VirtualFile;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Created by Administrator on 10/8/2015.
 */
public final class XmlRowBean {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_COUNT = "count";


    private VirtualFile file;
    private String name;
    private Integer total;
    private Integer code;



    public XmlRowBean()
    {
    }

    public XmlRowBean(VirtualFile file, String name, Integer total, Integer code)
    {
        this.file = file;
        this.name = name;
        this.total = total;
        this.code = code;
    }
    public VirtualFile getFile()
    {
        return file;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        String oldValue = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange("name", oldValue, name);
    }

    public Integer getTotal()
    {
        return total;
    }

    public void setTotal(Integer total)
    {
        Integer oldValue = this.total;
        this.total = total;
        propertyChangeSupport.firePropertyChange("count", oldValue, total);
    }


    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        Integer oldValue = this.code;
        this.code = code;
        propertyChangeSupport.firePropertyChange("comment", oldValue, code);
    }


    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("extension=").append(name);
        buffer.append(",count=").append(total);
        return buffer.toString();
    }

    public void addPropertyChangeListener(PropertyChangeListener l)
    {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l)
    {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
}
