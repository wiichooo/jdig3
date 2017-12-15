/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

/**
 *
 * @author Luis
 */
public class Paquete {

  
    private int no_paquete;  
    private String fecha; 
    private String tiempo;
    private String estado;
    private String tipo_paquete;
    private int tipo_paquete_id;
   

    public Paquete( int no_paquete, String fecha,String tiempo, String estado, String tipo_paquete, int tipo_paquete_id) {


        this.no_paquete = no_paquete;
        this.tipo_paquete = tipo_paquete;
        this.tipo_paquete_id = tipo_paquete_id;
        this.fecha = fecha;  
        this.tiempo = tiempo;  
        this.estado = estado;  
    
        
    }

 

    public int getno_paquete() {
        return no_paquete;
    }

    public void setno_paquete(int no_paquete) {
        this.no_paquete = no_paquete;
    }

    public String getfecha() {
        return fecha;
    }

    public void setfecha(String fecha) {
        this.fecha = fecha;
    }

   public String gettiempo() {
        return tiempo;
    }

    public void settiempo(String tiempo) {
        this.tiempo = tiempo;
    }
 
    public String getestado() {
        return estado;
    }

    public void setestado(String estado) {
        this.estado = estado;
    }

    public String getTipo_paquete() {
        return tipo_paquete;
    }

    public void setTipo_paquete(String tipo_paquete) {
        this.tipo_paquete = tipo_paquete;
    }

    public int getTipo_paquete_id() {
        return tipo_paquete_id;
    }

    public void setTipo_paquete_id(int tipo_paquete_id) {
        this.tipo_paquete_id = tipo_paquete_id;
    }
 
}
