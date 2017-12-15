/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;
    import java.io.File;   
    import java.io.FileInputStream;  
    import java.io.FileOutputStream;  
    import java.io.IOException;  
    import java.util.Vector;  

      
    import com.jcraft.jsch.Channel;  
    import com.jcraft.jsch.ChannelSftp;  
    import com.jcraft.jsch.JSchException;  
    import com.jcraft.jsch.Session;  
    import com.jcraft.jsch.SftpException;  
    import com.jcraft.jsch.ChannelSftp.LsEntry;  
    import java.io.FileNotFoundException;
    import java.util.logging.Level;
    import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
    import org.apache.commons.vfs2.FileSystemException;
    import org.apache.commons.vfs2.FileSystemOptions;
    import org.apache.commons.vfs2.provider.sftp.SftpClientFactory;
    import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
      
    public class SFTPClient extends ftp{  
      
        private ChannelSftp command;  
      
        private Session session;  
      
        public SFTPClient() {  
            command = null;  
        }  
      
        @Override
        public boolean conexion(String servidor, String usuario, String pass, int port) throws JSchException {  
      
            //If the client is already connected, disconnect  
            if (command != null) {  
                disconnect();  
            }  
            FileSystemOptions fso = new FileSystemOptions();  
            try {  
                SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fso, "no");  
                session = SftpClientFactory.createConnection(servidor, port, usuario.toCharArray(), pass.toCharArray(), fso);  
                Channel channel = session.openChannel("sftp");  
                channel.connect();  
                command = (ChannelSftp) channel;  
      
            } catch (FileSystemException e) {  
//                e.printStackTrace();  
                return false;  
            }  
            return command.isConnected();  
        }  
      
        @Override
        public void disconnect() {  
            if (command != null) {  
                command.exit();  
            }  
            if (session != null) {  
                session.disconnect();  
            }  
            command = null;  
        }  
      
        public Vector<String> listFileInDir(String remoteDir) throws Exception {  
            try {  
                Vector<LsEntry> rs = command.ls(remoteDir);  
                Vector<String> result = new Vector<String>();  
                for (int i = 0; i < rs.size(); i++) {  
                    if (!isARemoteDirectory(rs.get(i).getFilename())) {  
                        result.add(rs.get(i).getFilename());  
                    }  
                }  
                return result;  
            } catch (Exception e) {  
                e.printStackTrace();  
                System.err.println(remoteDir);  
                throw new Exception(e);  
            }  
        }  
      
        public Vector<String> listSubDirInDir(String remoteDir) throws Exception {  
            Vector<LsEntry> rs = command.ls(remoteDir);  
            Vector<String> result = new Vector<String>();  
            for (int i = 0; i < rs.size(); i++) {  
                if (isARemoteDirectory(rs.get(i).getFilename())) {  
                    result.add(rs.get(i).getFilename());  
                }  
            }  
            return result;  
        }  
      
        protected boolean createDirectory(String dirName) {  
            try {  
                command.mkdir(dirName);  
            } catch (Exception e) {  
                return false;  
            }  
            return true;  
        }  
      
        protected boolean downloadFileAfterCheck(String remotePath, String localPath) throws IOException {  
            FileOutputStream outputSrr = null;  
            try {  
                File file = new File(localPath);  
                if (!file.exists()) {  
                    outputSrr = new FileOutputStream(localPath);  
                    command.get(remotePath, outputSrr);  
                }  
            } catch (SftpException e) {  
                try {  
                    System.err.println(remotePath + " not found in " + command.pwd());  
                } catch (SftpException e1) {  
                    e1.printStackTrace();  
                }  
                e.printStackTrace();  
                return false;  
            } finally {  
                if (outputSrr != null) {  
                    outputSrr.close();  
                }  
            }  
            return true;  
        }  

        public boolean changeDir(String remotePath) throws Exception {  
            try {  
                command.cd(remotePath);  
            } catch (SftpException e) {  
                return false;  
            }  
            return true;  
        }  
      
        public boolean isARemoteDirectory(String path) {  
            try {  
                return command.stat(path).isDir();  
            } catch (SftpException e) {  
                //e.printStackTrace();  
            }  
            return false;  
        }  
      
        public String getWorkingDirectory() {  
            try {  
                return command.pwd();  
            } catch (SftpException e) {  
                e.printStackTrace();  
            }  
            return null;  
        }  
       @Override
        public boolean mkdir(File file) throws SftpException{
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                //PRODUCCION
//                command.put(fis,
//                        "/srv/storage/PAQUETES/" + file.getParent().substring(file.getParent().lastIndexOf("\\")+1) + "//" +
//                    file.getName());
                //DESARROLLO
                System.out.println(file.getParent());
                System.out.println("/srv/desarrollo/PAQUETES/" + file.getParent().substring(file.getParent().lastIndexOf("PAQUETES")+9).replace("\\", "/") + "/");
                if(!isARemoteDirectory("/srv/desarrollo/PAQUETES/" + file.getParent().substring(file.getParent().lastIndexOf("PAQUETES")+9).replace("\\", "/") + "/"))
                command.mkdir("/srv/desarrollo/PAQUETES/" + file.getParent().substring(file.getParent().lastIndexOf("PAQUETES")+9).replace("\\", "/") + "/");

            } catch (FileNotFoundException ex) {
    //            Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } finally {
                try {
                    fis.close();
                } catch (IOException ex) {}
            }
            return true;
        }
        @Override
        public boolean uploadFile(File file) throws IOException, SftpException{
        FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                //PRODUCCION
//                command.put(fis,
//                        "/srv/storage/PAQUETES/" + file.getParent().substring(file.getParent().lastIndexOf("\\")+1) + "//" +
//                    file.getName());
                //DESARROLLO
                 System.out.println("/srv/desarrollo/PAQUETES/" + file.getParent().substring(file.getParent().lastIndexOf("\\")+1) + "/" +
                    file.getName());

                command.put(fis,
                        "/srv/desarrollo/PAQUETES/" + file.getParent().substring(file.getParent().lastIndexOf("\\")+1) + "/" +
                    file.getName());

            } catch (FileNotFoundException ex) {
    //            Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } finally {
                try {
                    fis.close();
                } catch (IOException ex) {}
            }
            return true;
        }
        
        @Override
        public boolean uploadFileE(File file) throws IOException, SftpException{
        FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                //PRODUCCION
//                command.put(fis,
//                        "/srv/storage/PAQUETES/" + file.getParent().substring(file.getParent().lastIndexOf("\\")+1) + "//" +
//                    file.getName());
                //DESARROLLO
               System.out.println("/srv/desarrollo/PAQUETES/" + 
                       file.getParent().substring(file.getParent().lastIndexOf("PAQUETES")+9).replace("\\", "/") + "/");
 
                command.put(fis,
                        "/srv/desarrollo/PAQUETES/" + 
                               file.getParent().substring(file.getParent().lastIndexOf("PAQUETES")+9).replace("\\", "/") + "/" +
                    file.getName());

            } catch (FileNotFoundException ex) {
    //            Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } finally {
                try {
                    fis.close();
                } catch (IOException ex) {}
            }
            return true;
        }
        
        @Override
        public boolean downloadFile(File file) throws SftpException{
            FileOutputStream fos = null;
            try {
                //
                // The remote filename to be downloaded.
                //
                fos = new FileOutputStream(file);
                //
                // Download file from FTP server
                //
                
//                PRODUCCION
//                command.get("/srv/storage/PAQUETES/" + file.getParent().substring(file.getParent().lastIndexOf("\\")+1) + 
//                        "//" + file.getName(), fos);
                //DESARROLLO
                command.get("/srv/desarrollo/PAQUETES/" + file.getParent().substring(file.getParent().lastIndexOf("\\")+1) + 
                        "//" + file.getName(), fos);
            } catch (Exception ex) {
//               Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return true;
        }
        
         public boolean downloadFileE(File file) throws SftpException{
            FileOutputStream fos = null;
            try {
                //
                // The remote filename to be downloaded.
                //
                if(!new File(file.getParent()).isDirectory()){
                    FileUtils.forceMkdir(new File(file.getParent()));
                }
                fos = new FileOutputStream(file);
                //
                // Download file from FTP server
                //
//                PRODUCCION
//                command.get("/srv/storage/PAQUETES/" + file.getParent().substring(file.getParent().lastIndexOf("\\")+1) + 
//                        "//" + file.getName(), fos);
                //DESARROLLO
                command.get("/srv/desarrollo/PAQUETES/" + 
                               file.getParent().substring(file.getParent().lastIndexOf("PAQUETES")+9).replace("\\", "/") + "/" +
                    file.getName(), fos);
            } catch (Exception ex) {
//                Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(FtpClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return true;
        }
    }  
