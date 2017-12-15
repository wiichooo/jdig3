/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import jdigitador3.Variables;
import org.w3c.dom.Node;
import pruebaxml.LeerXml;

/**
 *
 * @author Luis
 */
public class mongoDB {
   DBCollection collection = null;
    MongoClient mongoClient = null;
    DB db = null;
    BasicDBObject document = null;
    BasicDBObject docHogarGastos = null;
    BasicDBObject docMujerEmp = null;
    int documentoactual = -1;
    ArrayList<BasicDBObject> documents = new ArrayList<BasicDBObject>();
    String HPAQUETE = "";
    String HHOGAR = "";
    String HLINEA = "";
    String hlineaanterior = "";
        //Para prueba de ingreso de datos:
        String numc = "";
        String valc = "";
        //Prueba de guardar todo el hogar en un solo documento
        int NoH = 0;
        int NoM = 0;
        int NoE = 0;
        int NoG = 0;

    //COMISION
    String Comision = null;
    String FechaIni = null;
    String FechaFin = null;
    
    
    BasicDBObject qexists = null;
        BasicDBObject col = null;
        BasicDBObject qexists2 = null;
        BasicDBObject col2 = null;
        
        BasicDBObject qexists3 = null;
        BasicDBObject col3 = null;
        
          DBCursor vexists = null;
          
          DBCursor vexists2 = null;
          
          DBCursor vexists3 = null;
          BasicDBObject documento = null;
          
          
    public mongoDB(){
//        try {
//            mongoClient = new MongoClient("192.168.1.64");
//            db = mongoClient.getDB("admin");
//            boolean auth = db.authenticate("admin", "mongodb".toCharArray());
//                
//            if(auth) {
////                System.out.println("Si se conecto");
////                Set<String> tables = db.getCollectionNames();
//// 
////                for(String coll : tables){
////                        System.out.println(coll);
////                }
//                
//                collection = db.getCollection("Prueba2");
////                DBCursor cursor = collection.find();
////                while (cursor.hasNext()) {
////                        System.out.println(cursor.next());
////                }
////                cursor.close();
//                
////               collection.remove(new BasicDBObject());
//                
//            }
//            else {
//                System.out.println("No se conecto");
//            }          
//        
//        } catch (UnknownHostException ex) {
////            Logger.getLogger(mongoDB.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("No se conecto");
//        }
    }
        public boolean mongoDB(String servidor, String bd, String user, String pass){
       try {
            mongoClient = new MongoClient(servidor);
            db = mongoClient.getDB(bd);
            boolean auth = db.authenticate(user, pass.toCharArray());
                
            
            
            if(auth) {
//                collection = db.getCollection("info");
                return true;   
            }
            else {
                System.out.println("No se conecto " + servidor + bd + user + pass);
                return false;
            }          
        
        } catch (UnknownHostException ex) {
//            Logger.getLogger(mongoDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception e){
            return false;
        }       
 
            
    }

    public void Disconnect(){
        mongoClient.close();  
    }
    
    public void beginInsert(){
        document = new BasicDBObject();
    }
    public void beginInsertHogarGastos(){
        docHogarGastos = new BasicDBObject();
        documents.add(0,docHogarGastos);
        
    }
    public void beginInsertMujerEmp(){
        docMujerEmp= new BasicDBObject();
        documents.add(1,docMujerEmp);
    }
    public void Insert(String a[]){
        
        if(a[0].trim().equals(Variables.PAQUETE)){
            HPAQUETE = a[1].trim();
        }else if(a[0].trim().equals(Variables.Hogar)){
            HHOGAR = a[1].trim();
        }else if(a[0].trim().equals(Variables.Hogar_linea)){
            HLINEA = a[1].trim();
        }
        else
        if(a[0].trim().equals(Variables.No_Hogar) || a[0].trim().equals(Variables.No_Mujer) || a[0].trim().equals(Variables.No_Emp)){
            if(!document.isEmpty()) {
                finishInsert();
            }
            
            beginInsert();
            document.put(Variables.PAQUETE,HPAQUETE);
            document.put(Variables.Hogar,HHOGAR);
            document.put(Variables.Hogar_linea,HLINEA);
            document.put(a[0].trim(),a[1].trim());
        }else {
            document.put(a[0].trim(),a[1].trim());
        }

    }
    
