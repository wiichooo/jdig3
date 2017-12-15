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
public class TablaModeloCuestionario extends AbstractTableModel {

    private List<Cuestionario> cuestionarios = new ArrayList<Cuestionario>();

    public void agregarCuestionario(Cuestionario cuestionario) {
        cuestionarios.add(cuestionario);
        fireTableDataChanged();
    }

    public void eliminarCuestionario(int rowIndex) {
        cuestionarios.remove(rowIndex);
        fireTableDataChanged();
    }

    public void limpiarCuestionario() {
        cuestionarios.clear();
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
                return "No. Cuestionario";
            case 1:
                return "Tipo de Cuestionario";
            case 2:
                return "Resultado";
            case 3:
                return "Persona que Responde";
            case 4:
                return "Codigo de Supervisor(a)";
            case 5:
                return "Codigo de Encuestador(a)";
            case 6:
                return "Codigo de Editor(a)";
            case 7:
                return "Valido";    
                   
            default:
                return null;
        }
    }

    @Override
    public int getRowCount() {
        return cuestionarios.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return cuestionarios.get(rowIndex).getno_cuestinario();
            case 1:
                return cuestionarios.get(rowIndex).gettipo_cuestionario();
            case 2:
                return cuestionarios.get(rowIndex).getresultado();
            case 3:
                return cuestionarios.get(rowIndex).getlinea_pesona();
            case 4:
                return cuestionarios.get(rowIndex).getCodSupervisor();
            case 5:
                return cuestionarios.get(rowIndex).getCodEncuestador();
            case 6:
                return cuestionarios.get(rowIndex).getCodEditor();
            case 7:
                return cuestionarios.get(rowIndex).getvalido();    
                     
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
        Cuestionario cuestionario = cuestionarios.get(rowIndex);
        switch (columnIndex) {
            case 0:
                cuestionario.setno_cuestinario((Integer) value);
            case 1:
                cuestionario.settipo_cuestionario((Integer) value);
            case 2:
                cuestionario.setresultado((Integer) value);
            case 3:
                cuestionario.setlinea_pesona((Integer) value);
            case 4:
                cuestionario.setCodSupervisor((Integer) value);
            case 5:
                cuestionario.setCodEncuestador((Integer) value);
            case 6:
                cuestionario.setCodEditor((Integer) value);
            case 7:
                cuestionario.setvalido((String) value);    


        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
