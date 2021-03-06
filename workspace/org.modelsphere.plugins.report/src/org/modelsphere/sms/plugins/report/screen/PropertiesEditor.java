/*************************************************************************

Copyright (C) 2008 Grandite

This file is part of Open ModelSphere.

Open ModelSphere is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
or see http://www.gnu.org/licenses/.

You can reach Grandite at: 

20-1220 Lebourgneuf Blvd.
Quebec, QC
Canada  G2K 2G4

or

open-modelsphere@grandite.com

 **********************************************************************/

package org.modelsphere.sms.plugins.report.screen;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.JTable;

import org.modelsphere.jack.awt.AbstractTableCellEditor;

public class PropertiesEditor extends AbstractTableCellEditor {

    private ReportPropertiesFrame parent;

    public PropertiesEditor(ReportPropertiesFrame parent) {
        this.parent = parent;
    }

    transient private Editor delegate;

    public final Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        Object cellValue = table.getValueAt(row, column);

        if (cellValue instanceof Boolean)
            delegate = new BooleanEditor();
        else if (cellValue instanceof Color)
            delegate = new ColorEditor(parent);
        else if (cellValue instanceof Integer)
            delegate = new IntegerEditor();
        else if (cellValue instanceof File)
            delegate = new FileEditor(parent);
        else if (cellValue instanceof org.modelsphere.jack.baseDb.db.srtypes.Domain)
            delegate = new DomainEditor();
        else
            delegate = new StringEditor();

        if (delegate != null) {
            return delegate.getTableCellEditorComponent(table, this, value, isSelected, row, table
                    .convertColumnIndexToModel(column));
        }
        return null;
    }

    public final boolean stopCellEditing() {
        if (!delegate.stopCellEditing())
            return false;
        fireEditingStopped();
        return true;
    }

    public final Object getCellEditorValue() {
        return delegate.getCellEditorValue();
    }
}