    public void finishInsert(){
        collection.insert(document);
    }
    public void finishInsertMujerEmp(){
        collection.insert(docMujerEmp);
    }
    public void finishInsertHogarGastos(){
        collection.insert(docHogarGastos);
    }
    //Genera los campos para la tabla
    public void camposCollection(String xml){
        collection = db.getCollection("campos");
        beginInsert();
           
        LeerXml a = new LeerXml(xml);
        ArrayList<Node> iditems = a.getIdItemsDic();
        ArrayList<Node> asd = a.getRaizDic();
        ArrayList<Node> hijos = null;
        
        Iterator ilist = asd.iterator();
        Iterator iditemslist = iditems.iterator();
        Iterator ilist2 = null;
        Node qwe;
        Node aux;
        int MaxRecords = 1;
        
        while(iditemslist.hasNext()){
            qwe = (Node)iditemslist.next();
            String v = qwe.getAttributes().getNamedItem("Name").getNodeValue();
            document.put(v,"");
//            System.out.print(","+v);
        }
        while(ilist.hasNext()){
            qwe = (Node)ilist.next();
//            System.out.print(qwe.getAttributes().getNamedItem("Name").getNodeValue());
            try{
                MaxRecords = Integer.valueOf(qwe.getAttributes().getNamedItem("MaxRecords").getNodeValue().trim());
//                System.out.print(MaxRecords);
            }catch(Exception e){
                MaxRecords = 1; 
//                System.out.println();
            }              
            
            hijos = a.getNodosHijos(qwe);
            ilist2 = hijos.iterator();
     
            
            while(ilist2.hasNext()){
                aux = (Node)ilist2.next();
                
                if(MaxRecords==1){
                    String v = aux.getAttributes().getNamedItem("Name").getNodeValue();
//                    System.out.print(","+v);
                    document.put(v,"");
                }else {
                    for(int z = 1; z <= MaxRecords; z++){ 
                        String v = aux.getAttributes().getNamedItem("Name").getNodeValue() + "_" + z;
//                        System.out.print(","+v);
                          document.put(v,"");
                    }
                }
//                System.out.println();
            }
            

        }
        finishInsert();
        Disconnect();
    }
     
    public void find(String field, String value, String numc, String valc, int agregar){
        DBCollection tmp = db.getCollection("campos");
        BasicDBObject qempty = new BasicDBObject(numc, valc).append(field, new BasicDBObject("$gt", "-1"));
        BasicDBObject qexists = new BasicDBObject(field, new BasicDBObject("$exists", "true"));
        BasicDBObject col = new BasicDBObject(field, "1");
//        { "MNUM" : "10", "MRES" : { $exists : true }}


//        DBCursor vexists = collection.find(qexists, col); //ORG Martes23
          DBCursor vexists = tmp.find(qexists, col);
//        DBCursor vempty = collection.find(qempty, col);
      
        if(vexists.hasNext()){
            if(!document.containsKey(field)){
                if(agregar != 0) {
                    document.put(field+"_"+agregar,value);
                }
                else {
                    document.put(field,value);
                }
            }
        }else{
            if(field.contains("Nombre") || field.contains("Telefono") 
//                    || field.equals("Comision") || field.equals("Fecha Inicio") || field.equals("Fecha Fin")
                    ){
                if(agregar != 0)
                    document.put(field+"_"+agregar,value);
                else
                    document.put(field,value);
            }else {
                findNext(field,value, 1, numc,valc, agregar);
            }
        }        
//        try {
//            while(vexists.hasNext()) {
//                System.out.println(vexists.next());
//            }
//         } finally {
//            vexists.close();
//         }
    }
    public void findNext(String field, String value, int num, String numc, String valc, int agregar){
//        field = field + "_" + num;
        DBCollection tmp = db.getCollection("campos");
        BasicDBObject qempty = new BasicDBObject(numc, valc).append(field+ "_" + num, new BasicDBObject("$gt", "-1"));
        BasicDBObject qexists = new BasicDBObject(field+ "_" + num, new BasicDBObject("$exists", "true"));
        BasicDBObject col = new BasicDBObject(field+ "_" + num, "1");
//        { "MNUM" : "10", "MRES" : { $exists : true }}
        
//        DBCursor vexists = collection.find(qexists, col);//ORG Martes 23
        DBCursor vexists = tmp.find(qexists, col);
//        DBCursor vempty = collection.find(qempty, col);
        if(vexists.hasNext()){
            if(!document.containsKey(field+ "_" + num)){
//                document.put(field+ "_" + num,value);
                if(agregar != 0)
                    document.put(field+ "_" + num+"_"+agregar,value);
                else
                    document.put(field+ "_" + num,value);
            }else{
                num = num + 1;
                findNext(field, value,num, numc, valc, agregar);
            } 
        }
        

    }
    
