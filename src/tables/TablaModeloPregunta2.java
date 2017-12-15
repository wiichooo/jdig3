/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Luis
 */
public class TablaModeloPregunta2 extends AbstractTableModel {

    private List<Pregunta> preguntas = new ArrayList<Pregunta>();

    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
        fireTableDataChanged();
    }

    public void eliminarPregunta(int rowIndex) {
        preguntas.remove(rowIndex);
        fireTableDataChanged();
    }

    public void limpiarPregunta() {
        preguntas.clear();
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
                return "No.Hogar";
            case 2:
                return "No.Cuestionario";
            case 3:
                return "No.Pregunta";
            case 4:
                return "Pregunta";
            case 5:
                return "Ocurrencia";
            case 6:
                return "Respuesta A";
            case 7:
                return "Respuesta B";
            case 8:
                return "Respuesta X";
            case 9:
                return  "Validado";
                
                   
            default:
                return null;
        }
    }

    @Override
    public int getRowCount() {
        return preguntas.size();
    }

    @Override
    public int getColumnCount() {
        return 10;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return preguntas.get(rowIndex).getNopaquete();
            case 1:
                return preguntas.get(rowIndex).getNohogar();
            case 2:
                return preguntas.get(rowIndex).getNocuestionario();
            case 3:
                return preguntas.get(rowIndex).getno_pregunta();
            case 4:
                return preguntas.get(rowIndex).getpregunta();
            case 5:
                return preguntas.get(rowIndex).getNocc();
            case 6:
                return preguntas.get(rowIndex).getrespuestaA();
            case 7:
                return preguntas.get(rowIndex).getrespuestaB();
            case 8:
                return preguntas.get(rowIndex).getrespuestaX();
            case 9:
                return preguntas.get(rowIndex).getvalidado();
                
       
                     
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
        if(columnIndex == 8)
            return true;
        else
            return false;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Pregunta pregunta = preguntas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                pregunta.setNopaquete((Integer) value);
                break;
            case 1:
                pregunta.setNohogar((Integer) value);
                break;
            case 2:
                pregunta.setNocuestionario((String) value);
                break;
            case 3:
                pregunta.setno_pregunta((String) value);
                break;
            case 4:
                pregunta.setpregunta((String) value);
                break;
            case 5:
                pregunta.setNocc((Integer)value);
                break;
            case 6:
                pregunta.setrespuestaA((String) value);
                break;
            case 7:   
                   pregunta.setrespuestaB((String) value);
                break;
            case 8:
                   pregunta.setrespuestaX((String) value);        
                break;
            case 9:
                    pregunta.setvalidado((String) value);
                break;

        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    public List<Pregunta> getPreguntas(){
        return preguntas;
    }
    public void setPreguntas(List<Pregunta> pg){
        preguntas = pg;
    }
}
