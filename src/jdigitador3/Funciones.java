 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jdigitador3;

import Conexion.DBClient;
import Conexion.ftp;
import com.mysql.jdbc.EscapeTokenizer;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdigitador3.Inicio.jProgressBar1;
import org.apache.commons.io.FileUtils;
import pruebaxml.Info;
import pruebaxml.InfoRes;
import tables.Cuestionario;
import tables.Hogar;

/**
 *
 * @author LUIS
 */
public class Funciones {
    private Info InfoDigitacion;
    private Inicio ini;
    private InfoRes InfoDigitacionRes;
    private Mensajes mensaje = new Mensajes();
    private int Dig;
    
    public Funciones(Inicio ini){
        this.ini = ini;
    }
      //publico por que se accede desde Verificar
    public void enviar(){
        setEstadoBotones(false);
        jProgressBar1.setValue(0);
        jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Iniciando...");
        String noPaquete = null;
        String estado = null;
        boolean resultado = false;

        try{
            Thread.sleep(200);
            noPaquete = ini.jTablePaquete.getValueAt(ini.jTablePaquete.getSelectedRow(), 0).toString();
            estado = ini.jTablePaquete.getValueAt(ini.jTablePaquete.getSelectedRow(), 2).toString();

            ftp ftp = ini.getFTPServer();
//            FtpClient ftp = new FtpClient();
//            SFTPClient ftp = new SFTPClient();
            
//            if(ftpcon) {
                File f = null;
                File origen = null;
                File fOtros = null;
                File origenOtros = null;
                File fSTS = null;
                File origenSTS = null;
                File fLOG = null;
                File origenLOG = null;
                String Tipo = "";
                String otros = "otros";
                if(estado.equals(Variables.EnD18)){
                    Tipo = "-"+ini.idUsuario+"P"; Dig = 1;
                    origen = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo);
                    f = new File(ini.pathArchivosLocales +"//" + Variables.Primera_Digitacion+ "//"+Variables.Prefijo_Tipo_Paquete+noPaquete+"//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo);
                    origenOtros = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+otros);
                    fOtros = new File(ini.pathArchivosLocales +"//" + Variables.Primera_Digitacion+ "//"+Variables.Prefijo_Tipo_Paquete+noPaquete+"//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+otros);
                }
                else if(estado.equals(Variables.EnD211)){
                    Tipo = "-"+ini.idUsuario+"S"; Dig = 2;
                    origen = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo);
                    f = new File(ini.pathArchivosLocales + "//" + Variables.Segunda_Digitacion + "//"+Variables.Prefijo_Tipo_Paquete+noPaquete+"//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo);
                    origenOtros = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+otros);
                    fOtros = new File(ini.pathArchivosLocales + "//" + Variables.Segunda_Digitacion + "//"+Variables.Prefijo_Tipo_Paquete+noPaquete+"//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+otros);
                    origenSTS = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+".sts");
                    fSTS = new File(ini.pathArchivosLocales + "//" + Variables.Segunda_Digitacion + "//"+Variables.Prefijo_Tipo_Paquete+noPaquete+"//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+".sts");
                    origenLOG = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+".log");
                    fLOG = new File(ini.pathArchivosLocales + "//" + Variables.Segunda_Digitacion + "//"+Variables.Prefijo_Tipo_Paquete+noPaquete+"//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+".log");
                }
                else if(estado.equals(Variables.EnV14)){
                    Tipo = "-"+ini.idUsuario+"V"; Dig = 3;
                    origen = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo);
                    f = new File(ini.pathArchivosLocales + "//" + Variables.Verificacion + "//"+Variables.Prefijo_Tipo_Paquete+noPaquete+"//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo);
                    origenOtros = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+otros);
                    fOtros = new File(ini.pathArchivosLocales + "//" + Variables.Verificacion + "//"+Variables.Prefijo_Tipo_Paquete+noPaquete+"//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+otros);
                } 
                else if(estado.equals(Variables.D110)){mensaje.send(Variables.Fin_D1);resultado = true;}
                else if(estado.equals(Variables.D213)){mensaje.send(Variables.Fin_D2);resultado = true;}
                else if(estado.equals(Variables.V16)){mensaje.send(Variables.Fin_Ver);resultado = true;}
                if(origen.exists()) {     
                    String Checksum = CheckSum.getMD5Checksum(origen);
//                    System.out.println(Checksum);//Muestra CheckSum
                    //Verificaciones (numero de paquetes)
                    jProgressBar1.setValue(25);
                    jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Verificando.." );
                    Thread.sleep(200);
                        if(Verificar(noPaquete, origen.getAbsolutePath())) {
                            jProgressBar1.setValue(80);
                            Thread.sleep(200);
                            FileUtils.copyFile(origen, f);
                            //otros
                            FileUtils.copyFile(origenOtros, fOtros);
                            if(origenSTS != null && origenSTS.exists())
                                FileUtils.copyFile(origenSTS, fSTS);
                            if(origenLOG != null && origenLOG.exists())
                                FileUtils.copyFile(origenLOG, fLOG);
                            boolean ftpcon = ftp.conexion(ini.rc.getDireccionSA(), ini.rc.getUsuarioSA(), ini.rc.getPassSA(),22);
//                            boolean ftpcon = ftp.connect(rc.getDireccionSA(), rc.getUsuarioSA(), rc.getPassSA(),22);
                            if(ftpcon){
                                try {//Enviar al servidor
                                    ftp.mkdir(f);
                                    if(ftp.uploadFileE(f) && ftp.uploadFileE(fOtros)){
                                        if(origenSTS != null && origenSTS.exists()){
                                            ftp.uploadFileE(fSTS);
                                        }
                                        if(origenLOG != null && origenLOG.exists()){
                                            ftp.uploadFileE(fLOG);
                                        }
                                        //Cambiar estado en BD            
                                        if(ini.RegistrarCambio(2,noPaquete,estado,Checksum)) {
//                                            Checksum = CheckSum.getMD5Checksum(f);System.out.println(Checksum);                                       
                                            //Ingresa info a MongoDB
                                            jProgressBar1.setValue(80);
                                            jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Ingresando Informacion del paquete..." );
                                            
                                            Thread.sleep(200);    
                                            //SE INGRESA LA INFORMACION EN MONGODB
//                                            new Thread(){
//                                                  public void run() {
//                                                      InfoDigitacion.digitacion_print(ini.rc, Dig, ini.jLabel4);
//                                                  }
//                                              }.start();
                                      
                                            mensaje.bitacora("Se finalizo el ingreso de datos: " + noPaquete + Tipo);
                                            jProgressBar1.setValue(80);
                                            jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Paquete Correcto..." );
                                            
                                            Thread.sleep(200);
                                            mensaje.send(Variables.Cambio_Correcto);   
                                            resultado = true;
                                            ini.callActualizar();
                                            
                                        }
                                        else {
                                            resultado = false;
                                        }
                                    }else{
                                        jProgressBar1.setValue(80);
                                        jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Error FTP..." );
                                        Thread.sleep(400);
                                        mensaje.send(Variables.No_Se_Copio_al_FTP);    
                                    }

                                } catch (Exception ex) {
                                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                                } 
                                ftp.disconnect();
                            }else{
                                
                                jProgressBar1.setValue(80);
                                jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Error FTP..." );
                                Thread.sleep(200);
                                mensaje.send(Variables.Error_FTP);
                            }
                        }else{
                            jProgressBar1.setValue(80);
                            jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Error en Verificacion..." );
                            Thread.sleep(200);
                            mensaje.send(Variables.No_Paquetes_Incorrecto);
                        }
                    }else{
                        jProgressBar1.setValue(80);
                        jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Archivos no creado..." );
                        Thread.sleep(200);
                        mensaje.send(Variables.Archivo_No_Creado);   
                    }
//            }
            if(!resultado) {
                mensaje.send(Variables.Error_Cambio);   
            }

        }catch(Exception e){}
        jProgressBar1.setValue(100);
        jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Finalizando..." );

        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        jProgressBar1.setValue(0);
        jProgressBar1.setString("" + jProgressBar1.getValue() + "%");
        setEstadoBotones(true);
   }
    
