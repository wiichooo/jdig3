/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdigitador3;

import Conexion.ftp;
import Conexion.SFTPClient;
import Conexion.DBClient;
import Conexion.FtpClient;
import Ejecutar.*;
import com.jcraft.jsch.JSchException;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JToolTip;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.commons.io.FileUtils;
import pruebaxml.PruebaXML;
import tables.*;

public class Inicio extends javax.swing.JFrame {

    /**
     * Creates new form Inicio
     */
    public Mensajes mensaje = new Mensajes();
    private final Dimension d;
    //Tablas
    TablaModeloPaquete modeloPaquete = new TablaModeloPaquete();;
    TablaModeloHogar modeloHogar = new TablaModeloHogar();
    TablaModeloCuestionario modeloCuestionario = new TablaModeloCuestionario();
    TablaRenderizadorPaquetes renderizador = new TablaRenderizadorPaquetes();
    TablaRenderizadorHogares renderizador2 = new TablaRenderizadorHogares();
    TablaRenderizadorCuestionarios renderizador3 = new TablaRenderizadorCuestionarios();
    private ListSelectionModel listSelectionModel2;
    
    ArrayList<Hogar> listahogares;
    ArrayList<Cuestionario> listacuestionarios;
    pruebaxml.PruebaXML generador;
    public Propiedades rc;
    
    //Propiedades
    int grupo;          
    long idUsuario;
    String idUsuarioLog;
    String usuario, pathCSPro, pathArchivosLocales, pathXML;
    //XML
    String entradaA,entradaB,xml,xmlotros;
     Object[] elements = null; ///categorias
    int paqueteactual = 0;
    String estadoactual = "";
    String nopaqueteselecionado = "";
    Popup tooltipPopup;
    
    //LOGIN
    Login lg;
    public String error,errorC;
    boolean verificarChecksum = false;
    private String DicENTRY;

    public Inicio(Login lg, Propiedades rc) {
//        this.lg = lg;
        initComponents();  
        jProgressBar1.setMaximum(100);
        jProgressBar1.setMinimum(0);
        jProgressBar1.setStringPainted(true);
        btnEnviar.setEnabled(false);
        btnVerificar.setEnabled(false);
        btnCheck.setEnabled(false);
        jMenuItem5.setVisible(false);
        
        generador = new PruebaXML();
        this.rc = rc;
        this.lg = lg;
        usuario = lg.getUsuario();
        grupo = lg.getGrupo();
        idUsuario = lg.getID();
        idUsuarioLog = lg.getIDLog();
        pathCSPro = lg.getPathCSPRO();
        pathXML = lg.getPathCuestionarios();
        pathArchivosLocales = lg.getPathArchivosLocales();
        xml = lg.StrDic;
//        mensaje.info("Bienvenido(a) " + lg.getNombre() + " " + lg.getApellido() , "Iniciando Sesión...");
        mensaje.bienvenida("Bienvenido(a) " + lg.getNombre() + " " + lg.getApellido() , "Iniciando Sesión...");
        //Ocultar el boton de la propiedades.
        ///btnProp.setVisible(false);
        jLabel7.setText(lg.getID() + " - " + lg.getNombre() + " " + lg.getApellido());
        AddListeners();
        Muestrafecha();
        Muestrahora();

        mensaje.bitacora("Se inicio sesion con el usuario: " + jLabel7.getText());

        
        jTablePaquete.setModel(modeloPaquete);
        jTablePaquete.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTablePaquete.setDefaultRenderer(String.class, renderizador);
        jTableHogares.setModel(modeloHogar);
        jTableHogares.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableHogares.setDefaultRenderer(String.class, renderizador2);
        jTableCuestio.setModel(modeloCuestionario);
        jTableCuestio.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableCuestio.setDefaultRenderer(String.class, renderizador3);
        
        //ImageIcon img = new ImageIcon("/icons/16/Group.png");
        //this.setIconImage(img.getImage());
        BufferedImage image = null;
        try {
            image = ImageIO.read(
                    this.getClass().getResource("/icons/16/Keyboard.png"));
        } catch (IOException e) {
        }
        this.setIconImage(image);

        //ancho del splitpanel vertical
        jSplitPane1.setDividerLocation(500);

        // jSplitPane1.setOneTouchExpandable(false);
        jSplitPane1.setEnabled(false);

        ///ancho del split jSplitPaneCuestionario
        d = CuetionariojSplitPane.getSize();
        int x;
        x = (int) d.getHeight();
        CuetionariojSplitPane.setDividerLocation(x);
        CuetionariojSplitPane.setResizeWeight(.315d);
         
        /*Para evitar que se reordenen las columnas*/
        jTablePaquete.getTableHeader().setReorderingAllowed(false);
        jTableHogares.getTableHeader().setReorderingAllowed(false);
        jTableCuestio.getTableHeader().setReorderingAllowed(false);
        jTablePaquete.setEnabled(true);
        
        /////Conexion
        try {
            DBClient con = new DBClient();
            Connection c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
                    ,rc.getPuertoBD()); 
            //Llena las tablas           
            SetPaquetes(con,c);
            c.close();
        } catch (SQLException ex) {mensaje.send(Variables.Error_BD);}
        
        if(estadoactual.contains("Pausa")) {
            if(RegistrarCambio(1, String.valueOf(paqueteactual), estadoactual,"")) {
                mensaje.send(Variables.Cambio_Correcto);
            }
        }

        btnActualizarActionPerformed(null);
        setVisible(true);     
        CargarArchivo();
        
