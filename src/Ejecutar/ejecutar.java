/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejecutar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import jdigitador3.Variables;

/**
 *
 * @author Luis
 */
public class ejecutar {
//DicCSPro varible para la carpeta compartida donde se encuentran los archivos de Entry
    public void csproDIGITA(String noPaquete, String ArchivosLocales, int i1, String pathCSPro, String a, String DicCSPro) {
        try {
//            if(noPaquete.length()==1) {
//                noPaquete = "00"+noPaquete;
//            }
//            else if(noPaquete.length()==2) {
//                noPaquete = "0"+noPaquete;
//            }
//            String D = "";
//            if(a.contains("P")){ D = "-1";}else if(a.contains("S")){ D = "-2";}else if(a.contains("V")){ D = "-V";}
                    
                if(!new File(ArchivosLocales + "\\DICCIONARIO\\"+ Variables.Prefijo_Tipo_Paquete+noPaquete+"-"+a+".pff").exists())
                {
                    Metodo_Nuevo_Paquete(noPaquete,ArchivosLocales,a, DicCSPro);
                }
                Runtime rt = Runtime.getRuntime();
                String proceso = "\"" + pathCSPro + "\\runpff.exe\" " + ArchivosLocales + "\\DICCIONARIO\\"+ Variables.Prefijo_Tipo_Paquete+noPaquete+"-"+a+".pff";
                Process pr = rt.exec(proceso);
                
            } catch(Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            }

    }

    static void csproVERIFICA(int i, int i0, int i1) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    protected boolean Metodo_Nuevo_Paquete(String NoPaquete, String ArchivosLocales, String D, String DicCSPro){
        FileWriter fw;
        BufferedWriter bw;
        PrintWriter ArchErr;
        String entry = "";
        if(Variables.Prefijo_Tipo_Paquete.equals(Variables.Prefijo_Tipo_Paquete_CS)){
            entry = Variables.EntryCyS;
        }else{
            entry = Variables.EntryHogar;
        }
        String pr = "";
        if(D.endsWith("P")){
            pr = "\nLock=Verify";
        }else if(D.endsWith("S")){
            pr = //"\nStartMode=verify" +
                        "\nLock=Add,Modify";
        }
        String Texto="";
//        Metodo_Limpiar_Archivo(1);
        Texto="[Run Information] "
                + "\nVersion=CSPro 5.0 "
                + "\nAppType=Entry "
                + "\n\n[DataEntryInit] "
                + "\nInteractive=ErrMsg "
                + pr
                + "\n\n[Files] "
                + "\nApplication="+DicCSPro+"\\"+ entry;
        
        Texto+="\nInputData=.\\" + Variables.Prefijo_Tipo_Paquete + NoPaquete+"-"+D;
        Texto+="\n\n[ExternalFiles] \n" +"OTROS=.\\" + Variables.Prefijo_Tipo_Paquete + NoPaquete+"-"+ D + "otros ";
        Texto+="\n\n[Parameters] \n";
        
        try{
            fw = new FileWriter (ArchivosLocales+"//DICCIONARIO//"+ Variables.Prefijo_Tipo_Paquete + NoPaquete +"-"+ D + ".pff");
            bw = new BufferedWriter (fw);
            ArchErr = new PrintWriter (bw);			
            ArchErr.println (Texto);
            ArchErr.close();
            return true;
            
        }
        catch(Exception e){System.out.println("Mensaje: No se pudo abrir el archivo pff "+ e);}
        return false;
    }
  
}
