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
import javax.swing.JLabel;
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
public class Info {
    String xml;
    String salida;
    LeerXml lector;
    ArrayList<Node> records;
    Node aux;
    int NoHogares = 0;
    int NohogarA = 0;
    int noCuestionarios = 0;
    int noCHogar = 0;
    int noCMujer = 0;
    int noCEmp = 0;
    String paqueteactual = "";
    //VERIFICAR HOGAR
    int Nohogar = 0;
    int Nomiembros = 0;
    int Nomujeres = 0;
    int Noninos = 0;
    ArrayList<Hogar> listahogares = new ArrayList<Hogar>();
    
    //VERIFICAR CUESTIONARIOS
    int no_hogar = 0;
    int no_mujer = 0;
    int no_emp = 0;   
    int resultado_hogar = 0;
    int resultado_mujer = 0;
    int resultado_emp = 0;
    int persona_hogar = 0;
    int persona_mujer = 0;
    int persona_emp = 0;
    
    int no_hombre = 0;
    int resultado_hombre = 0;
    int persona_hombre = 0;
    
    //VERIFICAR GASTOS Y SERVICIOS
    int no_gys = 0;
    int resultado_gys = 0;
    int persona_gys = 0;
    
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

    Propiedades rc = null;
    String paquete = "";
    String maxRec = "";
    
