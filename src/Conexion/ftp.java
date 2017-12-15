/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Luis
 */
public class ftp {
    public boolean conexion(String servidor, String usuario, String pass, int port) throws JSchException {
        return false;
    }    
    public void disconnect(){
    }
    
    public boolean downloadFile(File file) throws IOException, SftpException{
        return false;
    }
    
    public boolean downloadFileE(File file) throws IOException, SftpException{
        return false;
    }
        
    public boolean uploadFile(File file) throws IOException, SftpException{
        return false;
    }
    public boolean uploadFileE(File file) throws IOException, SftpException{
        return false;
    }
    public boolean mkdir(File file) throws SftpException{
        return false;
    }
}