    public boolean Verificar(String paquete, String f){
       
        try{
//            int NoHogares = con.getNoHogares(c, Integer.valueOf(paquete));
            String arch = ini.generador.convertir(f, "8");
//            String xml1 = generador.convertir(pathXML, "");
            jProgressBar1.setValue(50);
            jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Obteniendo Informacion..." );
            Thread.sleep(500);
            InfoDigitacion = new Info(ini.xml, arch,ini.rc);

            boolean a = false;
             if(InfoDigitacion.getIn()){
//                ArrayList<Hogar> lt = InfoDigitacion.getListaHogares();
                //VERIFICACIONES
                jProgressBar1.setValue(60);
                jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Verificando información..." );
                Thread.sleep(500);
//                a = verificarPaquetes(lt);
                if(Variables.Paquete_Correcto){
                jProgressBar1.setValue(70);
//                jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Verificando cuestionarios..." );
                Thread.sleep(500);
//                if(ListarCuestionarios(InfoDigitacion.getListaCuestionario()) && a){
//                     a = true;
//                }else{
//                     a = false;
//                }
                    a = true;
                }
                
            }
             return a;
        }catch(Exception e){
             mensaje.error("¡Archivo xml no encontrado!.", "Error");
             System.out.println(e.getMessage());
             System.out.println(e.getMessage());
            return false;
        }
    }
    
