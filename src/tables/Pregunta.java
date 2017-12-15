/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Luis
 */
public class Pregunta implements Serializable{

    private String no_pregunta;
    private String pregunta;
    private String respuestaA;
    private String respuestaB;
    private String respuestaX;
    private String validado;
    private String start;
    private String len;
    private String no_cuestionario;
    int fila;
    int col;
    ArrayList valores;
    int Nopaquete;
    int Nohogar;
    int occ;
    
   public Pregunta(String no_pregunta, String pregunta, String respuestaA,
            String respuestaB, String respuestaX, String validado, String start, String len, int fila, int col,
            ArrayList valores, int nocc, String nocuestionario) {
        this.no_pregunta = no_pregunta;
        this.pregunta = pregunta;
        this.respuestaA = respuestaA;
        this.respuestaB= respuestaB;
        this.respuestaX=respuestaX;
        this.validado=validado;
        
        this.start = start;
        this.len = len;
        
        this.fila = fila;
        this.col = col;
        
        this.valores = valores;
        this.occ = nocc;
        this.no_cuestionario = nocuestionario;
    }
   
   public Pregunta(String no_pregunta, String pregunta, String respuestaA,
            String respuestaB, String respuestaX, String validado, String start, String len, int fila, int col,
            ArrayList valores, int Nopaquete, int Nohogar, int nocc, String nocuestionario) {

        this.no_pregunta = no_pregunta;
        this.pregunta = pregunta;
        this.respuestaA = respuestaA;
        this.respuestaB= respuestaB;
        this.respuestaX=respuestaX;
        this.validado=validado;
        
        this.start = start;
        this.len = len;
        
        this.fila = fila;
        this.col = col;
        
        this.valores = valores;
        
        this.Nohogar = Nohogar;
        this.Nopaquete = Nopaquete;
        this.occ = nocc;
        this.no_cuestionario = nocuestionario;
    }
   
   public Pregunta( int Nopaquete, int Nohogar, String nocuestionario, String pregunta,String occ, String respuestaA,
            String respuestaB, String respuestaX, String validado
             ) {

        super();
        this.pregunta = pregunta;
        this.respuestaA = respuestaA;
        this.respuestaB= respuestaB;
        this.respuestaX=respuestaX;
        this.validado=validado;
        this.occ = Integer.valueOf(occ);
        this.Nohogar = Nohogar;
        this.Nopaquete = Nopaquete;

        this.no_cuestionario = nocuestionario;
    }
   
     public String getno_pregunta() {
        return no_pregunta;
    }

    public void setno_pregunta(String no_pregunta) {
        this.no_pregunta = no_pregunta;
    }

    public String getpregunta() {
        return pregunta;
    }

    public void setpregunta(String pregunta) {
        this.pregunta = pregunta;
    }
    

   public String getrespuestaA() {
        return respuestaA;
    }

    public void setrespuestaA(String respuestaA) {
        this.respuestaA = respuestaA;
    }
    

   public String getrespuestaB() {
        return respuestaB;
    }

    public void setrespuestaB(String respuestaB) {
        this.respuestaB = respuestaB;
                    }
    
    
    
   public String getrespuestaX() {
        return respuestaX;
    }

    public void setrespuestaX(String respuestaX) {
        this.respuestaX = respuestaX;
    }
    
    
    
    
    public String getvalidado() {
        return validado;
    }

    public void setvalidado(String validado) {
        this.validado = validado;
    }

   public String getstart() {
        return start;
    }

    public void setstart(String start) {
        this.start = start;
    }
    
    public String getlen() {
        return len;
    }

    public void setlen(String len) {
        this.len = len;
    }
   
    public int getfila() {
        return fila;
    }

    public void setfila(int fila) {
        this.fila = fila;
    }
    
    public int getcol() {
        return col;
    }

    public void setcol(int col) {
        this.col = col;
    }
    
    public ArrayList getValores() {
        return valores;
    }

    public void setValores(ArrayList valores) {
        this.valores = valores;
    }
    
    public int getNohogar() {
        return Nohogar;
    }

    public void setNohogar(int hogar) {
        this.Nohogar = hogar;
    }
    
        public int getNopaquete() {
        return Nopaquete;
    }

    public void setNopaquete(int paquete) {
        this.Nopaquete = paquete;
    }
    public int getNocc() {
        return occ;
    }

    public void setNocc(int oc) {
        this.occ = oc;
    }
    
    public String getNocuestionario() {
        return no_cuestionario;
    }

    public void setNocuestionario(String nocuestionario) {
        this.no_cuestionario = nocuestionario;
    }
}
