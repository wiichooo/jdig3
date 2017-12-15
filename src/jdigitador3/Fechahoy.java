/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdigitador3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author LUIS
 */
public class Fechahoy {

    String damefecha() {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateNow = formatter.format(currentDate.getTime());
        return dateNow;

    }

    String damehora() {
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        return formatter2.format(new Date());
    }
    
    public String dafeFechaHora() {
        String res = damefecha();
        res = res.concat(" ");
        res = res.concat(damehora());
        return res; 
    }
    
    public static String dameFechaArchivo() {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateNow = formatter.format(currentDate.getTime());
        return dateNow;
    }
    
    public static String dameFechaHoraArchivo() {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmm");
        String dateNow = formatter.format(currentDate.getTime());
        return dateNow;
    }
    
    public static Date convertirFecha(String fecha) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date fec = null;
        fec = formatter.parse(fecha);
        return fec;
    }

}