        //Categorias de las observaciones
        getCategorias();
        paquete_correcto(false);
        btnVerificar.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel_Fecha = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel_Hora = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jToolBar1 = new javax.swing.JToolBar();
        btnCSPro = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnActualizar = new javax.swing.JButton();
        btnCheck = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnEnviar = new javax.swing.JButton();
        btnVerificar = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnObser = new javax.swing.JButton();
        btnAyuda = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        CuetionariojSplitPane = new javax.swing.JSplitPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableHogares = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableCuestio = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTablePaquete = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jToolBar2 = new javax.swing.JToolBar();
        btnSalir = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jmVerificar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("JDigitador 3.0");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Calender 31 Day.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Folder Network Check.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Server Check.png"))); // NOI18N

        jLabel_Fecha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_Fecha.setText("Fecha");

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Time 2.png"))); // NOI18N

        jLabel_Hora.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel_Hora.setText("Hora");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("123 - Juan Perez");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/User 7 Check.png"))); // NOI18N

        jProgressBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Hora, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel_Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel_Hora, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnCSPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48/cspro.fw.png"))); // NOI18N
        btnCSPro.setFocusable(false);
        btnCSPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCSPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCSPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCSProActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCSPro);
        jToolBar1.add(jSeparator1);

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48/Symbol Refresh 4.png"))); // NOI18N
        btnActualizar.setFocusable(false);
        btnActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnActualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnActualizar);

        btnCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48/symbol_check.png"))); // NOI18N
        btnCheck.setFocusable(false);
        btnCheck.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCheck.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCheck);
        jToolBar1.add(jSeparator5);

        btnEnviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48/pk.fw.png"))); // NOI18N
        btnEnviar.setFocusable(false);
        btnEnviar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEnviar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEnviar);

        btnVerificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48/Clipboard Edit 2.png"))); // NOI18N
        btnVerificar.setFocusable(false);
        btnVerificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVerificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVerificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerificarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnVerificar);
        jToolBar1.add(jSeparator3);

        btnObser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48/Chat 2.png"))); // NOI18N
        btnObser.setFocusable(false);
        btnObser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnObser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnObser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObserActionPerformed(evt);
            }
        });
        jToolBar1.add(btnObser);

        btnAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48/Help.png"))); // NOI18N
        btnAyuda.setFocusable(false);
        btnAyuda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAyuda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAyudaActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAyuda);

        CuetionariojSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jTableHogares.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableHogares.setShowHorizontalLines(false);
        jTableHogares.setShowVerticalLines(false);
        jScrollPane3.setViewportView(jTableHogares);

        CuetionariojSplitPane.setTopComponent(jScrollPane3);

        jTableCuestio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTableCuestio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableCuestio.setShowHorizontalLines(false);
        jTableCuestio.setShowVerticalLines(false);
        jScrollPane4.setViewportView(jTableCuestio);

        CuetionariojSplitPane.setRightComponent(jScrollPane4);

        jSplitPane1.setRightComponent(CuetionariojSplitPane);

        jTablePaquete.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"123", "En digitación"},
                {"456", "Finalizado 1era"},
                {"565", "Finalizado 2da"},
                {"478", "Verificado"}
            },
            new String [] {
                "Paquete", "Estado"
            }
        ));
        jTablePaquete.setShowHorizontalLines(false);
        jTablePaquete.setShowVerticalLines(false);
        jScrollPane5.setViewportView(jTablePaquete);

        jSplitPane1.setLeftComponent(jScrollPane5);

        jLabel4.setToolTipText("");

        jToolBar2.setRollover(true);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/48/Logout.png"))); // NOI18N
        btnSalir.setFocusable(false);
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jToolBar2.add(btnSalir);

        jMenu1.setText("Archivo");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Logout.png"))); // NOI18N
        jMenuItem1.setText("Salir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Utilidades");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Keyboard.png"))); // NOI18N
        jMenuItem2.setText("Digitar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/ClipboardEdit2.png"))); // NOI18N
        jMenuItem3.setText("Verificar");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);
        jMenu2.add(jSeparator4);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Chat 2.png"))); // NOI18N
        jMenuItem4.setText("Enviar Observaciones");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Symbol Configuration.png"))); // NOI18N
        jMenuItem5.setText("Propiedades");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Acerca de");

        jMenuItem6.setText("Acerca de..");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuItem7.setText("Calcular Edad");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jmVerificar.setText("Verificar");
        jmVerificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmVerificarActionPerformed(evt);
            }
        });
        jMenu3.add(jmVerificar);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
            .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel4))
                    .addComponent(jToolBar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        btnCSProActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        abrepropiedades();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        salir();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        try {
            DBClient con = new DBClient();
            Connection c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
                    ,rc.getPuertoBD()); 
            //Llena las tablas
            listacuestionarios = null;
            SetPaquetes(con,c);
            c.close();
        } catch (SQLException ex) {mensaje.send(Variables.Error_BD);}
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        salir();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        salir();
    }//GEN-LAST:event_jMenuItem1ActionPerformed
     /*
     * Verifica el estado del paquete seleccionado.
     * Verifica si el paquete existe localmente.
     * NO: Verifica si se encuentra en el servidor FTP
     * Ejecuta CSPro, ya sea si lo bajo del servidor o existia localmente o lo creo
     */
    private void btnCSProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCSProActionPerformed

        try{
        String noPaquete = jTablePaquete.getValueAt(jTablePaquete.getSelectedRow(), 0).toString();
        String estado = jTablePaquete.getValueAt(jTablePaquete.getSelectedRow(), 2).toString(); 
        File f = null;
        File fotros = null;
        File fsts = null;
        if(estado.equals(Variables.EnD18)){
            f = new File(pathArchivosLocales + "//" + Variables.Diccionario + "//"+Variables.Prefijo_Tipo_Paquete+ noPaquete+"-"+idUsuario+"P");
            fotros = new File(pathArchivosLocales + "//" + Variables.Diccionario + "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+"-"+idUsuario+"Potros");
             ftp ftp = getFTPServer();
                if(ftp.conexion(rc.getDireccionSA(), rc.getUsuarioSA(), rc.getPassSA(),22)){
                    if(!f.exists()) {
                        if(ftp.downloadFile(f) && ftp.downloadFile(fotros)){
//                                System.out.println(CheckSum.getMD5Checksum(f));
                                mensaje.send(Variables.Archivo_Descargado);
                        }
                    }
                                mensaje.bitacora("Se inicio/continuo la digitación del paquete: " + (noPaquete+"-"+idUsuario+"P"));
                                new ejecutar().csproDIGITA(noPaquete, pathArchivosLocales, 1, pathCSPro,idUsuario+"P", DicENTRY);
                  ftp.disconnect();
                }else{
                    mensaje.send(Variables.Error_FTP);
                }

        }else if(estado.equals(Variables.EnD211)){
            f = new File(pathArchivosLocales + "//" + Variables.Diccionario +"//"+Variables.Prefijo_Tipo_Paquete + noPaquete+"-"+idUsuario+"S");
            fotros = new File(pathArchivosLocales + "//" + Variables.Diccionario +"//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+"-"+idUsuario+"Sotros");
            fsts = new File(pathArchivosLocales + "//" + Variables.Diccionario +"//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+"-"+idUsuario+"S.sts");
            File flog = new File(pathArchivosLocales + "//" + Variables.Diccionario +"//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+"-"+idUsuario+"S.log");
            String PD = "";
                try { 
                    DBClient con = new DBClient();
                    Connection c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
                            ,rc.getPuertoBD());
                    PD = con.getPrimerDigitador(c, noPaquete, Variables.Tipo_Paquete);
                    c.close();
                }catch (SQLException ex) {}
            
            File fpd = new File(pathArchivosLocales + "//" + Variables.Primera_Digitacion + "//" + Variables.Prefijo_Tipo_Paquete + noPaquete +
                    "//" + Variables.Prefijo_Tipo_Paquete + noPaquete + "-" + PD + "P");
            File fpdotros = new File(pathArchivosLocales + "//" + Variables.Primera_Digitacion + "//" + Variables.Prefijo_Tipo_Paquete + noPaquete +
                    "//" + Variables.Prefijo_Tipo_Paquete + noPaquete + "-" + PD + "Potros");
            
            ftp ftp = getFTPServer();    
                if(ftp.conexion(rc.getDireccionSA(), rc.getUsuarioSA(), rc.getPassSA(), 22)){
//                if(ftp.connect(rc.getDireccionSA(), rc.getUsuarioSA(), rc.getPassSA(),22)){
                    if(!f.exists()) {
                        if(ftp.downloadFile(fotros) && ftp.downloadFile(f)){
                            try{
                                ftp.downloadFile(fsts);
                                ftp.downloadFile(flog);
                            }catch(Exception e){
                                System.out.println(".STS o .LOG no descargado.");
                            }
//                                System.out.println(CheckSum.getMD5Checksum(f));
                                mensaje.send(Variables.Archivo_Descargado);
                        }
                        else{
                            if(!fpd.exists()){
                                if(ftp.downloadFileE(fpd) && ftp.downloadFileE(fpdotros)){
        //                                System.out.println(CheckSum.getMD5Checksum(f));
                                        mensaje.send(Variables.Archivo_Descargado);
                                }
                            }
                            FileUtils.copyFile(fpd, f);
                            FileUtils.copyFile(fpdotros, fotros);
                        }
                        
                    }
                                mensaje.bitacora("Se inicio/continuo la digitación del paquete: " + (noPaquete+"-"+idUsuario+"S"));
                                new ejecutar().csproDIGITA(noPaquete, pathArchivosLocales, 1, pathCSPro,idUsuario+"S", DicENTRY);
                   ftp.disconnect();             
                }else{
                    mensaje.send(Variables.Error_FTP);
                }  
            
        }else if(estado.equals(Variables.EnV14)){mensaje.send(Variables.No_Ver);}
        else if(estado.equals(Variables.D110)){mensaje.send(Variables.Fin_D1);}
        else if(estado.equals(Variables.D213)){mensaje.send(Variables.Fin_D2);}
        else if(estado.equals(Variables.V16)){mensaje.send(Variables.Fin_Ver);}
            
        }catch(Exception e){System.err.println(e);}
        paquete_correcto(false);
    }//GEN-LAST:event_btnCSProActionPerformed

    private void btnVerificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificarActionPerformed
//        new Thread() {
//                    public void run() {
//                        abreverifica();
//                    }
//                }.start();
    }//GEN-LAST:event_btnVerificarActionPerformed

    private void btnObserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObserActionPerformed
        enviarobserva();
    }//GEN-LAST:event_btnObserActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        btnVerificarActionPerformed(evt);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
