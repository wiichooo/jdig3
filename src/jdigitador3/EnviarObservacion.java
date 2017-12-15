/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdigitador3;

import Conexion.DBClient;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 *
 * @author LUIS
 */
public class EnviarObservacion extends javax.swing.JDialog {

    /**
     * Creates new form EnviarObservacion
     */
    Propiedades rc;
    private DBClient con;
    private Connection c;
    private Mensajes msg = new Mensajes();
    String tipo = ""; 
    public EnviarObservacion(java.awt.Frame parent, boolean modal, Propiedades rc,
            String usuario, String paquete, String tipo_paquete, String hogar, String cuestionario, String tipo, Object[] elements) {
        super(parent, modal);
        initComponents();
        this.tipo = tipo;
        this.rc = rc;
           BufferedImage image = null;
        try {
            image = ImageIO.read(
                        this.getClass().getResource("/icons/16/Chat 2.png"));
        } catch (IOException e) {
        }
        this.setIconImage(image);
        jRadioButton3.setSelected(true);
        txtusuario.setText(usuario);
        final SimpleDateFormat formatter2 = new SimpleDateFormat("kk:mm:ss");
        txthora.setText(formatter2.format(new Date()));
        Fechahoy d = new Fechahoy();
        txtfecha.setText(d.damefecha());
        txtpaquete.setText(paquete);
        txthogar.setText(hogar);
        txtcuestio.setText(cuestionario);
        txttpaquete.setText(tipo_paquete);
        
//        Object[] elements = new Object[] {"(Eliga una categoria)", "Sin respuesta", 
//    "Numero Ilegible", "Respuesta Incorrecta", "Salto Incorrecto", "Falta Equivalencia", 
//    "Cambio de Linea", "Cambio de Miembros", "Cambio de Mujeres Elegibles", "Cambio de Niños Elegibles" };
        
    AutoCompleteSupport.install(jComboBox1, GlazedLists.eventListOf(elements));
    jComboBox1.setSelectedIndex(0);
        
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtpaquete = new javax.swing.JTextField();
        txthogar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtpregunta = new javax.swing.JTextField();
        txtusuario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtfecha = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txthora = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtdesc = new javax.swing.JTextArea();
        txtcuestio = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        txttpaquete = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Envio de observaciones e inconsistencias");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Symbol Restricted.png"))); // NOI18N
        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Symbol Check.png"))); // NOI18N
        jButton2.setText("Aceptar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Paquete");

        jLabel2.setText("Hogar");

        jLabel3.setText("Digitador");

        txtpaquete.setEditable(false);

        txthogar.setEditable(false);

        jLabel4.setText("Cuestionario");

        txtpregunta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpreguntaActionPerformed(evt);
            }
        });

        txtusuario.setEditable(false);

        jLabel5.setText("Fecha");

        txtfecha.setEditable(false);

        jLabel6.setText("Hora");

        txthora.setEditable(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel7.setText("Describa la inconcistencia que detecto y presione aceptar");

        jLabel8.setText("Observaciones del cuestionario");

        txtdesc.setColumns(20);
        txtdesc.setLineWrap(true);
        txtdesc.setRows(5);
        jScrollPane1.setViewportView(txtdesc);

        txtcuestio.setEditable(false);

        jLabel10.setText("Pregunta");

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("!URGENTE!");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Revisar");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Puede esperar");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(Eliga una categoria)", "Sin respuesta", "Numero Ilegible", "Respuesta Incorrecta", "Salto Incorrecto", "Falta Equivalencia", "Cambio de Linea", "Cambio de Miembros", "Cambio de Mujeres Elegibles", "Cambio de Niños Elegibles" }));

        jLabel9.setText("Categoria");

        txttpaquete.setEditable(false);
        txttpaquete.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txttpaquete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttpaqueteActionPerformed(evt);
            }
        });

        jLabel11.setText("Tipo");

        jLabel12.setText("Tipo:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(Elegir tipo)", "Edición", "Digitación" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(90, 90, 90))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1)))
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButton2)
                                    .addComponent(jRadioButton1)
                                    .addComponent(jRadioButton3)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtpaquete, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(8, 8, 8)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel5)
                                                    .addComponent(jLabel11)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txttpaquete, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txthogar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel2)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txthora, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10)
                                    .addComponent(txtcuestio, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtpregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(19, 19, 19))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtpaquete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txthogar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcuestio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txttpaquete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txthora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtpregunta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox2)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(jLabel8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton3)
                        .addGap(3, 3, 3)))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        if(!txtpregunta.getText().equals("") && !txtdesc.getText().equals("") 
                && jComboBox1.getSelectedIndex() != 0 && jComboBox1.getSelectedIndex() != -1){
            int prioridad = 0;
            if(jRadioButton1.isSelected()) {
                prioridad = 3;
            }
            else if(jRadioButton2.isSelected()) {
                prioridad = 2;
            }
            else if(jRadioButton3.isSelected()) {
                prioridad = 1;
            }
            String tipoObs = jComboBox2.getSelectedItem().toString();
            try {
                con = new DBClient();
                c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
                        ,rc.getPuertoBD()); 

//                con.insertarOb(c, Integer.valueOf(txtpaquete.getText()), txttpaquete.getText(), Integer.valueOf(txthogar.getText()),
//                        Integer.valueOf(txtcuestio.getText()), Integer.valueOf(txtusuario.getText()),
//                        txtpregunta.getText(), prioridad ,txtdesc.getText(), tipo, con.getcategoriaID(c, jComboBox1.getSelectedItem().toString()));
                con.insertarObCu(c, 
                        Integer.valueOf(txtcuestio.getText()), Integer.valueOf(txtusuario.getText()),
                        txtpregunta.getText(), prioridad ,txtdesc.getText(), tipo, con.getcategoriaID(c, jComboBox1.getSelectedItem().toString()),tipoObs);
                c.close();
            } catch (SQLException ex) {
                msg.send(Variables.Error_BD);
            }
            msg.send(Variables.Observacion_Correcta);
            this.dispose();
        }else{
            msg.send(Variables.Error_Observacion);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtpreguntaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpreguntaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpreguntaActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void txttpaqueteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttpaqueteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttpaqueteActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtcuestio;
    private javax.swing.JTextArea txtdesc;
    private javax.swing.JTextField txtfecha;
    private javax.swing.JTextField txthogar;
    private javax.swing.JTextField txthora;
    private javax.swing.JTextField txtpaquete;
    private javax.swing.JTextField txtpregunta;
    private javax.swing.JTextField txttpaquete;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}