    //Utilizando 2 colecciones. 1 hogares 2 cuestionarios
    public void InsertPrueba4(String a[]){

        if(a[0].trim().equals(Variables.PAQUETE)){
            HPAQUETE = a[1].trim();
        }else if(a[0].trim().equals(Variables.Hogar)){
            HHOGAR = a[1].trim();
        }else if(a[0].trim().equals(Variables.Hogar_linea)){
            hlineaanterior = HLINEA;
            HLINEA = a[1].trim();
        }
        //AGREGAR LA COMISION
        else if(a[0].trim().equals("Comision")){
            Comision = a[1].trim();
        }else if(a[0].trim().equals("FechaInicio")){
            FechaIni = a[1].trim();
        }else if(a[0].trim().equals("FechaFin")){
            FechaFin = a[1].trim();
        }
        else{
            if(a[0].trim().equals(Variables.No_Hogar) || a[0].trim().equals(Variables.No_GyS)
                    || a[0].trim().equals(Variables.No_Emp) || a[0].trim().equals(Variables.No_Mujer)){

                
                if(a[0].trim().equals(Variables.No_Hogar) || a[0].trim().equals(Variables.No_GyS)){
                    collection = db.getCollection(Variables.ColHogar);
                    if(a[0].trim().equals(Variables.No_Hogar)){
                        if(docHogarGastos != null && !docHogarGastos.isEmpty()) {
                            finishInsertHogarGastos();
                            beginInsertHogarGastos();
                        }else{
                            beginInsertHogarGastos();
                        }
                    }
                    documentoactual = 0;
                    docHogarGastos.put(Variables.PAQUETE,HPAQUETE);
                    docHogarGastos.put(Variables.Hogar,HHOGAR);
                      //AGREGAR COMISION
                   docHogarGastos.put("COMISION",Comision);
//                   docHogarGastos.put("FechaInicio",FechaIni);
//                   docHogarGastos.put("FechaFin",FechaFin);
                    numc = a[0].trim();
                    //quite el trim()
                    valc = a[1];
                    docHogarGastos.put(a[0].trim(),a[1]);
                   
                }else if(a[0].trim().equals(Variables.No_Mujer) || a[0].trim().equals(Variables.No_Emp)){
                    collection = db.getCollection(Variables.ColMujer);
                    
                    
//                        if(docMujerEmp == null) {
////                            finishInsertHogarGastos();
//                            beginInsertMujerEmp();
//                        }
                  
                        if(!hlineaanterior.equals(HLINEA)){
                            if(docMujerEmp == null) {
//                            finishInsertHogarGastos();
                                beginInsertMujerEmp();
                            }else{
                                finishInsertMujerEmp();
                                beginInsertMujerEmp();
                            }
                        }
                    documentoactual = 1;
                    docMujerEmp.put(Variables.Hogar_linea,HLINEA);
                    numc = a[0].trim();
                    valc = a[1];//quite trim() 13 de mayo
                    docMujerEmp.put(a[0].trim(),a[1]);
                }

            }else {
                
//                 documento = (BasicDBObject)documents.get(documentoactual);
//            documento.put(a[0].trim(),a[1].trim());
//                  find44(a[0].trim(),a[1].trim(),numc, valc,0,"","","");//Ultimo parametro agregado para la siguiente prueba InsertPrueba
                                    //quite trim()
                find443(a[0].trim(),a[1],numc, valc,0,a[2].trim());
            }
        }

    }
    
