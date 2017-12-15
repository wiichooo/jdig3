/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Luis
 */
public class FtpClient extends ftp {
    FTPClient client;
    public FtpClient(){
            client = new FTPClient();
    }
    
    public boolean conexion(String servidor, String usuario, String pass, int port){
        boolean login = false;
        try {
                client.connect(servidor);
                login = client.login(usuario,pass);
            } catch (IOException e) { }
        
        return login;
    }    
    public void disconnect(){
        try {
            client.logout();
            client.disconnect();
        } catch (IOException ex) {
            Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean downloadFile(File file){
        FileOutputStream fos = null;
        try {
            //
            // The remote filename to be downloaded.
            //
            fos = new FileOutputStream(file);
            //
            // Download file from FTP server
            //
            return client.retrieveFile("PAQUETES//" + file.getParent().substring(file.getParent().lastIndexOf("\\")+1) + 
                    "//" + file.getName(), fos);
        } catch (IOException ex) {
//            Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
          public boolean mkdir(File file) throws SftpException{
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                //PRODUCCION
//                command.put(fis,
//                        "/srv/storage/PAQUETES/" + file.getParent().substring(file.getParent().lastIndexOf("\\")+1) + "//" +
//                    file.getName());
                //DESARROLLO
                client.mkd("/PAQUETES//" + file.getParent().substring(file.getParent().lastIndexOf("\\")+1) + "//");

            } catch (FileNotFoundException ex) {
    //            Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (IOException ex) {
            Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                try {
                    fis.close();
                } catch (IOException ex) {}
            }
            return true;
        }
            
    public boolean uploadFile(File file) throws IOException{
        FileInputStream fis = null;
        try {
            //
            // Create an InputStream of the file to be uploaded
            //
            fis = new FileInputStream(file);
            //
            // Store file to server
            System.out.println("PAQUETES//" + file.getParent().substring(file.getParent().lastIndexOf("\\")+1) + "//" +
                    file.getName());
             client.storeFile("PAQUETES//" +
                    file.getName(), fis);
            return client.storeFile("PAQUETES//" + file.getParent().substring(file.getParent().lastIndexOf("\\")+1) + "//" +
                    file.getName(), fis);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
//                Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }
    }
    public boolean uploadFileE(File file) throws IOException{
        return false;
    }
}