     /*
     * Verifica que venga la cantidad de hogares definidos dentro de la BD
     * Imprime cuales hacen falta.
     * Verifica las variables del total de miembros y total de mujeres elegibles
     * Muestra el numero de hogar con el tipo de problema que tiene de los
     * anteriores.
     */
    public boolean verificarPaquetes(ArrayList<Hogar> lt){
        Iterator lth = lt.iterator();
        Iterator lti = ini.listahogares.iterator();
        ArrayList correcto = new ArrayList();
        ArrayList erroneo = new ArrayList();
        
        while(lti.hasNext()){
            Hogar h = (Hogar) lti.next();
            int nhogar = h.getno_hogar();
            Hogar h2 = null;
            Hogar aux = null;
            ini.error = "";
            while(lth.hasNext()){
                h2 = (Hogar)lth.next();
                if(nhogar == h2.getno_hogar()){
                    if(h.gettotal_miembros() == h2.gettotal_miembros()){
                        if(h.gettotal_mujeres() == h2.gettotal_mujeres()){
                            if(h.getTotal_hombres()== h2.getTotal_hombres()){
                                if(h.getno_ninos() == h2.getno_ninos()){
                                    if(h.getno_cuestionarios() == h2.getno_cuestionarios() ||
                                            h.getno_cuestionarios() == h2.getno_cuestionarios()+1 ||
                                            h.getno_cuestionarios() == h2.getno_cuestionarios()-1){
                                        if(h.getempoderamiento() == h2.getempoderamiento() && h.getasociado() == h2.getasociado()){
                                            correcto.add(nhogar);
                                            lt.remove(h2);
                                            nhogar = 0;
                                            break;
                                        }else{
                                            ini.error = ": Diferencia de Empoderamiento o Asociado.";
                                        }
                                    }else{
                                        ini.error = ": No. de cuestionarios diferente al ingresado en la BD.";
                                    }
                                }else{
                                    ini.error = ": No. niños diferente al ingresado en BD.";
                                }
                            }else{
                                ini.error = ": No. hombres elegibles diferente al ingresado en BD.";
                            }
                        }else{
                            ini.error = ": No. mujeres elegibles diferente al ingresado en BD.";
                        }
                    }else{
                        ini.error = ": No. miembros del hogar diferente al ingresado en BD.";
                    }
                    aux = h2;
                }
            }
            if(nhogar != 0) { 
                if(ini.error.equals("")){                 
                    erroneo.add("Hogar " + nhogar + ": No digitado.");                   
                }else{
                    erroneo.add("Hogar "+ nhogar + ini.error);
                }
                lt.remove(aux);
                ini.modeloHogar.setValueAt("\u200b", getRow(nhogar), 6);
            }
            lth = lt.iterator();
        }
        ini.error = "";
        if(!lt.isEmpty()){
            Iterator ilt = lt.iterator();
            Hogar rep = null;
//            System.out.println("Cuestionarios con digitacion repetida o incorecta: ");
            ini.error += "Hogares repetidos o que no pertenecen al paquete:\n";
            while(ilt.hasNext()){
                rep = (Hogar) ilt.next();
//                System.out.println(rep.getno_cuestinario());
                ini.error += " Hogar " + rep.getno_hogar() + "\n";
            }
            ini.error += "\n";
        }
        
        if(!erroneo.isEmpty()){
            Iterator ilist = erroneo.iterator();
            ini.error += "Información de errores en Hogares:\n ";
            while(ilist.hasNext()){
                ini.error += ilist.next().toString() + "\n ";
            }
            Iterator ilist2 = correcto.iterator();
            while(ilist2.hasNext()){
                ini.modeloHogar.setValueAt(" ", getRow(ilist2.next()), 6);
            }
        }else{
            Iterator ilist2 = correcto.iterator();
            while(ilist2.hasNext()){
                ini.modeloHogar.setValueAt(" ", getRow(ilist2.next()), 6);
            }
        }
        if(!ini.error.equals("")){
            mensaje.InfoPk(ini.error, "Verificación del paquete - Hogares");
            return false;
        }else{
            return true;
        }
    }
    