    public Info(String xml, String salida,Propiedades rc){
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
           return true;
       } 
        return false;
    }

    private boolean Merge(String[] lineas) {   
        int Y = 0;
    try{
                Node aux = null;       
                lineas = salida.split("\n");
                String tipo = "";
                //ELIMINADO LO DE DATA PARA PROBAR RENDIMIENTO
//                String data = "";
                    
                    for(int x = 0; x < lineas.length; x++){
                        Y = x;
//                        System.out.println(lineas[x].charAt(0));
                        tipo = String.valueOf(lineas[x].charAt(0));
//                        if(isInteger(tipo)==1){recHogar();NoHogares += 1; }
//                        aux = lector.getIdItems(); modificacion martes
//                        if(aux != null){
//                            data += DataItems(lineas[x], lector.getIdItemsDic()); //ORG
                            DataItems(lineas[x], lector.getIdItemsDic());
//                        }
                        aux = getNodeRecord(tipo);
                        if(aux != null) {
//                            data += Data(lineas[x], aux); //ORG
                            if(x < 396){
                                Data(lineas[x], aux);
                            }else{
                            Data(lineas[x], aux);
                            }
                        }
                    }
//                    recHogar();
//                    NohogarA = Nohogar;
//                    recCuestionario();
//                    System.out.println(data);
//                    new PruebaXML().writeFile("C:\\PAQUETES\\SALIDA.txt", data + "\nNoHogares: " + NoHogares);
            //Ingresa informacion en MongoDB Aqui inserta sin haber verificado es solo una prueba.
//                    digitacion_print(Propiedades.);
        return true;     
        }catch(Exception e){
           System.out.println(lineas[Y]);
        }
 return false;
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
            String var[] = {Nombre, String.valueOf(Valor),maxRec};
            Digitacion.add(var);
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
                paqueteactual = Valor;
//                System.out.println(paquete);
//                System.out.println(Valor);
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
            String var[] = {Nombre, String.valueOf(Valor), maxRec};
            Digitacion.add(var);
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
////////        if(Nombre.equals(Variables.Miembros)){
////////            if(!Valor.trim().equals("")){
////////            Nomiembros = Integer.valueOf(Valor.trim());
////////            }
////////            else Nomiembros = 0;
////////        }else if(Nombre.equals(Variables.Mujeres)){
////////            if(!Valor.trim().equals("")){
////////            Nomujeres = Integer.valueOf(Valor.trim());
////////            }
////////            else Nomujeres = 0;
////////        }else if(Nombre.equals(Variables.Ninios)){
////////            if(!Valor.trim().equals("")){
////////            Noninos = Integer.valueOf(Valor.trim());
////////            }
////////            else Noninos = 0;
////////        }
////////        ////AGREGADO ASOSIADOS Y EMPODERAMIENTO
////////        try{
////////        if(Nombre.equals(Variables.EMP)){
////////             
////////            noCEmp = Integer.valueOf(Valor.trim()); //USADO PARA VAR DE EMPODERAMIENTO
////////        }else if(Nombre.equals(Variables.ASO)){
////////            noCMujer = Integer.valueOf(Valor.trim()); //USADO PARA ASOSIADOS TEMPORALMENTE
////////        }
////////        /*agregar/modificar para las variables nuevas o cantidad de hombres*/
////////        
////////        }catch(Exception e){}
////////        ///////////////////////////////////////
////////        //PARA VERIFICACION DE CUESTIONARIOS
//////////        if(val == 1) {
////////            if(Nombre.equals(Variables.No_Hogar)){
////////                //SE TERMINO UN CUESTIONARIO
////////                recCuestionario();
////////                if(!Valor.trim().equals(""))
////////                no_hogar = Integer.valueOf(Valor.trim());
////////                Tipo_Cuestionario_Actual = Variables.C_HOGAR;
////////            }else if(Nombre.equals(Variables.No_Mujer)){
////////                //SE TERMINO UN CUESTIONARIO
////////                recCuestionario();
////////                if(!Valor.trim().equals(""))
////////                no_mujer = Integer.valueOf(Valor.trim());
////////                Tipo_Cuestionario_Actual = Variables.C_MUJER;
////////            }else if(Nombre.equals(Variables.No_Emp)){
////////                //SE TERMINO UN CUESTIONARIO
////////                recCuestionario();
////////                if(!Valor.trim().equals(""))
////////                no_emp = Integer.valueOf(Valor.trim());
////////                Tipo_Cuestionario_Actual = Variables.C_EMPODERAMIENTO;
////////            }
////////            /////Gasto y servicios
////////            else if(Nombre.equals(Variables.No_GyS)){
////////                //SE TERMINO UN CUESTIONARIO
////////                recCuestionario();
////////                if(!Valor.trim().equals(""))
////////                no_gys = Integer.valueOf(Valor.trim());
////////                Tipo_Cuestionario_Actual = Variables.C_GASTOS;
////////            }
//////////        }
////////        
////////        if(Nombre.equals(Variables.Resultado_Hogar)){
////////            if(!Valor.trim().equals(""))
////////            resultado_hogar = Integer.valueOf(Valor.trim());
////////        }else if(Nombre.equals(Variables.Resultado_Mujer)){
////////            if(!Valor.trim().equals(""))
////////            resultado_mujer = Integer.valueOf(Valor.trim());
////////        }else if(Nombre.equals(Variables.Resultado_Emp)){
////////            if(!Valor.trim().equals(""))
////////            resultado_emp = Integer.valueOf(Valor.trim());
////////        }else if(Nombre.equals(Variables.Persona_Hogar)){
////////            if(!Valor.trim().equals("")){
////////            persona_hogar = Integer.valueOf(Valor.trim());
////////            }
////////            else persona_hogar = 0;
////////        }else if(Nombre.equals(Variables.Persona_Mujer)){
////////            if(!Valor.trim().equals("")){
////////            persona_mujer = Integer.valueOf(Valor.trim());
////////            }
////////            else persona_mujer = 0;
////////        }else if(Nombre.equals(Variables.Persona_Emp)){
////////            if(!Valor.trim().equals("")){
////////            persona_emp = Integer.valueOf(Valor.trim());
////////            }
////////            else persona_emp = 0;
////////        }
////////        //Gastos y Servicios
////////        else if(Nombre.equals(Variables.Resultado_GyS)){
////////            if(!Valor.trim().equals(""))
////////            resultado_gys = Integer.valueOf(Valor.trim());
////////        }else if(Nombre.equals(Variables.Persona_GyS)){
////////            if(!Valor.trim().equals("")){
////////            persona_gys = Integer.valueOf(Valor.trim());
////////            }
////////            else persona_gys = 0;
////////        }

        //Agregado para la obtencion del personal//
        if(Nombre.equals(Variables.HSupervisor_Campo)){
            InfoPersonal(Valor,Variables.HSupervisor_Campo);
        }else if(Nombre.equals(Variables.MSupervisor_Campo)){
            InfoPersonal(Valor,Variables.MSupervisor_Campo);
        }else if(Nombre.equals(Variables.ESupervisor_Campo)){
            InfoPersonal(Valor,Variables.ESupervisor_Campo);
        }else if(Nombre.equals(Variables.GSupervisor_Campo)){
            InfoPersonal(Valor,Variables.GSupervisor_Campo);
        }
        else if(Nombre.equals(Variables.HEditor_Campo)){
            InfoPersonal(Valor,Variables.HEditor_Campo);
        }else if(Nombre.equals(Variables.MEditor_Campo)){
            InfoPersonal(Valor,Variables.MEditor_Campo);
        }else if(Nombre.equals(Variables.EEditor_Campo)){
            InfoPersonal(Valor,Variables.EEditor_Campo);
        }else if(Nombre.equals(Variables.GEditor_Campo)){
            InfoPersonal(Valor,Variables.GEditor_Campo);
        }
        else if(Nombre.equals(Variables.HEditor_Oficina)){
            InfoPersonal(Valor,Variables.HEditor_Oficina);
        }else if(Nombre.equals(Variables.MEditor_Oficina)){
            InfoPersonal(Valor,Variables.MEditor_Oficina);
        }else if(Nombre.equals(Variables.EEditor_Oficina)){
            InfoPersonal(Valor,Variables.EEditor_Oficina);
        }else if(Nombre.equals(Variables.GEditor_Oficina)){
            InfoPersonal(Valor,Variables.GEditor_Oficina);
        }
        else if(Nombre.equals(Variables.HDigitador_A)){
            InfoPersonal(Valor,Variables.HDigitador_A);
        }else if(Nombre.equals(Variables.MDigitador_A)){
            InfoPersonal(Valor,Variables.MDigitador_A);
        }else if(Nombre.equals(Variables.EDigitador_A)){
            InfoPersonal(Valor,Variables.EDigitador_A);
        }else if(Nombre.equals(Variables.GDigitador_A)){
            InfoPersonal(Valor,Variables.GDigitador_A);
        }
        else if(Nombre.equals(Variables.HDigitador_B)){
            InfoPersonal(Valor,Variables.HDigitador_B);
        }else if(Nombre.equals(Variables.MDigitador_B)){
            InfoPersonal(Valor,Variables.MDigitador_B);
        }else if(Nombre.equals(Variables.EDigitador_B)){
            InfoPersonal(Valor,Variables.EDigitador_B);
        }else if(Nombre.equals(Variables.GDigitador_B)){
            InfoPersonal(Valor,Variables.GDigitador_B);
        }
        else if(Nombre.equals(Variables.HEncuestador)){
            InfoPersonal(Valor,Variables.HEncuestador);
            InfoGrupo(paqueteactual,Variables.HEncuestador);
        }else if(Nombre.equals(Variables.MEncuestador)){
            InfoPersonal(Valor,Variables.MEncuestador);
            InfoGrupo(paqueteactual,Variables.MEncuestador);
        }else if(Nombre.equals(Variables.EEncuestador)){
            InfoPersonal(Valor,Variables.EEncuestador);
            InfoGrupo(paqueteactual,Variables.EEncuestador);
        }else if(Nombre.equals(Variables.GEncuestador)){
            InfoPersonal(Valor,Variables.GEncuestador);
            InfoGrupo(paqueteactual,Variables.GEncuestador);
        }
        else if(Nombre.equals(Variables.HAntropometrista)){
            InfoPersonal(Valor,Variables.HAntropometrista);
        }
        val = 1;
        }catch(Exception e){ 
            System.out.println("EEEEEERRRRROOORRR");
        }
    }

