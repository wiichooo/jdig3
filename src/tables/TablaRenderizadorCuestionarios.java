/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Luis
 */
public class TablaRenderizadorCuestionarios implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel etiqueta = new JLabel();
        etiqueta.setOpaque(true);
        /*
         * Coloreamos las filas
         */
        if (row % 2 == 0) {
            etiqueta.setBackground(new Color(218, 217, 217));
        } else {
            etiqueta.setBackground(Color.white);
        }
      
        if (column == 0) {
         
            
            String numCadena = String.valueOf(value);
            etiqueta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            etiqueta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Documents.png")));
           
            etiqueta.setText(value.toString().substring(0, numCadena.length()));

        }else if (column == 7) {
            // String nombre = (String) value;
            //String paquete = value;
            
            String numCadena = String.valueOf(value);
            etiqueta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            if (numCadena.matches(" ")){
            etiqueta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Symbol Check.png")));}
            else if (numCadena.matches("\u200b")){
              etiqueta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Symbol Error 3.png")));}
            
            // Color colorete = (Color) value;
            // etiqueta.setBackground(colorete);
            etiqueta.setText(value.toString().substring(0, numCadena.length()));

        } else {
            etiqueta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            etiqueta.setText(value.toString());
        }
      
        /*
         * Para una fila seleccionada
         */
        if (isSelected) {
            etiqueta.setBackground(new Color(151, 193, 215));
        }
        return etiqueta;
    }
}
