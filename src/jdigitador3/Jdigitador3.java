/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jdigitador3;

import com.alee.laf.WebLookAndFeel;
import java.awt.Component;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author LUIS
 */
public class Jdigitador3 {

    public static final String HELLO_MESSAGE = "already running";
    public static final int PORT = 7777;
    private Component frame;

    public Jdigitador3() {
        new Thread() {
            @Override
            public void run() {
                listen();
            }
        }.start();
    }
    
    public void listen() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(PORT);
        } catch (IOException e) {
            Socket s = null;
            try {
                s = new Socket("127.0.0.1", PORT);
                DataInputStream dis = new DataInputStream(s.getInputStream());
                String message = dis.readUTF();
                if (HELLO_MESSAGE.equals(message)) {
                    //   textField.setText("La aplicación ya se está ejecutando");
                    System.out.print("La aplicación ya se está ejecutando");
                    JOptionPane.showMessageDialog(frame,
                            "Intento acceder dos veces a la misma aplicacion. La"
                            + " aplicación ya se está ejecutando",
                            "ERROR, Aplicación",
                            JOptionPane.ERROR_MESSAGE);

                } else {
                    // textField.setText("Mmmm... algo se está ejecutando, pero  no parece ser nuestra aplicación");
                    System.out.print("Mmmm... algo se está ejecutando, pero no"
                            + " parece ser nuestra aplicación");
                    JOptionPane.showMessageDialog(frame,
                            "Mmmm... algo se está ejecutando, pero no parece ser"
                            + " nuestra aplicación",
                            "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (IOException ioe) {
                //    textField.setText("Ops. No se ha podido crear el ServerSocket ni conectar al puerto indicado...");
                System.out.print("Ops. No se ha podido crear el ServerSocket ni"
                        + " conectar al puerto indicado...");
                JOptionPane.showMessageDialog(frame,
                        "Ops. No se ha podido crear el ServerSocket ni conectar"
                        + " al puerto indicado...",
                        "ERROR, Aplicación",
                        JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (s != null) {
                        s.close();
                    }
                } catch (Exception ex) {
                }
            }
            return;
        }
        System.out.print("Somos la primera instancia de la aplicación!");
        new Thread() {

            @Override
            public void run() {
                try {
                    ejecutarAplicacion();
                } catch (IOException ex) {
                    Logger.getLogger(jdigitador3.Jdigitador3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();

        try {
            Socket s = null;
            while ((s = ss.accept()) != null) {
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeUTF(HELLO_MESSAGE);
                s.close();
            }
        } catch (Exception e) {
            // TODO
        }
    }

    
      private void ejecutarAplicacion() throws IOException {
        jdigitador3.Login rc = new jdigitador3.Login(new javax.swing.JFrame(), true);
        rc.setLocationRelativeTo(null);
        rc.setVisible(true);
      }
      
      
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName());
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    WebLookAndFeel.install();
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

               Jdigitador3 c = new Jdigitador3(); 
            }
        });
    }
    
}
