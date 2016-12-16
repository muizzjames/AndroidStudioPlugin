package com.yjc.codemetrics.com.yjc.codemetric.render;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 10/9/2015.
 */
public class JavaRender extends DefaultTableCellRenderer {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0%");

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        JLabel jLabel = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);       //Set Font of each row to table
        if (column == 0)
        {
            jLabel.setText("<html><b><font face=\"Verdana\" size=\"3\" color=\"navy\">" + value + "</font></b></html>");
        }
        else if ((column == 7) || (column == 5))
        {
            String s = DECIMAL_FORMAT.format(value);
            jLabel.setText(s);
        }
        else
        {
            jLabel.setText(value.toString());
        }
        return jLabel;
    }
}
