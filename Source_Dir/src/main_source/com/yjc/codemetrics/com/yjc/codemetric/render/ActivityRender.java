package com.yjc.codemetrics.com.yjc.codemetric.render;

import com.yjc.codemetrics.CodeMetricsComponents;
import com.yjc.codemetrics.com.yjc.codemetric.android.AndroidTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 10/9/2015.
 */
public class ActivityRender extends DefaultTableCellRenderer {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0%");

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        JLabel jLabel = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);       //Set Font of each row to table
        ImageIcon icon = new ImageIcon(CodeMetricsComponents.class.getResource("/resources/icons/overflow1.png"));
        if (column == 0)
        {
            jLabel.setText(value.toString());
            AndroidTableModel model = (AndroidTableModel)table.getModel();

            int nInCnt = Integer.valueOf(model.getValueAt(row, 2).toString());
            int nOutCnt = Integer.valueOf(model.getValueAt(row, 3).toString());

            if( nInCnt + nOutCnt >=10 )
                jLabel.setIcon(icon);
            else
                jLabel.setIcon(null);
        }
        else
        {
            jLabel.setText(value.toString());
            jLabel.setIcon(null);
        }
        return jLabel;
    }
}
