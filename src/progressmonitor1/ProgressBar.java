package progressmonitor1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import jdigitador3.Inicio;
import jdigitador3.Login;

/*
 * ProgressBar.java is a 1.4 application that requires these files:
 *   LongTask.java
 *   SwingWorker.java
 */
public class ProgressBar extends javax.swing.JDialog {
    public final static int ONE_SECOND = 1000;

    private JProgressBar progressBar;
    private Timer timer;
    private LongTask task;
    private JDialog a;
    Login lg;
    Inicio ini;
    public ProgressBar(String pathXML, Login aThis, String charset) {
        super(aThis,false);
        this.lg = aThis;
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Cargando archivo....");
        setPreferredSize(new java.awt.Dimension(184, 60));
        setResizable(false);
        task = new LongTask(pathXML, charset);
        a = this;
        progressBar = new JProgressBar(0, task.getLengthOfTask());
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
    
        JPanel panel = new JPanel();
        panel.add(progressBar);
        
        add(panel, BorderLayout.PAGE_START);
         //Display the window.
        pack();
        setVisible(true);
//        this.setAlwaysOnTop(true);
        //Create a timer.
        timer = new Timer(ONE_SECOND, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                progressBar.setValue(task.getCurrent());
                if (task.isDone()) {
                    progressBar.setValue(progressBar.getMaximum());
                    Toolkit.getDefaultToolkit().beep();
                    timer.stop();
                    setCursor(null); //turn off the wait cursor
                    
                    lg.setStrDic(task.getEntrada());
                    a.dispose();
                }
            }
        });
        task.go();
        timer.start();
    } 

}

