package com.yjc.codemetrics.com.yjc.codemetric.xml;

import com.intellij.openapi.vfs.VirtualFile;
import com.yjc.codemetrics.com.yjc.codemetric.java.JavaRowBean;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 10/8/2015.
 */
public class XmlTableModel extends AbstractTableModel {     //Table Details Value
    private final String[] columnNames = { "Source File ", "Total Lines ", "Source Code Lines "};

    private final List<XmlRowBean> data = new ArrayList();

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if ((rowIndex >= 0) && (rowIndex < getRowCount()))
        {
            if ((columnIndex >= -1) && (columnIndex < getColumnCount()))
            {
                XmlRowBean bean = (XmlRowBean)data.get(rowIndex);
                switch (columnIndex)
                {
                    case -1:
                        return bean.getFile();
                    case 0:
                        return bean.getName();
                    case 1:
                        return bean.getTotal();
                    case 2:
                        return bean.getCode();

                }
                throw new IllegalArgumentException("Column is out of bound. Index=" + columnIndex);
            }

            throw new IllegalArgumentException("ColumnIndex is out of bound. ColumnIndex=" + rowIndex);
        }

        throw new IllegalArgumentException("RowIndex is out of bound. RowIndex=" + columnIndex);
    }

    public Class getColumnClass(int columnIndex)
    {
        switch (columnIndex)
        {
            case -1:
                return VirtualFile.class;
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return Integer.class;

        }
        throw new IllegalArgumentException("Column is out of bound. Index=" + columnIndex);
    }

    public String getColumnName(int column)
    {
        if (column < columnNames.length)
        {
            return columnNames[column];
        }

        throw new IllegalArgumentException("Column is out of bound. Index=" + column);
    }

    public void deactivate()
    {
        if (SwingUtilities.isEventDispatchThread())
        {
            deactivateSwing();
        }
        else
        {
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    XmlTableModel.this.deactivateSwing();
                }
            });
        }
    }

    private void deactivateSwing() {
        data.clear();
        fireTableDataChanged();
    }

    public void addRow(final XmlRowBean bean)
    {
        if (SwingUtilities.isEventDispatchThread())
        {
            addRowSwing(bean);
        }
        else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    XmlTableModel.this.addRowSwing(bean);
                }
            });
        }
    }

    private void addRowSwing(XmlRowBean bean) {
        data.add(bean);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }


    public void removeRow()
    {
        if (SwingUtilities.isEventDispatchThread())
        {
            removeRowSwing();
        }
        else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    XmlTableModel.this.removeRowSwing();
                }
            });
        }
    }

    private void removeRowSwing() {
        data.clear();
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }
}