    private boolean ListarCuestionarios(ArrayList<Cuestionario> alist){
        ListaCuestionarios();
        Iterator lth = alist.iterator();
        Iterator lti = ini.listacuestionarios.iterator();
        ArrayList correcto = new ArrayList();
        ArrayList erroneo = new ArrayList();
        
        while(lti.hasNext()){
            Cuestionario h = (Cuestionario) lti.next();
            int ncuestionario = h.getno_cuestinario();
            Cuestionario h2 = null;
            Cuestionario aux = null;
            ini.errorC = "";
            while(lth.hasNext()){
                h2 = (Cuestionario)lth.next();
                if(h.gettipo_cuestionario() == h2.gettipo_cuestionario() && ncuestionario == h2.getno_cuestinario()){
                    if(h.gethogar() == h2.gethogar()){
                                if(h.getresultado() == h2.getresultado()){
                                    if((h.getlinea_pesona() == h2.getlinea_pesona())  || h.getresultado() == 96 || h.getresultado() == 2 || 
                                            h.getresultado() == 3 || h.getresultado() == 4 || h.getresultado() == 5 || h.getresultado() == 6 || 
                                             h.getresultado() == 7 || h.getresultado() == 8 || h.getresultado() == 9 || h.gettipo_cuestionario() == Variables.C_GASTOS){
                                        if(h.getCodSupervisor() == h2.getCodSupervisor() && h.getCodEditor() == h2.getCodEditor() && h.getCodEncuestador() == h2.getCodEncuestador()){
                                            correcto.add(ncuestionario);
                                            ncuestionario = 0;
                                            alist.remove(h2);
                                            h.setvalido(" ");
                                            break;
                                        }else{
                                            ini.errorC = ", Hogar " +h.gethogar() +": Codigos de personal diferentes a los ingresados en BD. ";
                                        }
                                    }else{
                                        ini.errorC = ", Hogar " +h.gethogar() +": No linea de Persona diferente al ingresado en BD. " + h2.getlinea_pesona();
                                    }
                                }else{

                                    ini.errorC = ", Hogar " +h.gethogar() +": Resultado diferente al ingresado en BD. " + h2.getresultado();
                                }
                      }
                      else{
                            ini.errorC = " pertenece al Hogar " +h.gethogar() +" y no al Hogar "+h2.gethogar()+" según lo ingresado en BD.";
                        }
                    aux = h2;
                }
            }
            if(ncuestionario != 0) { 
                if(ini.errorC.equals("")){
                    erroneo.add("Cuestionario " + ncuestionario + ", Hogar " +h.gethogar() +": No digitado.");                   
                }else{
                    erroneo.add("Cuestionario "+ ncuestionario + ini.errorC);
                }
                alist.remove(aux);
                h.setvalido("\u200b");
            }
            lth = alist.iterator();
        }
        //Cuestionarios repetidos; removido porque ahora si pueden venir cuestioanrios con el mismo numero
        //pero de diferente tipo.
        ini.errorC = ""; //AGREGADO PARA PRUEBA
        if(!alist.isEmpty()){
            Iterator ilt = alist.iterator();
            Cuestionario rep = null;
//            System.out.println("Cuestionarios con digitacion repetida o incorecta: ");
            ini.errorC += "Cuestionarios repetidos o que no pertenecen al paquete:\n";
            while(ilt.hasNext()){
                rep = (Cuestionario) ilt.next();
//                System.out.println(rep.getno_cuestinario());
                ini.errorC += " Cuestionario " + rep.getno_cuestinario() + ", Hogar " + rep.gethogar() +"\n";
            }
            ini.errorC += "\n";
        }
        if(!erroneo.isEmpty()){
           Iterator ilist = erroneo.iterator();
           ini.errorC += "Información de errores en Cuestionarios:\n ";
            while(ilist.hasNext()){
                ini.errorC += ilist.next().toString() + "\n ";
            }
            
//            System.out.println(ini.errorC);
            
        }
        if(!ini.errorC.equals("")){
            mensaje.InfoPk(ini.errorC, "Verificación del paquete - Cuestionarios");
            return false;
        }else{
            return true;
        }
        
    }
    
