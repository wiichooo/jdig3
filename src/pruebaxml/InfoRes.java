/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import Conexion.DBClient;
import jdigitador3.Propiedades;
import Conexion.mongoDB;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdigitador3.Variables;
import org.w3c.dom.Node;
import tables.Cuestionario;
import tables.Hogar;

/**
 *
 * @author Luis
 */
public class InfoRes {
    String xml;
    String salida;
    LeerXml lector;
    ArrayList<Node> records;
    Node aux;
    int NoHogares = 0;
    int NohogarA = 1;
    int noCuestionarios = 0;
    int asociado = 0;
    int empoderamiento = 0;
    //VERIFICAR HOGAR
    int Nohogar = 1;
    int Nomiembros = 0;
    int Nomujeres = 0;
    int Nohombres = 0;
    int Noninos = 0;
    ArrayList<Hogar> listahogares = new ArrayList<Hogar>();
    
    //VERIFICAR CUESTIONARIOS
    int no_hogar = 0;
    int resultado_hogar = 0;
    int persona_hogar = 0;
    
    int no_mujer = 0;
    int resultado_mujer = 0;
    int persona_mujer = 0;
    
    int no_emp = 0;
    int resultado_emp = 0;
    int persona_emp = 0;
    
    int no_gys = 0;
    int resultado_gys = 0;
    int persona_gys = 0;
    
    int no_hombre = 0;
    int resultado_hombre = 0;
    int persona_hombre = 0;
    
    int no_comunidad = 0;
    int resultado_comunidad = 0;
    int cor_comunidad = 0;
    
    int no_servicios = 0;
    int resultado_servicios = 0;
    int cor_servicios = 0;
    
    int supervisor = 0;
    int editor = 0;
    int encuestador = 0;
    
    Propiedades rc = null;
    ArrayList<Cuestionario> listacuestionarios = new ArrayList<Cuestionario>();
    
    ArrayList<String[]> Digitacion = new ArrayList<String[]>();
    /*Tipo de cuestionario que se esta trabajando
     * 1. Hogar
     * 2. Mujer
     * 3. Empoderamiento
     * 4. Gastos y Servicios
     */
    int Tipo_Cuestionario_Actual = 0;
    private int val = 0;
   
    String paquete = "";
    String maxRec = "";

//    private DBClient con;
//    private Connection c;
    public InfoRes(String xml, String salida,Propiedades rc){
        if(salida.startsWith(String.valueOf('\uFEFF'))){
            salida = salida.replace(String.valueOf('\uFEFF'), "");
        }
        this.salida = salida;
        this.xml = xml;
        lector = new LeerXml(xml);
//        records = lector.getNodosHijos(lector.getRaiz());
        records = lector.getRaizDic();
//        Iterator ilist = records.iterator();
//        while(ilist.hasNext()){
//            System.out.println(lector.getAtt((Node)ilist.next(),"Label"));
//        }
//        getIn();
        this.rc = rc;
    }
    public boolean getIn(){
       String[] listalineas = null;
       if(Merge(listalineas)){
           //agregando para llevar el control de lo que se tiene digitado
           //de cada paquete
//////           try {
//////                DBClient con = new DBClient();
//////                Connection c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
//////                        ,rc.getPuertoBD()); 
//////
//////                con.insertProg(c, Integer.valueOf(paquete.trim()), Integer.valueOf(NoHogares));
//////                c.close();
//////            } catch (SQLException ex) {
////////                msg.error("¡No existe conexión a la base de datos!", "Error");
//////            }
           //
           return true;
       } 
        return false;
    }

