package com.yjc.codemetrics.com.yjc.codemetric.android;

import com.intellij.openapi.vfs.VirtualFile;
import com.yjc.codemetrics.CodeMetricsComponents;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public final class AndroidRowBean {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private VirtualFile     file;
    private String          atyName;
    private String          layName;
    private Integer         inputCnt;
    private Integer         outputCnt;

    public AndroidRowBean()
    {
    }

    public AndroidRowBean(VirtualFile file, String name, String lay, Integer nIn, Integer nOut)
    {
        this.file = file;
        this.atyName = name;
        this.layName = lay;
        this.inputCnt = nIn;
        this.outputCnt = nOut;
    }
    public VirtualFile getFile()
    {
        return file;
    }

    public String getAtyName()
    {
        return atyName;
    }

    public void setAtyName(String atyName)
    {
        String oldValue = this.atyName;
        this.atyName = atyName;
        propertyChangeSupport.firePropertyChange("atyName", oldValue, atyName);
    }

    public String getLayName()
    {
        return layName;
    }

    public void setLayName(String name)
    {
        String oldValue = this.layName;
        this.layName = name;
        propertyChangeSupport.firePropertyChange("layName", oldValue, name);
    }
    public Integer getInputCnt()
    {
        return inputCnt;
    }

    public void setInputCnt(Integer nCnt)
    {
        Integer oldValue = this.inputCnt;
        this.inputCnt = nCnt;
        propertyChangeSupport.firePropertyChange("inputCnt", oldValue, nCnt);
    }

    public Integer getOutputCnt()
    {
        return outputCnt;
    }

    public void setOutputCnt(Integer nCnt)
    {
        Integer oldValue = this.outputCnt;
        this.outputCnt = nCnt;
        propertyChangeSupport.firePropertyChange("outputCnt", oldValue, nCnt);
    }
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ActivityName=").append(atyName);
        buffer.append("LayoutName=").append(layName);
        buffer.append(",inputCnt=").append(inputCnt);
        buffer.append(",outputCnt=").append(outputCnt);
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
