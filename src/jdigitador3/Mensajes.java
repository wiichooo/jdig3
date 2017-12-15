/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdigitador3;

import java.awt.Component;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

/**
 *
 * @author LUIS
 */
class Mensajes {
    Bitacora b = new Bitacora();
    //MANEJO DE MENSAJES/ERRORES
    public void send(int error){
       switch(error){
            case 1:
                error("¡No existe conexión a la base de datos!", "Error");
                bitacora("No existe conexión a la base de datos.");
                break;
            case 2:
                error("¡No se puede conectar con el servidor ftp!", "Error");
                bitacora("No se puede conectar con el servidor ftp.");
                break;
            case 3:
                informacion("¡Cambio de estado de paquete registrado correctamente!", "Información");
                bitacora("Cambio de estado de paquete registrado correctamente.");
                break;
            case 4:
                error("¡Cambio no Registrado Correctamente!", "Error");
                bitacora("¡Cambio no Registrado Correctamente!");
                break;
            case 5:
                error("¡El archivo no esta creado aún!", "Error");
                bitacora("El archivo no esta creado aún.");
                break;
            case 6:
                error("¡Error no se encuentran las dos versiones de este paquete!", "Error");
                bitacora("Error no se encuentran las dos versiones de este paquete.");
                break;
            case 7:
                error("¡No se copio el archivo al servidor!", "Error");
                bitacora("No se copio el archivo al servidor.");
                break;
            case 8:
                info("¡El archivo se descargo del servidor ftp!", "Información");
                bitacora("El archivo se descargo del servidor ftp");
                break;
            case 9:
                info("¡El archivo se creo exitosamente!", "Información");
                bitacora("El archivo se creo exitosamente.");
                break;
            case 10:
                info("Este paquete ya finalizó la primera digitación.", "Información");
                break;
            case 11:
                info("Este paquete ya finalizó la segunda digitación.", "Información");
                break;
            case 12:
                info("Este paquete ya finalizó la verificación.", "Información");
                break;
            case 13:
                info("La verificación de paquetes no se realiza mediante esta función.", "Información");
                break;
            case 14:
                error("Error los archivos no estan correctos.\nNo pasan la verificación de Checksum.", "Error");
                break;
            case 15:
                error("Numero de hogares incorrecto o contienen errores.", "Error");
                break;
            case 16:
                info("El paquete esta correcto.", "Error");
                break;
            case 17:
                info("No hay ningun paquete seleccionado.", "Error");
                break;
            case 18:
                info("¡Observación enviada correctamente!", "Información");
                bitacora("¡Observación enviada correctamente!");
                break;
            case 19:
                error("¡Se debe ingresar la pregunta, la categoria y la descripción!", "Error");
                break;
            case 20:
                error("¡No se ha verificado el documento.!", "Error");
                break;
        }
    }
     /* BITACORA
     * Envia el string a a la bitacora.
     */
    public void bitacora(String a){
        try {
            b.escribir(a);
            System.out.println(a);
        }catch(Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //MENSAJES
    int advertencia(String textomsgbox, String titulomsgbox) {
        Component frame = null;
       int n = JOptionPane.showConfirmDialog(
        frame,
        textomsgbox,
        titulomsgbox,
        JOptionPane.YES_NO_OPTION);
       // System.out.println (n);
       //1 no 
       //0 si
        return n;
       
    }
    void error(String textomsgbox, String titulomsgbox) {
           Component frame = null;
          JOptionPane.showMessageDialog(
       frame,
       textomsgbox,
       titulomsgbox,
       JOptionPane.ERROR_MESSAGE);
          return;
    }
 
    void info(String textomsgbox, String titulomsgbox) {
        Component frame = null;
       JOptionPane.showMessageDialog(
        frame,
        textomsgbox,
        titulomsgbox,
        JOptionPane.INFORMATION_MESSAGE);
  
    }
    
    void save(String textomsgbox, String titulomsgbox){
        JOptionPane pane = new JOptionPane(textomsgbox);  
        JDialog dialog = pane.createDialog(titulomsgbox);  
        dialog.setAlwaysOnTop(true);  
        dialog.show();  
     }
 
    void InfoPk(String msj,String title){
      Component frame = null;
      JTextArea textArea = null;
      
      if(!title.equals("Verificacion del paquete")) {
            textArea = new JTextArea(16, 70);
        }
      else {
            textArea = new JTextArea(20, 70);
        } 
      
      textArea.setText(msj);
      textArea.setEditable(false);
      
      JScrollPane scrollPane = new JScrollPane(textArea);
      
      JOptionPane.showMessageDialog(frame, scrollPane, title, JOptionPane.WARNING_MESSAGE);
    }
    
    public void bienvenida(String string, String iniciando_Sesión) {
      JOptionPane messagePane = new JOptionPane(
            string + "\nIniciando...",
            JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
      final JDialog dialog = messagePane.createDialog(null, iniciando_Sesión);

      new SwingWorker<Void, Void>() {

         @Override
         protected Void doInBackground() throws Exception {
            // do your background processing here
            // for instance validate your board here

            // mimics a background process that takes 3 seconds
            // you would of course delete this in your actual progam
            Thread.sleep(1200); 

            return null;
         }

         // this is called when background thread above has completed.
         protected void done() {
            dialog.dispose();
         };
      }.execute();

      dialog.setVisible(true);
    }
    
    public void informacion(String string, String iniciando_Sesión) {
      JOptionPane messagePane = new JOptionPane(
            string,
            JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
      final JDialog dialog = messagePane.createDialog(null, iniciando_Sesión);

      new SwingWorker<Void, Void>() {

         @Override
         protected Void doInBackground() throws Exception {
            // do your background processing here
            // for instance validate your board here

            // mimics a background process that takes 3 seconds
            // you would of course delete this in your actual progam
            Thread.sleep(1800); 

            return null;
         }

         // this is called when background thread above has completed.
         protected void done() {
            dialog.dispose();
         };
      }.execute();

      dialog.setVisible(true);
    }
}
