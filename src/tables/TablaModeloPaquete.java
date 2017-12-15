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
public class TablaModeloPaquete extends AbstractTableModel {

    private List<Paquete> paquetes = new ArrayList<Paquete>();

    public void agregarPaquete(Paquete item) {
        paquetes.add(item);
        fireTableDataChanged();
    }

    public void eliminarPaquete(int rowIndex) {
        paquetes.remove(rowIndex);
        fireTableDataChanged();
    }
    
    public Paquete getPaquete(int numero, String tipo_paquete){
        for(Paquete p:paquetes){
            if(p.getno_paquete() == numero && p.getTipo_paquete().equals(tipo_paquete)){
                return p;
            }
        }
        return null;
    }
    
    public void limpiarPaquete() {
        paquetes.clear();
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
                return "No.Paquete";
            case 1:
                return "Tipo de Paquete";
            case 2:
                return "Estado";
            case 3:
                return "Fecha";
            case 4:
                return "Tiempo";

                
                   
            default:
                return null;
        }
    }

    @Override
    public int getRowCount() {
        return paquetes.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return paquetes.get(rowIndex).getno_paquete();
            case 1:
                return paquetes.get(rowIndex).getTipo_paquete();
            case 2:
                return paquetes.get(rowIndex).getestado();
            case 3:
                return paquetes.get(rowIndex).getfecha();
            case 4:
                return paquetes.get(rowIndex).gettiempo();

       
                     
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
        Paquete item = paquetes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                item.setno_paquete((Integer) value);
            case 1:
                item.setTipo_paquete((String) value);
            case 2:
                item.setestado((String) value);    
            case 3:
                item.setfecha((String) value);
            case 4:
                item.settiempo((String) value);

                        
                

        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