    private boolean Merge(String[] lineas) {   
                boolean fin = false;
                Node aux = null;       
                lineas = salida.split("\n");
                String tipo = "";
                   
                    for(int x = 0; x < lineas.length; x++){
                        tipo = String.valueOf(lineas[x].charAt(0));
                        
                        if(Variables.Tipo_Paquete == 1){
                        /*
                        Indentificadores de record en el archivo de datos generado por CSPro
                        Estos indentificadores se encuentran en el primer caracter de cada linea
                        del archivo de datos generado.
                        1 - Caratula de cuestionario de hogar
                        Y - Caratula de cuestionario de gastos y consumos
                        B - Caratula de cuestionario de mujer
                        N - Caratula de cuestionario de empoderamiento
                        
                        Actualización: En el programa de captura de la DHS, la version de CSPRO
                        es muy antigua y el id del record es string de 3 caracteres y no se
                        encuentra al inicio de la linea.
                        
                        
                       
                        */
                        //Si se encuenta tipo 1, empieza un nuevo hogar, por lo que se registra.
                        if(isInteger(tipo)==1){recHogar();NoHogares += 1; }
                        if(tipo.equals("1") || tipo.equals("Y") || tipo.equals("B") || tipo.equals("N")){
                            if(fin && tipo.equals("1")){break;}
                            aux = getNodeRecord(tipo);
                            if(aux != null) {
                                 DataItems(lineas[x], lector.getIdItemsDic());
    //                            data += Data(lineas[x], aux); //ORG
    //                            if(x < 396){
                                    Data(lineas[x], aux);
    //                            }else{
    //                            Data(lineas[x], aux);
    //                            }
                                    if(lineas[x].startsWith("1"+Variables.Ultimo_Verificado)){
                                        fin = true;
                                    }
                            }
                        }
                        }else if( Variables.Tipo_Paquete == 2){
                            /*
                           Comunidad y Servicios
                           Indentificadores de record en el archivo de datos generado por CSPro
                           Estos indentificadores se encuentran en el primer caracter de cada linea
                           del archivo de datos generado.
                           u - Caratula
                           k - Caratula de comunidad
                           1 - Caratula de servicios
                           */
//                            NoHogares = 1;
                            Nohogar = 1;
                            
                           //Si se encuenta tipo u, empieza un nuevo hogar, por lo que se registra.
                           if(tipo.equals("u")){recHogar();NoHogares += 1; }
                           if(tipo.equals("1") || tipo.equals("k") || tipo.equals("u")){
                               if(fin && tipo.equals("u")){break;}
                               aux = getNodeRecord(tipo);
                               if(aux != null) {
                                    DataItems(lineas[x], lector.getIdItemsDic());
                                       Data(lineas[x], aux);
//                                       if(lineas[x].startsWith("1"+Variables.Ultimo_Verificado)){
//                                           fin = true;
//                                       }
                               }
                           }

                       }
                    }
                    recHogar();
                    NohogarA = Nohogar;
                    recCuestionario();
    return true;                
    }
    
    public Node getNodeRecord(String tipo){
        Node aux = null;
        Iterator ilist = records.iterator();
        while(ilist.hasNext()){
            aux = (Node) ilist.next();
            if(lector.getAtt(aux, "RecordTypeValue").equals(tipo)){
                maxRec = lector.getAtt(aux, "MaxRecords");
                return aux;
            }
        }
        return null;
    }