//        enviarobserva();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void btnAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAyudaActionPerformed
//        acercade();
        if(error != null || errorC != null) {
            if(!error.equals("") || !errorC.equals("")){
            String reporte = "Verificacion del paquete - Hogares\n\n"+ error + 
                    "\n\nVerificacion del paquete - Cuestionarios\n\n" + errorC;
            mensaje.InfoPk(reporte, "Verificacion del paquete");
            }
        }
       
    }//GEN-LAST:event_btnAyudaActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
//        acercade();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        try{
            String estado = jTablePaquete.getValueAt(jTablePaquete.getSelectedRow(), 2).toString();
          
            if(!estado.equals(Variables.EnV14)){
                new Thread() {
                    public void run() {
                        enviar();
                        paquete_correcto(false);
                    }
                }.start();
            }else{
                mensaje.send(13);
            }
        }catch(Exception e){mensaje.send(17);}

    }//GEN-LAST:event_btnEnviarActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
       //new CalcularEdad();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jmVerificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmVerificarActionPerformed
     
        new Thread() {
                    public void run() {
                        comprobar();
                    }
                }.start();
    }//GEN-LAST:event_jmVerificarActionPerformed

    private void btnCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckActionPerformed
                  
        new Thread() {
                    public void run() {
                       comprobar();
                    }
                }.start();
        
    }//GEN-LAST:event_btnCheckActionPerformed
   
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane CuetionariojSplitPane;
    public javax.swing.JButton btnActualizar;
    public javax.swing.JButton btnAyuda;
    public javax.swing.JButton btnCSPro;
    public javax.swing.JButton btnCheck;
    public javax.swing.JButton btnEnviar;
    public javax.swing.JButton btnObser;
    public javax.swing.JButton btnSalir;
    public javax.swing.JButton btnVerificar;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel_Fecha;
    public javax.swing.JLabel jLabel_Hora;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    public javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JSplitPane jSplitPane1;
    public javax.swing.JTable jTableCuestio;
    public javax.swing.JTable jTableHogares;
    public javax.swing.JTable jTablePaquete;
    public javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JMenuItem jmVerificar;
    // End of variables declaration//GEN-END:variables
    private void Muestrafecha() {
        Fechahoy d = new Fechahoy();
        jLabel_Fecha.setText(d.damefecha());
    }

    private void Muestrahora() {
        //MUESTRA LA HORA MH
        final SimpleDateFormat formatter2 = new SimpleDateFormat("kk:mm:ss");
        jLabel_Hora.setText("Hora de Ingreso: " +formatter2.format(new Date()));
    }
    
    //VERIFICACION DE CONEXION CON LOS SERVIDORES
    
    private void servidorArchivosActivo(){
        final ftp ftp = getFTPServer();
        
        final Mensajes msg = new Mensajes();
        new javax.swing.Timer(200000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //Verificar conexion con el servidor ftp
                    if(ftp.conexion(rc.getDireccionSA(), rc.getUsuarioSA(), rc.getPassSA(),22)) {
                        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Folder Network Check.png")));
                        ftp.disconnect();
                        
                    }else {
                        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Folder Network Error.png")));
                        mensaje.bitacora("Se desconecto del servidor FTP.");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                }
//                tempo += 30;
//                if(tempo == 1) {
//                    mensaje.save("Guarde su progreso de digitación.","Información");
//                    tempo = 0;
//                }
            }
        }).start();
    }
    
    private void servidorBDActivo(){
        final DBClient con = new DBClient();
        new javax.swing.Timer(200000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {             
                try { 
                    Connection c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
                            ,rc.getPuertoBD());
                    jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Server Check.png")));
                    c.close();
//                     mensaje.save("Guarde su progreso de digitación.","Información");
                }catch (SQLException ex) {
                    jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/16/Server Error.png")));
                    mensaje.bitacora("Se desconecto del servidor de Base de Datos.");
                }
            }
        }).start();
    }  
    
    private void AddListeners(){      
        listSelectionModel2 =  jTableHogares.getSelectionModel();
        jTableHogares.setSelectionModel(listSelectionModel2);
        listSelectionModel2.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if( !e.getValueIsAdjusting() ){
                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        HogarSelected(jTableHogares.getValueAt(i, 0).toString(), String.valueOf(paqueteactual));
                        break;
                    }
                }
                }
            }

            private void HogarSelected(String toString, String nopaquete) {
                try {
                    DBClient con = new DBClient();
                    Connection c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
                            ,rc.getPuertoBD()); 
                    SetCuestionarios(con, c, toString, nopaquete);
                    c.close();
                } catch (SQLException ex) {mensaje.send(Variables.Error_BD);}
  
            }
        });
    }
    
    private void SetPaquetes(DBClient con, Connection c){
        ResultSet paquetes = con.getPaquetes(c, idUsuario);
        modeloPaquete = new TablaModeloPaquete();  
        int ver = 0; int npk = -1; int ac = jTablePaquete.getRowCount()-1;
        try{
            boolean VB = false;
            while(paquetes.next()){
                ///////////////////////////////////////////////////////////////////////////////
                String dt = null; //TIEMPO 

                            
      ////////////////////////////////////////////////////////////////////////////////
                if(idUsuario == paquetes.getInt("pk.digitador1") && paquetes.getInt("epk.id")>=Variables.Dig1) {
                    dt = con.getTimeSpend(c, paquetes.getInt("pk.numero"),Variables.EnDig1 , Variables.PDig1, Variables.Dig1);
                    try{
                    modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt("pk.numero"), 
                          paquetes.getDate("pk.fecha_viaje").toString(), dt, Variables.D110, paquetes.getString("tp.nombre"),
                            paquetes.getInt("tp.id")));
                    }catch(Exception e){
                        modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt("pk.numero"), 
                          "", dt, Variables.D110,paquetes.getString("tp.nombre"),paquetes.getInt("tp.id")));
                    }
                    npk += 1;
                    VB = true;
                }
                
                else if(idUsuario == paquetes.getInt("pk.digitador2") && paquetes.getInt("epk.id")>=Variables.Dig2) {
                    dt = con.getTimeSpend(c, paquetes.getInt("pk.numero"),Variables.EnDig2 , Variables.PDig2, Variables.Dig2);
                    try{
                    modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt("pk.numero"), 
                          paquetes.getDate("pk.fecha_viaje").toString(), dt, Variables.D213,paquetes.getString("tp.nombre"),paquetes.getInt("tp.id")));
                    }catch(Exception e){
                        modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt("pk.numero"), 
                          "", dt, Variables.D213,paquetes.getString("tp.nombre"),paquetes.getInt("tp.id")));
                    }
                    npk += 1;
                    VB = true;
                }
                
                else if(idUsuario == paquetes.getInt("pk.digitador3") && paquetes.getInt("epk.id")>=Variables.Ver) {
                    dt = con.getTimeSpend(c, paquetes.getInt(1),Variables.EnVer , Variables.PVer,Variables.Ver);
                    try{
                    modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt(1), 
                          paquetes.getDate("pk.fecha_viaje").toString(), dt, Variables.V16,paquetes.getString("tp.nombre"),paquetes.getInt("tp.id")));
                    }catch(Exception e){
                        modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt("pk.numero"), 
                          "", dt, Variables.V16,paquetes.getString("tp.nombre"),paquetes.getInt("tp.id")));
                    }
                    npk += 1;
                    VB = false;
                }
                
               else{
                if(paquetes.getString(3).contains("En") || paquetes.getString(3).contains("Pausa")){ 
                paqueteactual = paquetes.getInt(1); 
                estadoactual = paquetes.getString(3);
                        
                ver+= 1;
                npk += 1;ac = npk;
//                }       
//                String dt = null;
//                        try {
//
                            if(estadoactual.contains("1"))
                                dt = con.getTimeSpend(c, paqueteactual,Variables.EnDig1 , Variables.PDig1, Variables.Dig1);
                            else if(estadoactual.contains("2"))
                                dt = con.getTimeSpend(c, paqueteactual,Variables.EnDig2 , Variables.PDig2, Variables.Dig2);
                            else if(estadoactual.contains("Ver"))
                                dt = con.getTimeSpend(c, paqueteactual,Variables.EnVer , Variables.PVer, Variables.Ver);

//                        } catch (ParseException ex) {
//                            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt(1), 
//                        paquetes.getDate(2).toString(), paquetes.getTime(2).toString(), String.valueOf(paquetes.getString(3)))); 
                            try{
                                modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt(1), 
                                paquetes.getDate("pk.fecha_viaje").toString(), dt, String.valueOf(paquetes.getString(3)),paquetes.getString("tp.nombre"),paquetes.getInt("tp.id"))); 
                            }catch(Exception e){
                                modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt(1), 
                                "", dt, String.valueOf(paquetes.getString(3)),paquetes.getString("tp.nombre"),paquetes.getInt("tp.id")));
                           }
                   VB = false;
                }
             }
                if((paquetes.getString(3).contains("En V") || paquetes.getString(3).contains("Pausa V"))
                        && VB  && idUsuario == paquetes.getInt("pk.digitador3")){ 
                paqueteactual = paquetes.getInt(1); 
                estadoactual = paquetes.getString(3);
                        
                ver+= 1;
                npk += 1;ac = npk;
//                }       
//                String dt = null;
//                        try {
//
                            if(estadoactual.contains("1"))
                                dt = con.getTimeSpend(c, paqueteactual, Variables.EnDig1 , Variables.PDig1, Variables.Dig1);
                            else if(estadoactual.contains("2"))
                                dt = con.getTimeSpend(c, paqueteactual,Variables.EnDig2 , Variables.PDig2, Variables.Dig2);
                            else if(estadoactual.contains("Ver"))
                                dt = con.getTimeSpend(c, paqueteactual,Variables.EnVer , Variables.PVer, Variables.Ver);

//                        } catch (ParseException ex) {
//                            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt(1), 
//                        paquetes.getDate(2).toString(), paquetes.getTime(2).toString(), String.valueOf(paquetes.getString(3)))); 
                            try{
                                modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt(1), 
                                paquetes.getDate("pk.fecha_viaje").toString(), dt, String.valueOf(paquetes.getString(3)),paquetes.getString("tp.nombre"),paquetes.getInt("tp.id"))); 
                             }catch(Exception e){
                                modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt(1), 
                                "", dt, String.valueOf(paquetes.getString(3)),paquetes.getString("tp.nombre"),paquetes.getInt("tp.id")));
                           }

                }
                if(paquetes.getString(3).startsWith("V")
                        && VB  && idUsuario == paquetes.getInt("pk.digitador3")){ 
                dt = con.getTimeSpend(c, paquetes.getInt(1),Variables.EnVer , Variables.PVer, Variables.Ver);
                try{
                          modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt(1), 
                          paquetes.getDate("pk.fecha_viaje").toString(), dt, Variables.V16,paquetes.getString("tp.nombre"),paquetes.getInt("tp.id")));
                    }catch(Exception e){
                        modeloPaquete.agregarPaquete(new Paquete(paquetes.getInt("pk.numero"), 
                          "", dt, Variables.V16,paquetes.getString("tp.nombre"),paquetes.getInt("tp.id")));
                    }
                    npk += 1;
                }
            }
            if(ver > 1) {
                mensaje.error("¡Tenia asignado mas de un paquete para digitación!", "Error");
            }
            jTablePaquete.setModel(modeloPaquete);
            jTablePaquete.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            jTablePaquete.setDefaultRenderer(String.class, renderizador);
            jTablePaquete.setRowSelectionInterval(ac,ac);
