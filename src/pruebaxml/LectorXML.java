/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebaxml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author Luis
 */
class LectorXML extends DefaultHandler {

    public final XMLReader xr;
    
    public LectorXML() throws SAXException {
        xr = XMLReaderFactory.createXMLReader();
        xr.setContentHandler(this);
        xr.setErrorHandler(this);
    }

    public void leer(final String archivoXML)
             throws FileNotFoundException, IOException,
                       SAXException {
        FileReader fr = new FileReader(archivoXML);
        xr.parse(new InputSource(fr));
        
    }

    @Override
    public void startDocument() {
        System.out.println("Comienzo del Documento XML");
    }

    @Override
    public void endDocument() {
        System.out.println("Final del Documento XML");
    }

    @Override
    public void startElement(String uri, String name,
              String qName, Attributes atts) {
////        System.out.println("tElemento: " + name);
//
//        for (int i = 0; i < atts.getLength(); i++) {
////         System.out.println("ttAtributo: " +
//          atts.getLocalName(i) + " = "+ atts.getValue(i));
//        }
//        
//      // También podemos obtener los atributos por nombre  
//      String valorId = atts.getValue("Label");  
//      if(valorId!=null){  
//         System.out.println("\tLabel: "+valorId);  
//      }  
    }

    @Override
    public void endElement(String uri, String name,
                                 String qName) {
//        System.out.println("tFin Elemento: " + name);
    }
}