    private String Data(String string, Node aux) {
//        String salida = "Nombre Record: " + lector.getAtt(aux, "Name") + "\t"
//                + "Label Record: " + lector.getAtt(aux, "Label") + "\t";
        
        ArrayList<Node> hijos = lector.getNodosHijos(aux);
        Iterator ihijos = hijos.iterator();
        Node hijo = null;
        int start;
        int len;
        String Nombre;
        String Label;
        String decimal = "0";
        String Valor = "";


        while(ihijos.hasNext()){
            hijo = (Node) ihijos.next();
            start = Integer.valueOf(lector.getStart(hijo));
            len = Integer.valueOf(lector.getLen(hijo));
            Nombre = lector.getAtt(hijo, "Name");
            Label = lector.getAtt(hijo, "Label");
//            if(Nombre.trim().equals("ETABLA")){
//                System.out.println("a");
//                        
//            }
                
            try{
            decimal = lector.getAtt(hijo, "Decimal");
            }catch(Exception e){decimal = "0";}
            try{
            Valor = string.substring(start-1, start-1+len);
//            MaxRec = lector.getAtt(hijo,"MaxRecords");
            }catch(Exception e){
                try{
                Valor = string.substring(start-1, string.length());
    //            MaxRec = lector.getAtt(hijo,"MaxRecords");
                }catch(Exception ex){Valor = "";}
            }
            
//            salida += "Item: " + Nombre + " " + Label + " Valor Digitado: " + Valor 
//                    + " Valor Label: "+ lector.getLabelByItemValue(hijo, Valor) + "\t";
//            getVar(Nombre, Valor);//Originalmente
            
//            salida += "Item: " + Nombre + " Valor Digitado: " + Valor 
//                    + " Valor Label: "+ lector.getLabelByItemValue(hijo, Valor) + "\t";
            
            if(!Valor.trim().equals("") && !decimal.equals("-") && !decimal.equals("0")){
                try{
                    int a = Integer.valueOf(decimal);
                    Valor = Valor.substring(0, Valor.length()-a)+"."+Valor.substring(Valor.length()-a, Valor.length());
                }catch(Exception e){}
            }
//            String var[] = {Nombre, String.valueOf(Valor),maxRec};
//            Digitacion.add(var);
            getVar(Nombre, Valor);
        }
//        salida += "\n";
//        return salida;
        return "";
    }
    private String DataItems(String string, ArrayList<Node> aux){
//        String salida = "";
        ArrayList<Node> hijos = aux;
        Iterator ihijos = hijos.iterator();
        Node hijo = null;
        int start;
        int len;
        String Nombre;
        String Label;
        String decimal="0";
        String Valor = "";
        while(ihijos.hasNext()){
            hijo = (Node)ihijos.next();
            start = Integer.valueOf(lector.getStart(hijo));
            len = Integer.valueOf(lector.getLen(hijo));
            Nombre = lector.getAtt(hijo, "Name");
            Label = lector.getAtt(hijo, "Label");
            try{
                decimal = lector.getAtt(hijo, "Decimal");
            }catch(Exception e){decimal = "0";}
            try{
                Valor = string.substring(start-1,start-1+len);
            }catch(Exception e){Valor = "";}
            
//            salida += "IdItem: " + Nombre + " " + Label + " Valor Digitado: " + Valor
//                    + " Valor Label: " + lector.getLabelByItemValue(hijo, Valor) + "\t";
            if(Nombre.equals(Variables.Hogar)){
                NohogarA = Nohogar;
                Nohogar = Integer.valueOf(Valor.trim());
            }
            //obtener comision
            if(Nombre.equals(Variables.PAQUETE)){
                
                if(paquete.trim().equals(Valor.trim()) || paquete.equals("")){
                paquete = Valor;
//                System.out.println("PAQUETE: " + paquete);
                }else{
                    JOptionPane.showMessageDialog(null,
                            "Existen Hogares de diferentes Paquetes dentro\n"
                           + "del mismo archivo.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                InfoComision(Valor);
            }
//            salida += "IdItem: " + Nombre + " Valor Digitado: " + Valor
//                    + " Valor Label: " + lector.getLabelByItemValue(hijo, Valor) + "\t";
            if(!Valor.trim().equals("") && !decimal.equals("-") && !decimal.equals("0")){
                try{
                    int a = Integer.valueOf(decimal);
                    Valor = Valor.substring(0, Valor.length()-a)+"."+Valor.substring(Valor.length()-a, Valor.length());
                }catch(Exception e){}
            }
//            String var[] = {Nombre, String.valueOf(Valor), maxRec};
//            Digitacion.add(var);
        }
//        return salida;
        return "";
    }
    
    public int getNoHogares(){
        return NoHogares;
    }

     private void getVar(String Nombre, String Valor) {
        //PARA VERIFICACION DE HOGARES
        try{
        if(Nombre.equals(Variables.Miembros)){
            if(!Valor.trim().equals("")){
            Nomiembros = Integer.valueOf(Valor.trim());
            }
            else Nomiembros = 0;
        }else if(Nombre.equals(Variables.Mujeres)){
            if(!Valor.trim().equals("")){
            Nomujeres = Integer.valueOf(Valor.trim());
            }
            else Nomujeres = 0;
        }else if(Nombre.equals(Variables.Ninios)){
            if(!Valor.trim().equals("")){
            Noninos = Integer.valueOf(Valor.trim());
            }
            else Noninos = 0;
        }
        //AGREGADO HOMBRE
        else if(Nombre.equals(Variables.Hombres)){
            if(!Valor.trim().equals("")){
            Nohombres = Integer.valueOf(Valor.trim());
            }
            else Nohombres = 0;
        }
        ////AGREGADO ASOSIADOS Y EMPODERAMIENTO
        try{
        if(Nombre.equals(Variables.EMP)){            
            empoderamiento = Integer.valueOf(Valor.trim()); 
        }else if(Nombre.equals(Variables.ASO)){
            asociado = Integer.valueOf(Valor.trim()); //USADO PARA ASOSIADOS TEMPORALMENTE
        }
        }catch(Exception e){}
        ///////////////////////////////////////
        //PARA VERIFICACION DE CUESTIONARIOS
//        if(val == 1) {
            if(Nombre.equals(Variables.No_Hogar)){
                //SE TERMINO UN CUESTIONARIO
                recCuestionario();
                if(!Valor.trim().equals(""))
                no_hogar = Integer.valueOf(Valor.trim());
                Tipo_Cuestionario_Actual = Variables.C_HOGAR;
            }else if(Nombre.equals(Variables.No_Mujer)){
                //SE TERMINO UN CUESTIONARIO
                recCuestionario();
                if(!Valor.trim().equals(""))
                no_mujer = Integer.valueOf(Valor.trim());
                Tipo_Cuestionario_Actual = Variables.C_MUJER;
            }else if(Nombre.equals(Variables.No_Emp)){
                //SE TERMINO UN CUESTIONARIO
                recCuestionario();
                if(!Valor.trim().equals(""))
                no_emp = Integer.valueOf(Valor.trim());
                Tipo_Cuestionario_Actual = Variables.C_EMPODERAMIENTO;
            }
            /////Gasto y servicios
            else if(Nombre.equals(Variables.No_GyS)){
                //SE TERMINO UN CUESTIONARIO
                recCuestionario();
                if(!Valor.trim().equals(""))
                no_gys = Integer.valueOf(Valor.trim());
                Tipo_Cuestionario_Actual = Variables.C_GASTOS;
            }
            else if(Nombre.equals(Variables.No_Hombre)){
                //SE TERMINO UN CUESTIONARIO
                recCuestionario();
                if(!Valor.trim().equals(""))
                no_hombre = Integer.valueOf(Valor.trim());
                Tipo_Cuestionario_Actual = Variables.C_HOMBRE;
            }
            else if(Nombre.equals(Variables.No_Comunidad)){
                //SE TERMINO UN CUESTIONARIO
                recCuestionario();
                if(!Valor.trim().equals(""))
                no_comunidad = Integer.valueOf(Valor.trim());
                Tipo_Cuestionario_Actual = Variables.C_COMUNIDAD;
            }
            else if(Nombre.equals(Variables.No_Servicios)){
                //SE TERMINO UN CUESTIONARIO
                recCuestionario();
                if(!Valor.trim().equals(""))
                no_servicios = Integer.valueOf(Valor.trim());
                Tipo_Cuestionario_Actual = Variables.C_SERVICIOS;
            }
//        }
        
        if(Nombre.equals(Variables.Resultado_Hogar)){
            if(!Valor.trim().equals(""))
            resultado_hogar = Integer.valueOf(Valor.trim());
        }else if(Nombre.equals(Variables.Resultado_Mujer)){
            if(!Valor.trim().equals(""))
            resultado_mujer = Integer.valueOf(Valor.trim());
        }else if(Nombre.equals(Variables.Resultado_Emp)){
            if(!Valor.trim().equals(""))
            resultado_emp = Integer.valueOf(Valor.trim());
        }else if(Nombre.equals(Variables.Persona_Hogar)){
            if(!Valor.trim().equals("")){
            persona_hogar = Integer.valueOf(Valor.trim());
            }
            else persona_hogar = 0;
        }else if(Nombre.equals(Variables.Persona_Mujer)){
            if(!Valor.trim().equals("")){
            persona_mujer = Integer.valueOf(Valor.trim());
            }
            else persona_mujer = 0;
        }else if(Nombre.equals(Variables.Persona_Emp)){
            if(!Valor.trim().equals("")){
            persona_emp = Integer.valueOf(Valor.trim());
            }
            else persona_emp = 0;
        }
        //Gastos y Servicios
        else if(Nombre.equals(Variables.Resultado_GyS)){
            if(!Valor.trim().equals(""))
            resultado_gys = Integer.valueOf(Valor.trim());
        }else if(Nombre.equals(Variables.Persona_GyS)){
            if(!Valor.trim().equals("")){
            persona_gys = Integer.valueOf(Valor.trim());
            }
            else persona_gys = 0;
        }
        //HOMBRE
        else if(Nombre.equals(Variables.Resultado_Hombre)){
            if(!Valor.trim().equals(""))
            resultado_hombre = Integer.valueOf(Valor.trim());
        }else if(Nombre.equals(Variables.Persona_Hombre)){
            if(!Valor.trim().equals("")){
            persona_hombre = Integer.valueOf(Valor.trim());
            }
            else persona_hombre = 0;
        }
        
        //COMUNIDAD
        else if(Nombre.equals(Variables.Resultado_Comunidad)){
            if(!Valor.trim().equals(""))
            resultado_comunidad = Integer.valueOf(Valor.trim());
        }
        //SERVICIOS
        else if(Nombre.equals(Variables.Resultado_Servicios)){
            if(!Valor.trim().equals(""))
            resultado_servicios = Integer.valueOf(Valor.trim());
        }
        
        //Agregado para la obtencion del personal//
//        if(Nombre.equals(Variables.HSupervisor_Campo)){
////            InfoPersonal(Valor,Variables.HSupervisor_Campo);
//        }else if(Nombre.equals(Variables.MSupervisor_Campo)){
////            InfoPersonal(Valor,Variables.MSupervisor_Campo);
//        }else if(Nombre.equals(Variables.ESupervisor_Campo)){
////            InfoPersonal(Valor,Variables.ESupervisor_Campo);
//        }else if(Nombre.equals(Variables.GSupervisor_Campo)){
////            InfoPersonal(Valor,Variables.GSupervisor_Campo);
//        }
        
        else if(Nombre.equals(Variables.CSupervisor_Campo)){
             supervisor = Integer.valueOf(Valor.trim());
        }
        else if(Nombre.equals(Variables.CEditor_Campo)){
             editor = Integer.valueOf(Valor.trim());
        }
        else if(Nombre.equals(Variables.CEncuestador)){
             encuestador = Integer.valueOf(Valor.trim());
        }
        
        else if(Nombre.equals(Variables.SSupervisor_Campo)){
             supervisor = Integer.valueOf(Valor.trim());
        }
        else if(Nombre.equals(Variables.SEditor_Campo)){
             editor = Integer.valueOf(Valor.trim());
        }
        else if(Nombre.equals(Variables.SEncuestador)){
             encuestador = Integer.valueOf(Valor.trim());
        }
        
//        else if(Nombre.equals(Variables.HEditor_Campo)){
////            InfoPersonal(Valor,Variables.HEditor_Campo);
//        }else if(Nombre.equals(Variables.MEditor_Campo)){
////            InfoPersonal(Valor,Variables.MEditor_Campo);
//        }else if(Nombre.equals(Variables.EEditor_Campo)){
////            InfoPersonal(Valor,Variables.EEditor_Campo);
//        }else if(Nombre.equals(Variables.GEditor_Campo)){
////            InfoPersonal(Valor,Variables.GEditor_Campo);
//        }
//         else if(Nombre.equals(Variables.HEncuestador)){
////            InfoPersonal(Valor,Variables.HEncuestador);
////            InfoGrupo(Valor,Variables.HEncuestador);
//        }else if(Nombre.equals(Variables.MEncuestador)){
////            InfoPersonal(Valor,Variables.MEncuestador);
////            InfoGrupo(Valor,Variables.MEncuestador);
//        }else if(Nombre.equals(Variables.EEncuestador)){
////            InfoPersonal(Valor,Variables.EEncuestador);
////            InfoGrupo(Valor,Variables.EEncuestador);
//        }else if(Nombre.equals(Variables.GEncuestador)){
////            InfoPersonal(Valor,Variables.GEncuestador);
////            InfoGrupo(Valor,Variables.GEncuestador);
//        }
//        else if(Nombre.equals(Variables.HEditor_Oficina)){
//            InfoPersonal(Valor,Variables.HEditor_Oficina);
//        }else if(Nombre.equals(Variables.MEditor_Oficina)){
//            InfoPersonal(Valor,Variables.MEditor_Oficina);
//        }else if(Nombre.equals(Variables.EEditor_Oficina)){
//            InfoPersonal(Valor,Variables.EEditor_Oficina);
//        }else if(Nombre.equals(Variables.GEditor_Oficina)){
//            InfoPersonal(Valor,Variables.GEditor_Oficina);
//        }
//        else if(Nombre.equals(Variables.HDigitador_A)){
//            InfoPersonal(Valor,Variables.HDigitador_A);
//        }else if(Nombre.equals(Variables.MDigitador_A)){
//            InfoPersonal(Valor,Variables.MDigitador_A);
//        }else if(Nombre.equals(Variables.EDigitador_A)){
//            InfoPersonal(Valor,Variables.EDigitador_A);
//        }else if(Nombre.equals(Variables.GDigitador_A)){
//            InfoPersonal(Valor,Variables.GDigitador_A);
//        }
//        else if(Nombre.equals(Variables.HDigitador_B)){
//            InfoPersonal(Valor,Variables.HDigitador_B);
//        }else if(Nombre.equals(Variables.MDigitador_B)){
//            InfoPersonal(Valor,Variables.MDigitador_B);
//        }else if(Nombre.equals(Variables.EDigitador_B)){
//            InfoPersonal(Valor,Variables.EDigitador_B);
//        }else if(Nombre.equals(Variables.GDigitador_B)){
//            InfoPersonal(Valor,Variables.GDigitador_B);
//        }
//        else if(Nombre.equals(Variables.HAntropometrista)){
//            InfoPersonal(Valor,Variables.HAntropometrista);
//        }
        val = 1;
        }catch(Exception e){ 
            System.out.println("EEEEEERRRRROOORRR");
        }
    }

    private void recHogar() {
        if(NoHogares != 0){
            listahogares.add(new Hogar(Nohogar, noCuestionarios, Nomiembros, Nomujeres, Noninos, empoderamiento, asociado, Nohombres));
            noCuestionarios = 0;
            asociado = 0; //USADO PARA ASOSIADOS TEMPORALMENTE
            empoderamiento = 0;  //USADO PARA EMPODERAMIENTO TEMPORALMENTE
        }
    }
    
    private void recCuestionario() {
        
            switch(Tipo_Cuestionario_Actual){
                
                case Variables.C_HOGAR:
                    listacuestionarios.add(new Cuestionario(no_hogar, Variables.C_HOGAR, resultado_hogar, persona_hogar, "",
                            NohogarA, supervisor, encuestador, editor));

                    break;
                case Variables.C_MUJER:
                    listacuestionarios.add(new Cuestionario(no_mujer, Variables.C_MUJER, resultado_mujer, persona_mujer, "",
                            NohogarA, supervisor, encuestador, editor));

                    break;
                case Variables.C_GASTOS:
                    listacuestionarios.add(new Cuestionario(no_gys, Variables.C_GASTOS, resultado_gys, persona_gys, "",
                            NohogarA, supervisor, encuestador, editor));

                    break;
               case Variables.C_EMPODERAMIENTO:
                    listacuestionarios.add(new Cuestionario(no_emp, Variables.C_EMPODERAMIENTO, resultado_emp, persona_emp, "",
                            NohogarA, supervisor, encuestador, editor));

                    break;
               case Variables.C_HOMBRE:
                    listacuestionarios.add(new Cuestionario(no_hombre, Variables.C_HOMBRE, resultado_hombre, persona_hombre, "",
                            NohogarA, supervisor, encuestador, editor));

                    break;
              //AGREGADO PARA COMUNIDAS Y SERVICIOS
               case Variables.C_COMUNIDAD:
                    listacuestionarios.add(new Cuestionario(no_comunidad, Variables.C_COMUNIDAD, resultado_comunidad, persona_emp, "",
                            NohogarA, supervisor, encuestador, editor));

                    break;
               case Variables.C_SERVICIOS:
                    listacuestionarios.add(new Cuestionario(no_servicios, Variables.C_SERVICIOS, resultado_servicios, persona_emp, "",
                            NohogarA, supervisor, encuestador, editor));

                    break;
            }
            noCuestionarios += 1;
        
    }
    
    public ArrayList<Hogar> getListaHogares(){
        return listahogares;
    }
     public ArrayList<Cuestionario> getListaCuestionario(){
        return listacuestionarios;
    }
    
    public void digitacion_print(Propiedades rc, int Dig) {
        mongoDB mongo = new mongoDB();
        String DBMongo = "";
        if(Dig == 1){
            DBMongo = Variables.PDigitacion;
        }else if(Dig == 2){
            DBMongo = Variables.SDigitacion;
        }else if(Dig == 3){
            DBMongo = Variables.VDigitacion;
        }else{
            DBMongo = "Datos";
        }
//        mongo.mongoDB(rc.getDireccionBDMongo(), rc.getNombreBDMongo(), 
//                        rc.getUsuarioBDMongo(), rc.getPassBDMongo());
        mongo.mongoDB(rc.getDireccionBDMongo(), DBMongo, 
                        rc.getUsuarioBDMongo(), rc.getPassBDMongo());
        Iterator idig = Digitacion.iterator();
        String dig[] = null;
//        mongo.beginInsertHogarGastos();
//        mongo.beginInsertMujerEmp();

        while(idig.hasNext()){
            dig = (String[])idig.next();
            mongo.InsertPrueba4(dig);
//            System.out.println(dig[0] + "  " + dig[1] + " " + dig[2]);
        }
        mongo.setColHogarGastos();       
        mongo.finishInsertHogarGastos();
        mongo.setColMujerEmp();
        mongo.finishInsertMujerEmp();
        mongo.Disconnect();
    }

    private int isInteger(String tipo) {
        try{
            return Integer.valueOf(tipo);
        }catch(Exception e){
            return 0;
        }
    }

    private void InfoPersonal(String Valor, String a) {
        DBClient con = new DBClient();
        Connection c = null;
        try { 
            c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
                    ,rc.getPuertoBD());
            
            ResultSet rs = con.PersonaByCod(c, Valor);
            
           if(rs.next()){
//               System.out.println(rs.getObject(1).toString()+rs.getObject(2)+rs.getObject(3)+rs.getObject(4)
//                       +rs.getObject(5));
               
                String var[] = {a+"Nombre", rs.getObject(2)+" "+rs.getObject(3),"0"};
//                String var2[] = {a+"Telefono", rs.getObject(5).toString(),"0"};
                Digitacion.add(var);
//                Digitacion.add(var2);
                
           }
            c.close();

        }catch (SQLException ex) {
            try {
                c.close();
            } catch (SQLException ex1) {
                Logger.getLogger(InfoRes.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    private void InfoGrupo(String Valor, String a){
        DBClient con = new DBClient();
        Connection c = null;
        try { 
            c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
                    ,rc.getPuertoBD());
            
            ResultSet rs = con.GrupoByCod(c, Valor);           
            if(rs.next()){
//               System.out.println(rs.getObject(1).toString()+rs.getObject(2)+rs.getObject(3)+rs.getObject(4)
//                       +rs.getObject(5));
               
                String var[] = {a+"NGrupo", rs.getObject(1)+"","0"};
                Digitacion.add(var);
            }
            c.close();
        }catch (SQLException ex) {
            try {
                c.close();
            } catch (SQLException ex1) {
                Logger.getLogger(InfoRes.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
    private void InfoComision(String Valor) {
        DBClient con = new DBClient();
        Connection c = null;
        try { 
            c = con.getConnection(rc.getDireccionBD(),rc.getNombreBD(),rc.getUsuarioBD(),rc.getPassBD()
                    ,rc.getPuertoBD());
            
            ResultSet rs = con.ComisionByPaquete(c,Valor);
            
           if(rs.next()){
//               System.out.println(rs.getObject(1).toString()+rs.getObject(2)+rs.getObject(3)+rs.getObject(4)
//                       +rs.getObject(5)+rs.getObject(6));
                String var[] = {"Comision", rs.getObject(2).toString(),"0"};
                String var2[] = {"FechaInicio", rs.getObject(5).toString(),"0"};
                String var3[] = {"FechaFin", rs.getObject(6).toString(),"0"};
                Digitacion.add(var);
                Digitacion.add(var2);
                Digitacion.add(var3);
           }
            c.close();
        }catch (SQLException ex) {
            try {
                c.close();
            } catch (SQLException ex1) {
                Logger.getLogger(InfoRes.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}
