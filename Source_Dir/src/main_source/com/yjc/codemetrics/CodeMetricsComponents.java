package com.yjc.codemetrics;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;

import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;


import javax.swing.*;


/**
 * Created by Administrator on 9/26/2015.
 */
public class CodeMetricsComponents implements ProjectComponent {

    private final Project project;
    private CodeMetricsPanel panel;
    private static final ImageIcon ICON_STATISTIC = new ImageIcon(CodeMetricsComponents.class.getResource("/resources/icons/logotypes.png"));

    public String fileTypes = "class;svn-base;svn-work;Extra;gif;png;jpg;jpeg;bmp;tga;tiff;ear;war;zip;jar;iml;iws;ipr;bz2;gz;";

    public  CodeMetricsComponents(Project project)
    {
        this.project = project;
    }
    @Override
    public void projectOpened() {       //Called Function when project open
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(this.project);
        ToolWindow toolWindow = toolWindowManager.registerToolWindow("CodeMetrics", false, ToolWindowAnchor.BOTTOM);
        toolWindow.setIcon(ICON_STATISTIC);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(this.panel, null, true);
        toolWindow.getContentManager().addContent(content);
    }

    @Override
    public void projectClosed() {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(this.project);
        toolWindowManager.unregisterToolWindow("CodeMetrics");
    }

    @Override
    public void initComponent() {
        System.out.println("Plugin '" + getComponentName() + "' has been activated for project=" + this.project.getName());
        try
        {
            this.panel = new CodeMetricsPanel(this.project.getBasePath(), this.project);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void disposeComponent() {
        System.out.println("Plugin '" + getComponentName() + "' has been deactivated for project=" + this.project.getName());
    }


    @Override
    public String getComponentName() {
        return "CodeMetricsComponents";
    }


}
