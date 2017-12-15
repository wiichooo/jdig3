/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package progressmonitor1;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import jdigitador3.Inicio;
import jdigitador3.Login;

/**
 *
 * @author Luis
 */
public class PB {
     public final static int ONE_SECOND = 1000;

//    private JProgressBar progressBar;
    private Timer timer;
    private LongTask task;
    private JDialog a;
    Login lg;
    Inicio ini;
        public PB(String pathXML, Inicio aThis, String charset, final JProgressBar progressBar) {
//        super(aThis,false);
        this.ini = aThis;
//        progressBar1;
//        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
//        setTitle("Cargando archivo....");
//        setPreferredSize(new java.awt.Dimension(184, 60));
//        setResizable(false);
        task = new LongTask(pathXML, charset);
//        a = this;
        progressBar.setMaximum(task.getLengthOfTask());
        progressBar.setValue(0);
        progressBar.setString("Cargando archivo xml...");
        progressBar.setStringPainted(true);
//    
//        JPanel panel = new JPanel();
//        panel.add(progressBar);
//        
//        add(panel, BorderLayout.PAGE_START);
//         //Display the window.
//        pack();
//        setVisible(true);
//        this.setAlwaysOnTop(true);
        //Create a timer.
        timer = new Timer(ONE_SECOND, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                progressBar.setValue(task.getCurrent());
                int aa = (task.getCurrent()*100)/task.getLengthOfTask();
                progressBar.setString(aa + "% Cargando archivo xml...");
                if (task.isDone()) {
                    progressBar.setValue(progressBar.getMaximum());
                    Toolkit.getDefaultToolkit().beep();
                    timer.stop();
//                    setCursor(null); //turn off the wait cursor
                    
                    ini.setStrDic(task.getEntrada());
                    progressBar.setMaximum(100);
                    progressBar.setValue(0);
                    progressBar.setString("0%");
//                    a.dispose();
                }
            }
        });
        task.go();
        timer.start();
    } 
}
