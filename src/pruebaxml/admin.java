/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pruebaxml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ProgressMonitorInputStream;

/**
 *
 * @author alejandro
 */
public class admin {

    File arc;
    FileInputStream fis = null;
    FileOutputStream fos = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;
    BufferedReader br=null;
    FileWriter fw=null;
    PrintWriter fsal=null;
    FileReader fr=null;
    InputStreamReader isr = null;
    Writer out = null;
    public admin(String narch){
        arc = new File(narch);
    }

    /**Este metodo permite abrir un archivo binario para que pueda ser leido */
    public boolean abre_bin_lectura(){
        try{
            fis = new FileInputStream(arc);
            ois = new ObjectInputStream(fis);
            return true;
        }catch(FileNotFoundException e){
            System.out.println("Archivo no existe." + arc.getName());
            return false;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
    /**Este metodo permite abrir un archivo de texto para que pueda ser leido*/
    public boolean abre_txt_lectura() throws UnsupportedEncodingException{
        try{
//            fr = new FileReader(arc);
//            br = new BufferedReader(fr);
            fis = new FileInputStream(arc);
            isr = new InputStreamReader(fis,"iso-8859-1");
            br = new BufferedReader(isr);          
            return true;
        }
            catch(FileNotFoundException fnfex) {
                System.out.println("Archivo no encontrado: " + fnfex);
                return false;
            }
    }
    /**Este metodo permite abrir un archivo de texto para que pueda ser leido*/
    public boolean abre_txt_lectura_utf8() throws UnsupportedEncodingException{
        try{
//            fr = new FileReader(arc);
//            br = new BufferedReader(fr);
            fis = new FileInputStream(arc);
            isr = new InputStreamReader(fis,"UTF-8");
            br = new BufferedReader(isr);          
            return true;
        }
            catch(FileNotFoundException fnfex) {
                System.out.println("Archivo no encontrado: " + fnfex);
                return false;
            }
    }
    
    /**Metodo que permite abrir un archivo binario para escribir sobre el mismo*/
    public boolean abre_bin_escritura(){
        try{
            fos = new FileOutputStream(arc);
            oos = new ObjectOutputStream(fos);
            return true;
        }catch(FileNotFoundException e){
            System.out.println("Archivo no existe." + arc.getName());
            return false;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
    /**Metodo que permite abrir un archivo de texto para escribir sobre el mismo*/
    public boolean abre_txt_escritura(){
        try{
//            fw = new FileWriter(arc);
//            fsal = new PrintWriter(fw);
            out = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(arc), "UTF-8"));
            
            return true;
	} catch (IOException e) {
	    System.out.println("EOF");
            return false;
	}
    }
    /**Cierra el archivo que este abierto ya sea de texo o binario*/
    public boolean cerrar_arc(){
        try{
            if(fis != null)
                fis.close();
            if(br!= null)
                br.close();
            if(fw != null)
                fw.close();
            if(fos != null)
                fos.close();
            if(fsal != null)
                fsal.close();
            if(ois != null)
                  ois.close();
            if(oos != null)
                 oos.close();
            if(fr!= null)
                fr.close();
            if(out!= null)
                out.close();
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }

    }
    /**Metodo que escribe sobre un archivo de texto*/
    public void estxt_string(String cadena){
        try {
            //            fsal.print(cadena);
                        out.write(cadena);
        } catch (IOException ex) {
            Logger.getLogger(admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**Metodo que escribe sobre una archivo binario*/
    public boolean esbin_string(String cadena){
        try{
           oos.writeObject(cadena);
           return true;
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("problema");
            return false;
        }
    }
    /**Metodo que lee un archivo de texto*/
    public String lee_txt(){
        String texto = "";
        try {
            texto=br.readLine();
        }
        catch(IOException ex) {}
        return texto;
    }
    /**Metodo que lee un archivo binario*/
    public String lee_bin(){
        String c="";
        try{
            c = (String)ois.readObject();
        }
        catch(ClassNotFoundException e){}
        catch(IOException ex) {}
        return c;
    }
    public void setPath(String path){
        arc = new File(path);
    }
    public long getLen(){
        return arc.length();
    }
}