//            jTablePaquete.setRowSelectionAllowed(false);
            
            String noPaquete = jTablePaquete.getValueAt(jTablePaquete.getSelectedRow(), 0).toString();
            String tipoPaquete = jTablePaquete.getValueAt(jTablePaquete.getSelectedRow(), 1).toString();
            Paquete p = modeloPaquete.getPaquete(Integer.valueOf(noPaquete.trim()), tipoPaquete.trim());
            Variables.Tipo_Paquete = p.getTipo_paquete_id();
            if(Variables.Tipo_Paquete == Variables.Paquete_CS){ 
                Variables.Prefijo_Tipo_Paquete = Variables.Prefijo_Tipo_Paquete_CS;
                DicENTRY = rc.getDicCSProCYS();
                System.out.println(DicENTRY);
            }else if(Variables.Tipo_Paquete == Variables.Paquete_Hogar){ 
                Variables.Prefijo_Tipo_Paquete = Variables.Prefijo_Tipo_Paquete_Hogar;
                DicENTRY = rc.getDicCSPro();
            }
            paqueteactual = Integer.valueOf(noPaquete);
            SetHogares(con,c,noPaquete);
          
        }catch(Exception e){System.err.println("¡Error carga de paquetes!");
         SetHogares(con,c,"ERROR");
        }

    }

    private void SetHogares(DBClient con, Connection c, String b) {
        
        ResultSet hogares = con.getHogares(c, b);
        modeloHogar = new TablaModeloHogar();
        listahogares = new ArrayList<Hogar>();
        
        try{
           while(hogares.next()){
               if(!hogares.isLast()){//PARA QUE NO SALGA EL HOGAR FICTICIO
                   
               int A = 2;
               int B = 2;
                if(hogares.getBoolean(6) == true)
                    A = 1;
                if(hogares.getBoolean(7) == true)
                    B = 1;
                if(Variables.Tipo_Paquete == 2){
                    A = 0; B = 0;
                }
               Hogar h = new Hogar(hogares.getInt(1), hogares.getInt(2),
                       hogares.getInt(3), hogares.getInt(4), hogares.getInt(5),A,B, hogares.getInt(8));
               listahogares.add(h);
               modeloHogar.agregarHogar(h);
               }
           }
            jTableHogares.setModel(modeloHogar);
            jTableHogares.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            jTableHogares.setDefaultRenderer(String.class, renderizador2);
            jTableHogares.setRowSelectionInterval(0, 0);
            jTableHogares.getColumnModel().getColumn(6).setMinWidth(7);
            jTableHogares.getColumnModel().getColumn(6).setWidth(7);
            jTableHogares.getColumnModel().getColumn(6).setPreferredWidth(7);
            String noHogar = jTableHogares.getValueAt(jTableHogares.getSelectedRow(), 0).toString();
            SetCuestionarios(con,c, noHogar, b);
        }catch(Exception e){SetCuestionarios(con,c, "ERROR", "ERROR");} 

    }

    private void SetCuestionarios(DBClient con, Connection c, String b, String nopaquete) {
//        ListaCuestionarios();
        modeloCuestionario = new TablaModeloCuestionario();
        if(listacuestionarios == null) {
            try{
                /////Conexion
//                try {
//                    c.close();
//                    con = new DBClient();
//                    c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
//                            ,rc.getPuertoBD()); 
//                } catch (SQLException ex) {mensaje.send(Variables.Error_BD);}
                
                 int numero = 0;String a = "";
                 
                ResultSet cuestionarios = con.getCuestionarios(c, b,  nopaquete);
                while(cuestionarios.next()){
                    String numeroc = cuestionarios.getString(1);
                    if(!numeroc.startsWith("A")){ 
                        //Quitar la letra inicial del nombre de los cuestionarios H;M;E;G
                        a = cuestionarios.getString(1).substring(1, cuestionarios.getString(1).length());
                        numero = Integer.valueOf(a);
//                    }
                //AGREGADO EMEPAO PARA RESOLVER LA COMPARACION DE TIPO DE CUESTIONARIO
//                int tipocuestionario = 0;
//                if(cuestionarios.getInt(2) == 9)
//                    tipocuestionario = 1;
//                else if(cuestionarios.getInt(2) == 10)
//                    tipocuestionario = 2;
//                else if(cuestionarios.getInt(2) == 11)
//                    tipocuestionario = 3;
//                else if(cuestionarios.getInt(2) == 12)
//                    tipocuestionario = 4;
//                else if(cuestionarios.getInt(2) == 13)
//                    tipocuestionario = 5;
                
                modeloCuestionario.agregarCuestionario(new Cuestionario(numero, 
                    cuestionarios.getInt(2), cuestionarios.getInt(3), 
                    cuestionarios.getInt(4), cuestionarios.getInt(5),cuestionarios.getInt(6),cuestionarios.getInt(7)));
                }
                    }
                jTableCuestio.getColumnModel().getColumn(4).setMinWidth(7);
                jTableCuestio.getColumnModel().getColumn(4).setWidth(7);
                jTableCuestio.getColumnModel().getColumn(4).setPreferredWidth(7);
                jTableCuestio.setModel(modeloCuestionario);
                jTableCuestio.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                jTableCuestio.setDefaultRenderer(String.class, renderizador3);
//                jTableCuestio.setRowSelectionInterval(0, 0);
                
            }catch(Exception e){}
        }else{
            try{
            ArrayList<Cuestionario> lc = listacuestionarios;
            Iterator ilc = lc.iterator();
            while(ilc.hasNext()){
                Cuestionario cs = (Cuestionario) ilc.next();
                if(cs.gethogar() == Integer.valueOf(b.trim())){
                    modeloCuestionario.agregarCuestionario(cs);
                }
            }
                jTableCuestio.getColumnModel().getColumn(4).setMinWidth(3);
                jTableCuestio.getColumnModel().getColumn(4).setWidth(3);
                jTableCuestio.getColumnModel().getColumn(4).setPreferredWidth(3);
                jTableCuestio.setModel(modeloCuestionario);
                jTableCuestio.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                jTableCuestio.setDefaultRenderer(String.class, renderizador3);
//                jTableCuestio.setRowSelectionInterval(0, 0);
            }catch(Exception e){}
        }

    } 
    
    //SALIR
    private void salir() {
        if (mensaje.advertencia("¿Esta seguro que desea salir?", "Salir") != 1) {
       
            if(estadoactual.equals(Variables.EnD18) || estadoactual.endsWith(Variables.EnD211) || estadoactual.equals(Variables.EnV14)){
                 ftp ftp = getFTPServer();
                boolean ftpcon = false;
                try {
                    ftpcon = ftp.conexion(rc.getDireccionSA(), rc.getUsuarioSA(), rc.getPassSA(),22);
                } catch (JSchException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(ftpcon && RegistrarCambio(1, String.valueOf(paqueteactual), estadoactual, "")) {
                    try {
    //                   boolean ftpcon = ftp.conexion(rc.getDireccionSA(), rc.getUsuarioSA(), rc.getPassSA(),22);
    //                   boolean ftpcon = ftp.connect(rc.getDireccionSA(), rc.getUsuarioSA(), rc.getPassSA(),22);
                        if(estadoactual.equals(Variables.EnD18)){
                            try {
                                File origen = new File(pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + paqueteactual+"-"+idUsuario+"P");
                                File origenOtros = new File(pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + paqueteactual+"-"+idUsuario+"Potros");
                                if(ftpcon && origen.exists() && origenOtros.exists()) {
                                    if(ftp.uploadFile(origen) && ftp.uploadFile(origenOtros)){ 
                                        mensaje.send(Variables.Cambio_Correcto);
                                    }else{
                                        mensaje.send(Variables.No_Se_Copio_al_FTP);
                                    }
                                    ftp.disconnect();
                                }else{
    //                                mensaje.send(Error_FTP);
                                    mensaje.send(Variables.Archivo_No_Creado);
                                    mensaje.send(Variables.Cambio_Correcto);
                                }
                            } catch (IOException ex) {
        //                        Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                                mensaje.send(Variables.Error_FTP); 
                            }
                        }else if(estadoactual.equals(Variables.EnD211)){
                            try {
                                File origen = new File(pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + paqueteactual+"-"+idUsuario+"S");
                                File origenOtros = new File(pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + paqueteactual+"-"+idUsuario+"Sotros");
                                File origenSTS = new File(pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + paqueteactual+"-"+idUsuario+"S.sts");
                                File origenLOG = new File(pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + paqueteactual+"-"+idUsuario+"S.log");
                                if(ftpcon && origen.exists() && origenOtros.exists()) {
                                    if(ftp.uploadFile(origen) && ftp.uploadFile(origenOtros)){
                                        if(origenSTS.exists()){
                                            ftp.uploadFile(origenSTS);
                                        }
                                        if(origenLOG.exists()){
                                            ftp.uploadFile(origenLOG);
                                        }
                                        mensaje.send(Variables.Cambio_Correcto);
                                    }else{
                                        mensaje.send(Variables.No_Se_Copio_al_FTP);
                                    }
                                    ftp.disconnect();
                                }else{
    //                                mensaje.send(Error_FTP);
    //                                mensaje.send(Archivo_No_Creado);
                                    mensaje.send(Variables.Cambio_Correcto);
                                }
                            } catch (IOException ex) {
        //                        Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex); 
                                mensaje.send(Variables.Archivo_No_Creado); 
                            }   
                        }else if(estadoactual.equals(Variables.EnV14)){mensaje.send(Variables.Cambio_Correcto);}
                    } catch (Exception ex) {
                        Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                        mensaje.send(Variables.Error_FTP);
                    }
                   try { 
                        DBClient con = new DBClient();
                        Connection c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
                        ,rc.getPuertoBD());
                        con.setUserLogin(c, idUsuarioLog, grupo, 0);
                        c.close();
                    }catch (SQLException ex) {mensaje.send(Variables.Error_BD);}
                mensaje.bitacora("Se salio de la aplicación");
                System.exit(0); //se sale de la aplicacion
                }
                else {
                    mensaje.send(Variables.Error_Cambio);   
                }
            }else{
                try { 
                    DBClient con = new DBClient();
                    Connection c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
                    ,rc.getPuertoBD());
                    con.setUserLogin(c, idUsuarioLog, grupo, 0);
                    c.close();
                }catch (SQLException ex) {mensaje.send(Variables.Error_BD);}
//                mensaje.send(Cambio_Correcto);
                mensaje.bitacora("Se salio de la aplicación");
                System.exit(0); //se sale de la aplicacion
            }
//            mensaje.bitacora("Se salio de la aplicación");
//            System.exit(0); //se sale de la aplicacion
        }
    }

   public void enviar(){
       new Funciones(this).enviar();
   }
   public void comprobar(){
       new Funciones(this).comprobar();
   }

    private void abrepropiedades() {
        rc = new Propiedades(new javax.swing.JFrame(), true);
        rc.setLocationRelativeTo(null);
        rc.setVisible(true);
    }

    private void enviarobserva() {
        try{
        EnviarObservacion ro = new EnviarObservacion(new javax.swing.JFrame(), true,rc, String.valueOf(idUsuario),
                jTablePaquete.getValueAt(jTablePaquete.getSelectedRow(), 0).toString(),
                jTablePaquete.getValueAt(jTablePaquete.getSelectedRow(), 1).toString(),
                jTableHogares.getValueAt(jTableHogares.getSelectedRow(), 0).toString(),
                jTableCuestio.getValueAt(jTableCuestio.getSelectedRow(), 0).toString(),
                jTableCuestio.getValueAt(jTableCuestio.getSelectedRow(), 1).toString(), elements);

        ro.setLocationRelativeTo(null);
        ro.setVisible(true);
        }catch(Exception e){mensaje.error("Para enviar una observación de estar seleccionado\n"
                                    + "     un paquete, un hogar y un cuestionario. ", "Enviar Observación");
        }
    }

    /* CAMBIAR ESTADOS BASE DE DATOS
     * Registra los cambios de los estados de los paquetes dentro de la 
     * base de datos.
     * Registra En Digitacion 1/2 o En Verificacion al iniciar la app.
     * Registra Pausa Digitacion 1/2 o Pausa Verificacion al finalizar la app.
     */
    public boolean RegistrarCambio(int cambio, String noPaquete, String estado, String MD5Checksum){
        /////Conexion
        assert noPaquete != null;
        assert !noPaquete.equals("");
        assert estado != null;
        assert !estado.equals("");
        DBClient con = null;
        Connection c = null;
        try {
           con = new DBClient();
           c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
                    ,rc.getPuertoBD()); 
        } catch (SQLException ex) {
            mensaje.send(Variables.Error_BD);
        }
        
         /* ESTADOS
         * 9. En Digitación 1 10. Pausa Digitación 1 11. Digitado 1
         * 15. En Verificación 16. Pausa Verificación 17. Verificado
         */
        if(estado.equals(Variables.EnD18)){
            if(cambio == 2){estadoactual = Variables.D110;}
            return con.registro(c,noPaquete,idUsuario,Variables.EnDig1,(Variables.EnDig1+cambio), MD5Checksum);
            
        }
        else if(estado.equals(Variables.PD19)){
            estadoactual = Variables.EnD18;
            return con.registro(c,noPaquete,idUsuario,Variables.PDig1,Variables.EnDig1, MD5Checksum); 
        }
