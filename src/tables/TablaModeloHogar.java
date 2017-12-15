/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Luis
 */
public class TablaModeloHogar extends AbstractTableModel {

    private List<Hogar> hogares = new ArrayList<Hogar>();

    public void agregarHogar(Hogar hogar) {
        hogares.add(hogar);
        fireTableDataChanged();
    }

    public void eliminarHogar(int rowIndex) {
        hogares.remove(rowIndex);
        fireTableDataChanged();
    }

    public void limpiarHogar() {
        hogares.clear();
        fireTableDataChanged();
    }

    @Override
     // Devuelve el nombre de cada columna. Este texto aparecer� en la
        // cabecera de la tabla.
    public String getColumnName(int columnIndex) {
//        return columnNames[columnIndex];
        
        switch (columnIndex)
        {
        

            case 0:
                return "No.Hogar";
            case 1:
                return "No Cuestionarios";
            case 2:
                return "Miembros del Hogar";
            case 3:
                return "Mujeres Elegibles";
            case 4:
                return "Hombres Elegibles";
            case 5:
                return "Niños/Niñas Elegibles";
            case 6:
                return "Valido";
            default:
                return null;
        }
    }

    @Override
    public int getRowCount() {
        return hogares.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return hogares.get(rowIndex).getno_hogar();
            case 1:
                return hogares.get(rowIndex).getno_cuestionarios();
            case 2:
                return hogares.get(rowIndex).gettotal_miembros();
            case 3:
                return hogares.get(rowIndex).gettotal_mujeres();
            case 4:
                return hogares.get(rowIndex).getTotal_hombres();
            case 5:
                return hogares.get(rowIndex).getno_ninos();
            case 6:
                return hogares.get(rowIndex).getval_hogar(); 
            default:
                return null;
        }
    }

    @Override
    public Class getColumnClass(int columnIndex) {
//        return getValueAt(0, columnIndex).getClass();
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Hogar hogar = hogares.get(rowIndex);
        switch (columnIndex) {
            case 0:
                hogar.setno_hogar((Integer) value);
            case 1:
                hogar.setno_cuestionarios((Integer) value);
            case 2:
                hogar.settotal_miembros((Integer) value);
            case 3:
                hogar.settotal_mujeres((Integer) value);
            case 4:
                hogar.setTotal_hombres((Integer)value);
            case 5:
                hogar.setno_ninos((Integer)value);
            case 6:
                hogar.setval_hogar((String)value);
                

        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