    public void find443(String field, String value, String numc, String valc, int agregar,String maxR){
//        DBCollection tmp = db.getCollection("campos");
//        if(field.contains("1008")){
//            System.out.println("");
//        }

        qexists = new BasicDBObject(field, new BasicDBObject("$exists", "true"));
        col = new BasicDBObject(field, "1");
        int maxRi = 0;
        try{
            maxRi = Integer.valueOf(maxR);
        }catch(Exception e){
            maxRi = 0;
        }
//        qexists2 = new BasicDBObject(field+complemento2, new BasicDBObject("$exists", "true"));
//        col2 = new BasicDBObject(field+complemento2, "1");
//        
//        qexists3 = new BasicDBObject(field+complemento3, new BasicDBObject("$exists", "true"));
//        col3 = new BasicDBObject(field+complemento3, "1");
        
        documento = (BasicDBObject)documents.get(documentoactual);

//        DBCursor vexists = collection.find(qexists, col); //ORG Martes23
//          vexists = collection.find(qexists, col).limit(1);
          
//          vexists2 = collection.find(qexists2, col2);
//          
//          vexists3 = collection.find(qexists3, col3);
//        DBCursor vempty = collection.find(qempty, col);
//      try{
        if(maxRi == 0){
//            String a = document.getString(field);
            
            if(!documento.containsKey(field)){
                    documento.put(field,value);
            }
        }
        else{
            if(field.contains("Nombre") || field.contains("NGrupo")){
                documento.put(field,value);
            }else
            while(true){
                agregar += 1;
                    if(maxRi >= 100 && agregar < 10){
                        if(!documento.containsKey(field+"_00"+agregar)){
                            documento.put(field+"_00"+agregar,value);
                            break;
                        }
                    }else if(maxRi >= 100 && agregar >= 10 && agregar < 100){
                        if(!documento.containsKey(field+"_0"+agregar)){
                            documento.put(field+"_0"+agregar,value);
                            break;
                        }
                    }else if(maxRi >= 100 && agregar >= 100){
                        if(!documento.containsKey(field+"_"+agregar)){
                            documento.put(field+"_"+agregar,value);
                            break;
                        }
                    }else if(maxRi >= 10 && agregar < 10){
                        if(!documento.containsKey(field+"_0"+agregar)){
                            documento.put(field+"_0"+agregar,value);
                            break;
                        }
                    }else if(maxRi >= 10 && agregar >= 10){
                        if(!documento.containsKey(field+"_"+agregar)){
                            documento.put(field+"_"+agregar,value);
                            break;
                        }
                    }else if(maxRi < 10 && agregar < 10){
                        if(!documento.containsKey(field+"_"+agregar)){
                            documento.put(field+"_"+agregar,value);
                            break;
                        }
                    }
              
            }
        }
        return;
       
//      }catch(Exception e){ System.out.println(field);}
    }

    public void setColHogarGastos() {
        collection = db.getCollection(Variables.ColHogar);
    }

    public void setColMujerEmp() {
        collection = db.getCollection(Variables.ColMujer);
    }

}
