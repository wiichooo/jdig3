/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alejandro
 */
public class PruebaXML {

    /**
     * @param args the command line arguments
     */

    public PruebaXML() {
//        principal p = new principal();
//        p.compilar();
//        boolean a = true;
////        String In = "src\\PruebaXML\\dic\\Mujer07.dcf";
////        String MD = "src\\PruebaXML\\prueba2.txt";
////        String Out = "src\\PruebaXML\\pruebaXML.xml";
//        try{
//            if(a){
//            String z = convertir(In,MD);
//            InputStream inputStream = new  ByteArrayInputStream(z.getBytes("UTF-8"));
//            Yylex lexer = new Yylex(inputStream);
//            
//            parser miparser = new parser(lexer);
//            miparser.parse();
//            String salida = mod(2, miparser.action_obj.finalXML);
//            //System.out.println(salida);
//            writeFile(Out,salida);
//            }
////          new LeerXml(Out);
//         
////	 
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//        }
      }
    
    public String generarXML(String z){
        String salida = "";
        try{
            z = mod(1,z);    
            InputStream inputStream = new  ByteArrayInputStream(z.getBytes("UTF-8"));
            Yylex lexer = new Yylex(inputStream);
            
            parser miparser = new parser(lexer);
            miparser.parse();
            salida = mod(2, miparser.action_obj.finalXML);
 
        }catch(Exception ex){
//            ex.printStackTrace();
            salida = "Archivo Incorrecto!";
        }
        return salida;
    } 
    public String convertir(String path0,String path1){
        try {
            String archivo = "", s;
            admin arc = new admin(path0);
            
            if(path1.contains("8"))
             arc.abre_txt_lectura_utf8();
            else
                arc.abre_txt_lectura();
            
            while((s = arc.lee_txt())!=null){
                archivo += s+"\n";
//                  archivo += mod(1,s)+"\n";
            }
            arc.cerrar_arc();
//            archivo = mod(1,archivo);
//            arc.setPath(path1);
//            arc.abre_txt_escritura();
//            arc.estxt_string(archivo);
//            arc.cerrar_arc();
            return archivo;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PruebaXML.class.getName()).log(Level.SEVERE, null, ex);
        }
         
            return null;
        
    }
    
        public String ver(String path0,String path1){
        try {
            String is = "", s;
            admin arc = new admin(path0);
            arc.abre_txt_lectura();
            s = arc.lee_txt();
//            System.out.println(s);
            if(s.contains("ï»¿")) {
                is =  "utf-8";
            }
            else {
                is = "iso";
            }
            
            arc.cerrar_arc();
            return is;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PruebaXML.class.getName()).log(Level.SEVERE, null, ex);
        }
         
            return null;
        
    }
        
    public void writeFile(String URL,String contenido) {
        admin arc = new admin(URL);
        contenido = mod(2,contenido);
        arc.abre_txt_escritura();
        arc.estxt_string(contenido);
        arc.cerrar_arc();
    }

    private String mod(int i, String resultado) {
        if(i == 1){
            
            resultado = resultado.replaceAll(";", "#sep#");
            resultado = resultado.replaceAll("&", "&amp;");
            resultado = resultado.replaceAll("<", "&lt;");
            resultado = resultado.replaceAll(">", "&gt;");
            resultado = resultado.replaceAll("\"", "&quot;");
            resultado = resultado.replaceAll("'", "&apos;");
//            resultado = resultado.replaceAll("\"", "");
            resultado = resultado.replaceAll("(Note=)((.*)\n*)", "");

            
/*    
            resultado = resultado.replaceAll("á", "<aacute>");
            resultado = resultado.replaceAll("é", "<eacute>");
            resultado = resultado.replaceAll("í", "<iacute>");
            resultado = resultado.replaceAll("ó", "<oacute>");
            resultado = resultado.replaceAll("ú", "<uacute>");
            resultado = resultado.replaceAll("ñ", "<ntilde>");
*/
            
//            resultado = resultado.replaceAll("ü", "u");
//            resultado = resultado.replaceAll("ö", "o");
//            resultado = resultado.replaceAll("ï", "i");
//            resultado = resultado.replaceAll("ä", "a");
//            resultado = resultado.replaceAll("ë", "e");     
            
        }else if(i == 2){
/*            
            resultado = resultado.replaceAll("<aacute>", "á");
            resultado = resultado.replaceAll("<eacute>", "é");
            resultado = resultado.replaceAll("<iacute>", "í");
            resultado = resultado.replaceAll("<oacute>", "ó");
            resultado = resultado.replaceAll("<uacute>", "ú");
            resultado = resultado.replaceAll("<ntilde>", "ñ");
*/            
            resultado = resultado.replaceAll("#sep#",";");
//            resultado = resultado.replaceAll("&quot;","\"");
//            resultado = resultado.replaceAll("&apos;","'");
           // resultado = resultado.replace("Note=((.*)\n*)", "");
        }
        return resultado;
    }
    
    public boolean lectorXML(String Out){
        try {
            new LectorXML().leer(Out);
            return true;
        } catch (Exception ex) {
            return false;
        } 
    }
    
   
}
/*Enviar al lexer el flujo directo del archivo; Se descarto debido a que si 
 * vienen caractes <,>,& da error al momento de convertir a XML.
            InputStream inputStream = new FileInputStream(In);
            InputStreamReader b = new InputStreamReader(inputStream,"iso-8859-1");
            Yylex lexer = new Yylex(b);
 */

