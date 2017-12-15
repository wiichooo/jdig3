/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package progressmonitor1;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pruebaxml.admin;
/**
 *
 * @author Luis
 */
public class LongTask {
    private int lengthOfTask;
    private int current = 0;
    private boolean done = false;
    private boolean canceled = false;
    private String statMessage;
    String archivo = "";
    
    admin arc = null;
    public LongTask(String path, String charset) {
        super();
        try {
            //Compute length of task...
            //In a real program, this would figure out
            //the number of bytes to read or whatever.
            arc = new admin(path);
            if(charset.equals("iso")) {
                arc.abre_txt_lectura();
            }
            else {
                arc.abre_txt_lectura_utf8();
            }
            lengthOfTask = (int) arc.getLen();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LongTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Called from ProgressBarDemo to start the task.
     */
    public void go() {
        final SwingWorker worker = new SwingWorker() {
            public Object construct() {
                current = 0;
                done = false;
                canceled = false;
                statMessage = null;
                return new LongTask.ActualTask();
            }
        };
        worker.start();
    }

    /**
     * Called from ProgressBarDemo to find out how much work needs
     * to be done.
     */
    public int getLengthOfTask() {
        return lengthOfTask;
    }

    /**
     * Called from ProgressBarDemo to find out how much has been done.
     */
    public int getCurrent() {
        return current;
    }

    public void stop() {
        canceled = true;
        statMessage = null;
    }

    /**
     * Called from ProgressBarDemo to find out if the task has completed.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Returns the most recent status message, or null
     * if there is no current status message.
     */
    public String getMessage() {
        return statMessage;
    }
    public String getEntrada(){
        return archivo;
    }
    /**
     * The actual long running task.  This runs in a SwingWorker thread.
     */
    class ActualTask {
        ActualTask() {
            try {
                //Fake a long task,
                //making a random amount of progress every second.
                
                String s = "";       
                while (!canceled && !done) {
                    try {
    //                    try {
                            Thread.sleep(1);
                             //make some progress
                            //MOD 07.02
//                            if (current >= lengthOfTask) {
//                                done = true;
//                                current = lengthOfTask;
//                            }
                            
                            
    //                        statMessage = "Completed " + current +
    //                                      " out of " + lengthOfTask + ".";
    //                    } catch (InterruptedException e) {
    //                        System.out.println("ActualTask interrupted");
    //                    }
                        
                        if((s = arc.lee_txt())!=null){
                                archivo += s+"\n";
                                current += s.length()+1;
                //                  archivo += mod(1,s)+"\n";
                        }else{
                            done = true;
                        }
                        
                         
                    }catch (InterruptedException ex) {
                        Logger.getLogger(LongTask.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
//                System.out.println("salida: "+archivo);
            }catch(Exception e){}
        }
    }
}
