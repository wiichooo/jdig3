/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import java.io.File;

/**
 *
 * @author Alejandro
 */
public class principal {
    
    
    public void compilar(){
    JFlex.Main.generate(new File("src\\PruebaXML\\lex.flex"));
        
        String opciones[] = new String[5];

        //Seleccionamos la opción de dirección de destino
        opciones[0] = "-destdir";
                //Le damos la dirección
        opciones[1] = "src\\PruebaXML\\";
                //Seleccionamos la opción de nombre de archivo
        opciones[2] = "-parser";
                //Le damos el nombre que queremos que tenga
        opciones[3] = "parser";
        //Le decimos donde se encuentra el archivo .cup
        opciones[4] = "src\\PruebaXML\\sint.cup";

        try {
            java_cup.Main.main(opciones);
        } catch (Exception e) {
            System.out.print(e);
        }
    
    }
    
}