    private void ListaCuestionarios(){
        try {
            DBClient con = new DBClient();
            Connection c = con.getConnection(ini.rc.getDireccionBD(),ini.rc.getNombreBD(),ini.rc.getUsuarioBD(),ini.rc.getPassBD()
                    ,ini.rc.getPuertoBD());
            
            ResultSet cuestionarios = con.getALLCuestio(c, ini.paqueteactual);
            
            ini.listacuestionarios = new ArrayList<Cuestionario>();
            
             int numero = 0;String a = "";

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
//                else
//                    tipocuestionario = cuestionarios.getInt(2);

                    ini.listacuestionarios.add(new Cuestionario(numero, 
                    cuestionarios.getInt(2), cuestionarios.getInt(3), cuestionarios.getInt(4),"", 
                    cuestionarios.getInt(5),cuestionarios.getInt(6),cuestionarios.getInt(7),cuestionarios.getInt(8)));
                }
            }
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int getRow(Object next) {
        for(int a = 0; a < ini.modeloHogar.getRowCount(); a++){
            Object b = ini.modeloHogar.getValueAt(a, 0);
            if(next.equals(b)){
                return a;
            }
        }
        return 0;
    }
    
    public boolean VerificarRES(String paquete, String f){
       
        try{
            String arch = ini.generador.convertir(f, "8");
            jProgressBar1.setValue(50);
            jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Obteniendo Informacion..." );
            Thread.sleep(500);
            InfoDigitacionRes = new InfoRes(ini.xml, arch,ini.rc);

            boolean a = false;
             if(InfoDigitacionRes.getIn()){
                ArrayList<Hogar> lt = InfoDigitacionRes.getListaHogares();
                //VERIFICACIONES
                jProgressBar1.setValue(60);
                jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Verificando hogares..." );
                Thread.sleep(500);
                if(Variables.Tipo_Paquete == 1){
                    a = verificarPaquetes(lt);
                    jProgressBar1.setValue(70);
                    jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Verificando cuestionarios..." );
                    Thread.sleep(500);
                    if(ListarCuestionarios(InfoDigitacionRes.getListaCuestionario()) && a){
                         a = true;
                    }else{
                         a = false;
                    }
                }else{
                   a = verificarPaquetesCS(lt);
                    jProgressBar1.setValue(70);
                    jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Verificando cuestionarios..." );
                    Thread.sleep(500);
                    if(ListarCuestionariosCS(InfoDigitacionRes.getListaCuestionario()) && a){
                         a = true;
                    }else{
                         a = false;
                    } 
                }
                
            }
             return a;
        }catch(Exception e){
             mensaje.error("¡Archivo xml no encontrado!.", "Error");
             System.out.println(e.getMessage());
            return false;
        }
    }
    