//////    private void recHogar() {
//////        if(NoHogares != 0){
//////            listahogares.add(new Hogar(Nohogar, noCuestionarios, Nomiembros, Nomujeres, Noninos, noCHogar, noCMujer, noCEmp));
//////            noCuestionarios = 0;
//////            noCHogar = 0;
//////            noCMujer = 0; //USADO PARA ASOSIADOS TEMPORALMENTE
//////            noCEmp = 0;  //USADO PARA EMPODERAMIENTO TEMPORALMENTE
//////        }
//////    }
//////    
//////    private void recCuestionario() {
//////        //TIPO DE CUESTIONARIO
//////            switch(Tipo_Cuestionario_Actual){
//////                
//////                case Variables.C_HOGAR:
//////                    listacuestionarios.add(new Cuestionario(no_hogar, Variables.C_HOGAR, resultado_hogar, persona_hogar, "",NohogarA));
////////                    noCHogar += 1;
//////                    break;
//////                case Variables.C_MUJER:
//////                    listacuestionarios.add(new Cuestionario(no_mujer, Variables.C_MUJER, resultado_mujer, persona_mujer, "",NohogarA));
////////                    asociado += 1; 
//////                    break;
//////                case Variables.C_GASTOS:
//////                    listacuestionarios.add(new Cuestionario(no_gys, Variables.C_GASTOS, resultado_gys, persona_gys, "",NohogarA));
////////                    empoderamiento += 1;
//////                    break;
//////               case Variables.C_EMPODERAMIENTO:
//////                    listacuestionarios.add(new Cuestionario(no_emp, Variables.C_EMPODERAMIENTO, resultado_emp, persona_emp, "",NohogarA));
////////                    empoderamiento += 1;
//////                    break;
//////               //AGREGADO PARA HOMBRE
//////               case Variables.C_HOMBRE:
//////                    listacuestionarios.add(new Cuestionario(no_hombre, Variables.C_HOMBRE, resultado_hombre, persona_hombre, "",NohogarA));
////////                    asociado += 1; 
//////                    break;
//////               //AGREGADO PARA COMUNIDAS Y SERVICIOS
////////               case Variables.C_COMUNIDAD:
////////                    listacuestionarios.add(new Cuestionario(no_emp, Variables.C_COMUNIDAD, resultado_emp, persona_emp, "",NohogarA));
//////////                    empoderamiento += 1;
////////                    break;
////////              case Variables.C_SERVICIOS:
////////                    listacuestionarios.add(new Cuestionario(no_emp, Variables.C_SERVICIOS, resultado_emp, persona_emp, "",NohogarA));
//////////                    empoderamiento += 1;
////////                    break;
//////            }
//////            noCuestionarios += 1;
//////        
//////    }
//////    
//////    public ArrayList<Hogar> getListaHogares(){
//////        return listahogares;
//////    }
//////     public ArrayList<Cuestionario> getListaCuestionario(){
//////        return listacuestionarios;
//////    }
    
    public void digitacion_print(Propiedades rc, int Dig, JLabel jLabel4) {
        jLabel4.setVisible(false);
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
       jLabel4.setVisible(true);
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
            Valor = Valor.trim();
//            ResultSet rs = con.GrupoByCod(c, Valor);  
            ResultSet rs = con.GrupoByPaquete(c, Valor);
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
            Valor = Valor.trim();
            ResultSet rs = con.ComisionByPaquete(c,Valor);
            
           if(rs.next()){
//               System.out.println(rs.getObject(1).toString()+rs.getObject(2)+rs.getObject(3)+rs.getObject(4)
//                       +rs.getObject(5)+rs.getObject(6));
               //Se cambio a 2 para que obtuviera el nombre de la comision no el ID.
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
