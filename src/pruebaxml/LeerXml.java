/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Luis
 */

 public class LeerXml {

Document documento = null;
int records;
int recordsTotales;
 public LeerXml() {}
 public LeerXml(String xml) {

try {
 records = 0;
 recordsTotales = 0;

//InputStream inputStream = new FileInputStream(xml);
//BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//String entrada;
//String cadena="";
//
//while ((entrada = br.readLine()) != null){
//	cadena = cadena + entrada;
//}

DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
DocumentBuilder db = dbf.newDocumentBuilder();

InputSource archivo = new InputSource();
archivo.setCharacterStream(new StringReader(xml)); 

documento = db.parse(archivo);
documento.getDocumentElement().normalize();

//
//NodeList Diccionario = documento.getElementsByTagName("DICTIONARY");
//
//    InfoDicc(documento);
//
//    Node raiz = Diccionario.item(0);
//    ArrayList list = new ArrayList();
////    list = getAllNodeLabels(raiz,list);
//    
//    Iterator ilist = list.iterator();
////    while(ilist.hasNext())
////        System.out.println(ilist.next().toString());
//
//    raiz = getNodeByName("CAP_PRECIOS_DICT", raiz);
//    
// //   raiz = getNodeByLabel("Jakalteco (popti)", raiz);
////    list = getAllNodeLabels(raiz, list);
////    ilist = list.iterator();
////    while(ilist.hasNext())
////        System.out.println(ilist.next().toString());
//
//    System.out.println(getAtributos(raiz));
//    System.out.println(getHijos(raiz));
//    System.out.println(getStart(raiz));
//    System.out.println(getLen(raiz));
    
//    ArrayList<Node> nodelist =  new ArrayList<>();
//    nodelist = getNodeListByLabel("blanco", raiz, nodelist);
//    Iterator inodelist = nodelist.iterator();
//    
//    while(inodelist.hasNext()){
//        list = getAtributosLista((Node)inodelist.next());
//        ilist = list.iterator();
//        while(ilist.hasNext())
//            System.out.println(ilist.next().toString());
//    }
    
//    System.out.println("Valor del Value: " + raiz.getTextContent() + "\nPadre del Value: "+ raiz.getParentNode().getNodeName()+ " " + raiz.getParentNode().getAttributes().getNamedItem("Label"));
  }
  catch (Exception e) {
    	e.printStackTrace();
  }
 }

 protected String getHijos(Node n){
     String salida = n.getNodeName() + " " + n.getAttributes().getNamedItem("Label") + "\n";
     for(int s = 1; s < n.getChildNodes().getLength(); s++){
         if(n.getChildNodes().item(s) instanceof Element){
             salida += getAtributos(n.getChildNodes().item(s)) + "\n"; 
         }
     }
     return salida;
 }
 
 public ArrayList getHijosLista(Node n){
     ArrayList list = new ArrayList();
     list.add(n.getNodeName() + " " + n.getAttributes().getNamedItem("Name")
             + " " + n.getAttributes().getNamedItem("Label"));
//     list.add(getAtributos(n));
     for(int s = 1; s < n.getChildNodes().getLength(); s++){
//         if(!n.getChildNodes().item(s).getNodeName().equals("#text")){
         if(n.getChildNodes().item(s) instanceof Element){
             list.add(getAtributos(n.getChildNodes().item(s))); 
         }
     }
     return list;
 }
 
 public ArrayList getNodosHijos(Node n){
      ArrayList list = new ArrayList();
      //Caution
     for(int s = 0; s < n.getChildNodes().getLength(); s++){
         if(n.getChildNodes().item(s) instanceof Element){
             list.add(n.getChildNodes().item(s)); 
         }
     }
     return list;
 }
 
 protected String getAtributos(Node n){
     String salida = n.getNodeName() + " ";
     for (int f = 0 ; f < n.getAttributes().getLength(); f++){
            salida += n.getAttributes().item(f)+ " "; 
     }
     return salida;
 }
 
 protected ArrayList getAtributosLista(Node n){
     ArrayList list = new ArrayList();
     list.add(n.getNodeName());
     for (int f = 0 ; f < n.getAttributes().getLength(); f++){
            list.add(n.getAttributes().item(f)); 
     }
     return list;
 }
 
 public Node getNodeByName(String label,Node raiz){
    if(raiz.hasAttributes()){
        try{
        if(raiz.getAttributes().getLength() > 1 && raiz.getAttributes()
                .getNamedItem("Name").getNodeValue().equals(label)){
          return raiz;
        } 
        }catch(Exception e){
            
        }
     }
     NodeList hijos = raiz.getChildNodes();
     for(int a = 0; a < hijos.getLength(); a++){
         if(hijos.item(a) instanceof Element){
            Node aux = getNodeByName(label, hijos.item(a));
            if(aux != null && aux.hasAttributes() && aux.getAttributes().getLength() > 1 
                    && aux.getAttributes().getNamedItem("Name").getNodeValue().equals(label)){
                return aux;
            }
        }
     }
     return null;
 }
 
 protected Node getNodeByLabel(String label,Node raiz){
     if(raiz.hasAttributes()){
        if(raiz.getAttributes().getNamedItem("Label").getNodeValue().equals(label)){
          return raiz;
        } 
     }
     NodeList hijos = raiz.getChildNodes();
     for(int a = 0; a < hijos.getLength(); a++){
          if(hijos.item(a) instanceof Element){
            Node aux = getNodeByLabel(label, hijos.item(a));
            if(aux.hasAttributes() && aux.getAttributes().getNamedItem("Label").getNodeValue().equals(label)){
                return aux;
            }
        }
     }
     return raiz;
  }
  
  protected Node getNodeRecordByType(String label,Node raiz){
     if(raiz.hasAttributes()){
        if(raiz.getAttributes().getNamedItem("RecordTypeValue").getNodeValue().equals(label)){
          return raiz;
        } 
     }
     NodeList hijos = raiz.getChildNodes();
     for(int a = 0; a < hijos.getLength(); a++){
          if(hijos.item(a) instanceof Element){
            Node aux = getNodeRecordByType(label, hijos.item(a));
            if(aux.hasAttributes() && aux.getAttributes().getNamedItem("RecordTypeValue").getNodeValue().equals(label)){
                return aux;
            }
        }
     }
     return raiz;
  }
  
 protected ArrayList<Node> getNodeListByLabel(String label,Node raiz, ArrayList<Node> nodelist){
     if(raiz.hasAttributes()){
//        if(raiz.getAttributes().getNamedItem("Label").getNodeValue().equals(label)){
        if(raiz.getAttributes().getNamedItem("Label").getNodeValue().contains(label)){
          nodelist.add(raiz);
        } 
     }
     NodeList hijos = raiz.getChildNodes();
     for(int a = 0; a < hijos.getLength(); a++){
          if(hijos.item(a) instanceof Element){
            getNodeListByLabel(label, hijos.item(a), nodelist);
      }
     }
     return nodelist;
  }
  
 protected ArrayList<Node> getNodeListByName(String label,Node raiz, ArrayList<Node> nodelist){
     if(raiz.hasAttributes()){
//        if(raiz.getAttributes().getNamedItem("Label").getNodeValue().equals(label)){
        if(raiz.getAttributes().getNamedItem("Name").getNodeValue().contains(label)){
          nodelist.add(raiz);
        } 
     }
     NodeList hijos = raiz.getChildNodes();
     for(int a = 0; a < hijos.getLength(); a++){
//        if(!hijos.item(a).getNodeName().equals("#text")){
          if(hijos.item(a) instanceof Element){
            getNodeListByLabel(label, hijos.item(a), nodelist);
      }
     }
     return nodelist;
  }
    
 public ArrayList getAllNodeLabels(Node raiz,ArrayList list){
    if(raiz.hasAttributes()){
        try{
            list.add(raiz.getNodeName() + " " + 
                    raiz.getAttributes().getNamedItem("Label").getNodeValue() + " " + 
                    raiz.getAttributes().getNamedItem("Name").getNodeValue());
        }catch(Exception e){
            list.add(raiz.getNodeName() + " " + raiz.getAttributes().getNamedItem("Label").getNodeValue());
        }
    }
     NodeList hijos = raiz.getChildNodes();
     for(int a = 0; a < hijos.getLength(); a++){
        if(hijos.item(a) instanceof Element){
            getAllNodeLabels(hijos.item(a), list);
        }
     }
 return list;
 }
 
 public String getStart(Node e){
     try{
         return e.getAttributes().getNamedItem("Start").getNodeValue();
     }catch(Exception a){ 
         return "Nodo no tiene esta caracteristica.";
     }
 }
 
 public String getLen(Node e){
     try{
         return e.getAttributes().getNamedItem("Len").getNodeValue();
     }catch(Exception a){
         try{
            return e.getAttributes().getNamedItem("RecordLen").getNodeValue();   
         }catch(Exception i){
            return "Nodo no tiene esta caracteristica.";
         }
     }
 }
 
  public String getAtt(Node e, String att){
     try{
         return e.getAttributes().getNamedItem(att).getNodeValue().toString();
     }catch(Exception a){
         return "-";
     }
 }
  
 public JTree buildTree(Node doc) {
        
        return new JTree(build(doc));
   }

   public DefaultMutableTreeNode build(Node raiz) {
      DefaultMutableTreeNode result = new DefaultMutableTreeNode(raiz.getNodeValue());

      NodeList hijos = raiz.getChildNodes();
        for(int a = 0; a < hijos.getLength(); a++){
           if(hijos.item(a) instanceof Element){
               result.add(build(hijos.item(a)));
           }
        }
      return result;         
   }
 protected void InfoDicc(Document documento){
     
    NodeList Dicc = documento.getElementsByTagName("DICTIONARY");
    NodeList Level = documento.getElementsByTagName("LEVEL");
    NodeList IdItems = documento.getElementsByTagName("IDITEMS");
    NodeList Record = documento.getElementsByTagName("RECORD");
    NodeList Item = documento.getElementsByTagName("ITEM");
    NodeList ValueSet = documento.getElementsByTagName("VALUESET");
    NodeList Value = documento.getElementsByTagName("VALUE");
    
    int totalDiccionarios = Dicc.getLength();
    int totalLevels = Level.getLength();
    int totalIdItems = IdItems.getLength();
    int totalRecords = Record.getLength();
    int totalItems = Item.getLength();
    int totalValueSet = ValueSet.getLength();
    int totalValue = Value.getLength();
    
    System.out.println("Informacion de Diccionario");
    System.out.println("Número total de diccionarios : " + totalDiccionarios);
    System.out.println("Número total de levels : " + totalLevels);
    System.out.println("Número total de iditems : " + totalIdItems);
    System.out.println("Número total de records : " + totalRecords);
    System.out.println("Número total de items : " + totalItems);
    System.out.println("Número total de valueset : " + totalValueSet);
    System.out.println("Número total de value : " + totalValue);
 }
 
 public Node getRaiz(){
     NodeList Diccionario = documento.getElementsByTagName("LEVEL");
     return Diccionario.item(0);
 }
 
 //PRUEBAS PARA EL 25
  public ArrayList getRaizDic(){
      ArrayList b = new ArrayList();
     NodeList hijos = documento.getElementsByTagName("RECORD");
     for(int a = 0; a < hijos.getLength(); a++){
         if(hijos.item(a) instanceof Node){
           b.add(hijos.item(a));
        }
     }
//     InfoDicc(documento);
     return b;
 }
 
   public ArrayList getIdItemsDic(){
      ArrayList b = new ArrayList();
     NodeList hijos = documento.getElementsByTagName("IDITEMS");
     for(int a = 0; a < hijos.getLength(); a++){
         if(hijos.item(a) instanceof Node){    
             ArrayList hijos2 = getNodosHijos(hijos.item(a));
           for(int c = 0; c < hijos2.size(); c++){
                if(hijos2.get(c) instanceof Node){      
                  b.add(hijos2.get(c));
               }
            }
        }
     }
//     InfoDicc(documento);
     return b;
 }
   
  public Node getIdItems(){
     NodeList Diccionario = documento.getElementsByTagName("IDITEMS");
     return Diccionario.item(0);
 }

    public String getVal(Node aux2) {
        try{
         return aux2.getTextContent();
     }catch(Exception a){
         return "-";
     }
    }
     public Node getNodeByNameAndRecords(String label,Node raiz){
        // System.out.println(raiz.getNodeName());
    if(raiz.hasAttributes()){
        try{
        if(raiz.getAttributes().getLength() > 1 && raiz.getAttributes()
                .getNamedItem("Name").getNodeValue().equals(label)){
          return raiz;
        }else if (raiz.getNodeName().equals("RECORD")){
            records+=1;
        }
        }catch(Exception e){
            
        }
     }
     NodeList hijos = raiz.getChildNodes();
     for(int a = 0; a < hijos.getLength(); a++){
         if(hijos.item(a) instanceof Element){
            Node aux = getNodeByNameAndRecords(label, hijos.item(a));
            if(aux != null && aux.hasAttributes() && aux.getAttributes().getLength() > 1 
                    && aux.getAttributes().getNamedItem("Name").getNodeValue().equals(label)){
                return aux;
            }else if (aux != null && aux.getNodeName().equals("RECORD")){
                records+=1;
            }
        }
     }
     return null;
 }
     
   public Node getAllRecords(Node raiz){
        // System.out.println(raiz.getNodeName());
    if(raiz.hasAttributes()){
        try{
        if (raiz.getNodeName().equals("RECORD")){
            recordsTotales+=1;
        }
        }catch(Exception e){
            
        }
     }
     NodeList hijos = raiz.getChildNodes();
     for(int a = 0; a < hijos.getLength(); a++){
         if(hijos.item(a) instanceof Element){
            Node aux = getAllRecords(hijos.item(a));
             if (aux != null && aux.getNodeName().equals("RECORD")){
                recordsTotales+=1;
            }
        }
     }
     return null;
 }
   public ArrayList<Node> getAllNodeRecords(Node raiz, ArrayList<Node> record){
        // System.out.println(raiz.getNodeName());
    if(raiz.hasAttributes()){
        try{
        if (raiz.getNodeName().equals("RECORD")){
            record.add(raiz);
        }
        }catch(Exception e){
            
        }
     }
     NodeList hijos = raiz.getChildNodes();
     for(int a = 0; a < hijos.getLength(); a++){
         if(hijos.item(a) instanceof Element){
            Node aux = getAllRecords(hijos.item(a));
             if (aux != null && aux.getNodeName().equals("RECORD")){
                record.add(aux);
            }
        }
     }
     return record;
 }   
   
   public ArrayList<Node> ARECORD(){
       ArrayList<Node> a = new ArrayList<Node>();
              a = getAllNodeRecords(getRaiz(), a);
       return a;
   }
   
     public int getRecords(){ return records;}
     public int getRecordsTotales(Node nodo){ getAllRecords(nodo);return recordsTotales;}
     
     public String getLabelByItemValue(Node item, String a){
     String[] b;
         
     if(item.hasChildNodes()){
         ArrayList hijos = getNodosHijos(item);
         if(!hijos.isEmpty()){
             Node aux = (Node)hijos.get(0);
            if(aux.hasChildNodes()){
                ArrayList valueset = getNodosHijos(aux);
                Iterator ivalueset = valueset.iterator();
                while(ivalueset.hasNext()){                    
                    Node e = (Node) ivalueset.next();
                    if(esNumero(a) && e.getTextContent().contains(":")){
                         b = e.getTextContent().split(":");
                         if(b[0].trim().contains(".") && b[1].trim().contains(".")){
                             int z = a.length()/2;
                             a = a.substring(0,z)+"."+a.substring(z,a.length());
                             if(Float.valueOf(a.trim()) >= Float.valueOf(b[0].trim()) &&
                                 Float.valueOf(a.trim()) <= Float.valueOf(b[1].trim())){
                                 return e.getAttributes().getNamedItem("Label").getNodeValue();
                            }
                         }else
                         if(Integer.valueOf(a.trim()) >= Integer.valueOf(b[0].trim()) &&
                                 Integer.valueOf(a.trim()) <= Integer.valueOf(b[1].trim())){
                                 return e.getAttributes().getNamedItem("Label").getNodeValue();
                         }else{
                             return "numero invalido; no esta dentro del rango permitido";
                         }
                         
                         
                    }else if(e.getTextContent().equals(a)){
                            return e.getAttributes().getNamedItem("Label").getNodeValue();
                    }else if(esNumero(a) && esNumero(e.getTextContent())){
                            if(a.equals("0" +e.getTextContent()))
                                return e.getAttributes().getNamedItem("Label").getNodeValue();
                    }
                }
            }
         }
     }
     
     return "!";
     }

    private boolean esNumero(String a) {
        try{
            Integer.valueOf(a);
            return true;
        }catch(Exception e){
            return false;
        }
    }

     public ArrayList getItemsDic(){
      ArrayList b = new ArrayList();
     NodeList hijos = documento.getElementsByTagName("RECORD");
     for(int a = 0; a < hijos.getLength(); a++){
         if(hijos.item(a) instanceof Node){    
             ArrayList hijos2 = getNodosHijos(hijos.item(a));
           for(int c = 0; c < hijos2.size(); c++){
                if(hijos2.get(c) instanceof Node){      
                  b.add(hijos2.get(c));
               }
            }
        }
     }
//     InfoDicc(documento);
     return b;
 } 
}