    public void comprobar(){
        setEstadoBotones(false);
        jProgressBar1.setValue(0);
        jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Iniciando...");
        String noPaquete = null;
        String estado = null;
        boolean resultado = false;

        try{
            Thread.sleep(100);
            noPaquete = ini.jTablePaquete.getValueAt(ini.jTablePaquete.getSelectedRow(), 0).toString();
            estado = ini.jTablePaquete.getValueAt(ini.jTablePaquete.getSelectedRow(), 2).toString();


                File origen = null;
                File origenOtros = null;
                String Tipo = "";
                String otros = "otros";
                if(estado.equals(Variables.EnD18)){
                    Tipo = "-"+ini.idUsuario+"P"; Dig = 1;
                    origen = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo);
                    origenOtros = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+otros);
                }
                else if(estado.equals(Variables.EnD211)){
                    Tipo = "-"+ini.idUsuario+"S"; Dig = 2;
                    origen = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo);
                    origenOtros = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+otros);
                    File sts = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+".sts");
                    Variables.Ultimo_Verificado = getUltimoVerificado(sts);
                }
                else if(estado.equals(Variables.EnV14)){
                    Tipo = "-"+ini.idUsuario+"V"; Dig = 3;
                    origen = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo);
                    origenOtros = new File(ini.pathArchivosLocales +"//"+ Variables.Diccionario+ "//"+ Variables.Prefijo_Tipo_Paquete + noPaquete+ Tipo+otros);
                } 
                else if(estado.equals(Variables.D110)){mensaje.send(Variables.Fin_D1);resultado = true;}
                else if(estado.equals(Variables.D213)){mensaje.send(Variables.Fin_D2);resultado = true;}
                else if(estado.equals(Variables.V16)){mensaje.send(Variables.Fin_Ver);resultado = true;}
                if(!Variables.Ultimo_Verificado.equals(".")){
                if(origen.exists()) {
                    //Verificaciones (numero de paquetes)
                    jProgressBar1.setValue(25);
                    jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Verificando.." );
                    Thread.sleep(100);
                        if(VerificarRES(noPaquete, origen.getAbsolutePath())) {

                                            Thread.sleep(100);
                                            jProgressBar1.setValue(80);
                                            jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Paquete Correcto..." );
                                            mensaje.info("Paquete Correcto.", "Información");
                                            ini.paquete_correcto(true);
                                            Thread.sleep(100);             
                                
                        }else{
                            jProgressBar1.setValue(80);
                            jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Error en Verificacion..." );
                            Thread.sleep(200);
                            mensaje.send(Variables.No_Paquetes_Incorrecto);
                        }
                    }else{
                        jProgressBar1.setValue(80);
                        jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Archivos no creado..." );
                        Thread.sleep(200);
                        mensaje.send(Variables.Archivo_No_Creado);   
                    }
                }else{
                        jProgressBar1.setValue(80);
                        jProgressBar1.setString(jProgressBar1.getValue() + "% " + "No verificado todavia..." );
                        Thread.sleep(200);
                        mensaje.send(20);   
                    }
        }catch(Exception e){}
        jProgressBar1.setValue(100);
        jProgressBar1.setString(jProgressBar1.getValue() + "% " + "Finalizando..." );

        try {
            Thread.sleep(600);
        } catch (InterruptedException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        jProgressBar1.setValue(0);
        jProgressBar1.setString("" + jProgressBar1.getValue() + "%");
        setEstadoBotones(true);
    }
    
    void setEstadoBotones(boolean estado){          
        ini.btnActualizar.setEnabled(estado);
        ini.btnAyuda.setEnabled(estado);
        ini.btnCSPro.setEnabled(estado);
        ini.btnEnviar.setEnabled(Variables.Paquete_Correcto);
        ini.btnObser.setEnabled(estado);
        ini.btnSalir.setEnabled(estado);
//        ini.btnVerificar.setEnabled(estado);
        ini.btnCheck.setEnabled(estado);
        
        ini.jToolBar1.setEnabled(estado);
    }

    private String getUltimoVerificado(File sts) {
        String ultimo = "";
        try {
            String data = FileUtils.readFileToString(sts);
            if(data.contains("NodeKey=")){
//                ultimo = data.substring(data.lastIndexOf("NodeKey=")+"NodeKey=".length(),data.lastIndexOf("NodeKey=")+"NodeKey=".length()+5);
                ultimo = data.substring(data.lastIndexOf("NodeKey=")+"NodeKey=".length());
                    if(ultimo.contains("\n")){
                        ultimo = ultimo.substring(0, ultimo.indexOf("\n"));
                    }
                System.out.println(ultimo);
            }else{
                ultimo = ".";
            }
            return ultimo;
        } catch (IOException ex) {
            Logger.getLogger(Funciones.class.getName()).log(Level.SEVERE, null, ex);
            return ".";
        }

    }
    
    
    ///COMUNIDAD Y SERVICIOS
    public boolean verificarPaquetesCS(ArrayList<Hogar> lt){
        Iterator lth = lt.iterator();
        Iterator lti = ini.listahogares.iterator();
        ArrayList correcto = new ArrayList();
        ArrayList erroneo = new ArrayList();
        
        while(lti.hasNext()){
            Hogar h = (Hogar) lti.next();
            int nhogar = h.getno_hogar();
            Hogar h2 = null;
            Hogar aux = null;
            ini.error = "";
            while(lth.hasNext()){
                h2 = (Hogar)lth.next();
                    if(h.gettotal_miembros() == h2.gettotal_miembros()){
                                    if(h.getno_cuestionarios() == h2.getno_cuestionarios() ||
                                            h.getno_cuestionarios() == h2.getno_cuestionarios()+1 ||
                                            h.getno_cuestionarios() == h2.getno_cuestionarios()-1){
                                      
                                            correcto.add(nhogar);
                                            lt.remove(h2);
                                            nhogar = 0;
                                            break;

                                    }else{
                                        ini.error = ": No. de cuestionarios diferente al ingresado en la BD.";
                                    }
                    }else{
                        ini.error = ": No. miembros del hogar diferente al ingresado en BD.";
                    }
                    aux = h2;
                
            }
            if(nhogar != 0) { 
                if(ini.error.equals("")){                 
                    erroneo.add("Paquete " + nhogar + ": No digitado.");                   
                }else{
                    erroneo.add("Paquete "+ nhogar + ini.error);
                }
                lt.remove(aux);
                ini.modeloHogar.setValueAt("\u200b", getRow(nhogar), 6);
            }
            lth = lt.iterator();
        }
        ini.error = "";
        if(!lt.isEmpty()){
            Iterator ilt = lt.iterator();
            Hogar rep = null;
//            System.out.println("Cuestionarios con digitacion repetida o incorecta: ");
            ini.error += "Error repetidos o que no pertenecen al paquete:\n";
            while(ilt.hasNext()){
                rep = (Hogar) ilt.next();
//                System.out.println(rep.getno_cuestinario());
                ini.error += " Hogar " + rep.getno_hogar() + "\n";
            }
            ini.error += "\n";
        }
        
        if(!erroneo.isEmpty()){
            Iterator ilist = erroneo.iterator();
            ini.error += "Información de errores de Paquete:\n ";
            while(ilist.hasNext()){
                ini.error += ilist.next().toString() + "\n ";
            }
            Iterator ilist2 = correcto.iterator();
            while(ilist2.hasNext()){
                ini.modeloHogar.setValueAt(" ", getRow(ilist2.next()), 6);
            }
        }else{
            Iterator ilist2 = correcto.iterator();
            while(ilist2.hasNext()){
                ini.modeloHogar.setValueAt(" ", getRow(ilist2.next()), 6);
            }
        }
        if(!ini.error.equals("")){
            mensaje.InfoPk(ini.error, "Verificación del paquete - PAQUETE");
            return false;
        }else{
            return true;
        }
    }
    
    private boolean ListarCuestionariosCS(ArrayList<Cuestionario> alist){
        ListaCuestionarios();
        Iterator lth = alist.iterator();
        Iterator lti = ini.listacuestionarios.iterator();
        ArrayList correcto = new ArrayList();
        ArrayList erroneo = new ArrayList();
        
        while(lti.hasNext()){
            Cuestionario h = (Cuestionario) lti.next();
            int ncuestionario = h.getno_cuestinario();
            Cuestionario h2 = null;
            Cuestionario aux = null;
            ini.errorC = "";
            while(lth.hasNext()){
                h2 = (Cuestionario)lth.next();
                if(h.gettipo_cuestionario() == h2.gettipo_cuestionario() && ncuestionario == h2.getno_cuestinario()){
//                    if(h.gethogar() == h2.gethogar()){
                                if(h.getresultado() == h2.getresultado()){
                                    if((h.getlinea_pesona() == h2.getlinea_pesona())  || h.getresultado() == 96 || h.getresultado() == 2 || 
                                            h.getresultado() == 3 || h.getresultado() == 4 || h.getresultado() == 5 || h.getresultado() == 6 || 
                                             h.getresultado() == 7 || h.getresultado() == 8 || h.getresultado() == 9 || h.gettipo_cuestionario() == Variables.C_GASTOS){
                                        if(h.getCodSupervisor() == h2.getCodSupervisor() && h.getCodEditor() == h2.getCodEditor() && h.getCodEncuestador() == h2.getCodEncuestador()){
                                            correcto.add(ncuestionario);
                                            ncuestionario = 0;
                                            alist.remove(h2);
                                            h.setvalido(" ");
                                            break;
                                        }else{
                                            ini.errorC = ", Tipo " +h.gettipo_cuestionario() +": Codigos de personal diferentes a los ingresados en BD. ";
                                        }
                                    }else{
                                        ini.errorC = ", Tipo " +h.gettipo_cuestionario() +": No linea de Persona diferente al ingresado en BD. " + h2.getlinea_pesona();
                                    }
                                }else{

                                    ini.errorC = ", Tipo " +h.gettipo_cuestionario() +": Resultado diferente al ingresado en BD. " + h2.getresultado();
                                }
//                      }
//                      else{
//                            ini.errorC = " pertenece al Hogar " +h.gethogar() +" y no al Hogar "+h2.gethogar()+" según lo ingresado en BD.";
//                        }
                    aux = h2;
                }
            }
            if(ncuestionario != 0) { 
                if(ini.errorC.equals("")){
                    erroneo.add("Cuestionario " + ncuestionario + ": No digitado.");                   
                }else{
                    erroneo.add("Cuestionario "+ ncuestionario + ini.errorC);
                }
                alist.remove(aux);
                h.setvalido("\u200b");
            }
            lth = alist.iterator();
        }
        //Cuestionarios repetidos; removido porque ahora si pueden venir cuestioanrios con el mismo numero
        //pero de diferente tipo.
        ini.errorC = ""; //AGREGADO PARA PRUEBA
        if(!alist.isEmpty()){
            Iterator ilt = alist.iterator();
            Cuestionario rep = null;
//            System.out.println("Cuestionarios con digitacion repetida o incorecta: ");
            ini.errorC += "Cuestionarios repetidos o que no pertenecen al paquete:\n";
            while(ilt.hasNext()){
                rep = (Cuestionario) ilt.next();
//                System.out.println(rep.getno_cuestinario());
                ini.errorC += " Cuestionario " + rep.getno_cuestinario() +"\n";
            }
            ini.errorC += "\n";
        }
        if(!erroneo.isEmpty()){
           Iterator ilist = erroneo.iterator();
           ini.errorC += "Información de errores en Cuestionarios:\n ";
            while(ilist.hasNext()){
                ini.errorC += ilist.next().toString() + "\n ";
            }
            
//            System.out.println(ini.errorC);
            
        }
        if(!ini.errorC.equals("")){
            mensaje.InfoPk(ini.errorC, "Verificación del paquete - Cuestionarios");
            return false;
        }else{
            return true;
        }
        
    }
    
}