//        else if(estado.equals(D110)){   /* No se puede dar este caso */
//            con.registro(c,idUsuario,9,9);
//        }
        else if(estado.equals(Variables.EnD211)){
            if(cambio == 2){estadoactual = Variables.D213;}
            return con.registro(c,noPaquete,idUsuario,Variables.EnDig2,(Variables.EnDig2+cambio), MD5Checksum);
        }
        else if(estado.equals(Variables.PD212)){
            estadoactual = Variables.EnD211;
            return con.registro(c,noPaquete,idUsuario,Variables.PDig2,Variables.EnDig2, MD5Checksum);
        }
//        else if(estado.equals(D213)){
//            con.registro(c,idUsuario,12,12);  /*No se puede dar este caso */
//        }
        else if(estado.equals(Variables.EnV14)){
            if(cambio == 2){estadoactual = Variables.V16;}
            return con.registro(c,noPaquete,idUsuario,Variables.EnVer,(Variables.EnVer+cambio), MD5Checksum);
        }
        else if(estado.equals(Variables.PV15)){
            estadoactual = Variables.EnV14;
            return con.registro(c,noPaquete,idUsuario,Variables.PVer,Variables.EnVer, MD5Checksum);
        }
//        else if(estado.equals(V16)){
//            con.registro(c,idUsuario,15,15);  /*No se puede dar este caso */
//        }  
        try {
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private String NotNull(String string) {
        if(string != null) {return string;}
        else {return "";}
    }
   
    private void RealizarLectura(){
        try {
            jProgressBar1.setToolTipText("Cargando archivo xml...");
//            progressmonitor.PB demo = new PB(rc.getDirXML(), this,"UTF-8", jProgressBar1);
            if(Variables.Tipo_Paquete == Variables.Paquete_Hogar){
                xml = FileUtils.readFileToString(new File(rc.getDirXML()), "UTF-8");
            }else if (Variables.Tipo_Paquete == Variables.Paquete_CS){
                xml = FileUtils.readFileToString(new File(rc.getDirCyS()), "UTF-8");
            }
//            progressmonitor.PB demo = new PB(rc.getDirOtros(), this,"UTF-8", jProgressBar1);            
            setStrDic(FileUtils.readFileToString(new File(rc.getDirOtros()), "UTF-8"));
            
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void CargarArchivo() {
        new Thread() {
            public void run() {
                RealizarLectura();                       
            }
        }.start();
        jProgressBar1.setToolTipText("");
    }

    private void showTooltip(int seconds) {
        tooltipPopup.show();
        javax.swing.Timer t= new javax.swing.Timer(seconds*1000, new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            tooltipPopup.hide(); // disposes of the popup.
          }
        });
        t.setRepeats(false);
        t.start();
  }
  
    public void setStrDic(String entrada) {
        xmlotros = entrada;
        Double a = jProgressBar1.getLocationOnScreen().getX();
        Double b = jProgressBar1.getLocationOnScreen().getY();
        final PopupFactory popupFactory = PopupFactory.getSharedInstance();
        final JToolTip toolTip = new JToolTip();
        toolTip.setTipText("El diccionario ha sido cargado\n exitosamente.");
        tooltipPopup = popupFactory.getPopup(jProgressBar1, toolTip,
                        a.intValue() + jProgressBar1.getWidth() - 100,
                        b.intValue() - 25
                );
//        btnEnviar.setEnabled(true);
//        btnVerificar.setEnabled(false);
        btnCheck.setEnabled(true);
        servidorArchivosActivo();
        servidorBDActivo();

          //MONGODB crea todos los campos de la tabla
//        try{
//            mongoDB mongo = new mongoDB();
//            mongo.mongoDB(rc.getDireccionBDMongo(), rc.getNombreBDMongo(), 
//                            rc.getUsuarioBDMongo(), rc.getPassBDMongo());
//            mongo.camposCollection(xml);
//        }catch(Exception e){}
        
        showTooltip(5);
        
    }
    
    protected ftp getFTPServer(){
        ftp ftp;
        if(Login.esFTP){
            ftp = new FtpClient();
        }else{
            ftp = new SFTPClient(); 
        }
        return ftp;    
    }

    private void getCategorias() {
         try {
            DBClient con = new DBClient();
            Connection c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
                    ,rc.getPuertoBD());
                       
            ResultSet categorias = con.getcategorias(c);
//            elements = new Object[100];
//            int i = 1;
////            elements[0] = "(eliga una categoria)";   
//            while(categorias.next()){
//                        elements[i] = categorias.getString(2);
//                i += 1;           
//            }
            
            ArrayList cat = new ArrayList();
            cat.add("(eliga una categoria)");
            while(categorias.next()){
                        cat.add(categorias.getString(2));           
            }
            
            elements = cat.toArray(new Object[cat.size()]);
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void callActualizar() {
        btnActualizarActionPerformed(null);
    }
    
    void paquete_correcto(boolean estado){
        Variables.Paquete_Correcto = estado;
        if(Variables.Paquete_Correcto){
            btnEnviar.setEnabled(true);
        }else{
            btnEnviar.setEnabled(false);
        }
    }
}
