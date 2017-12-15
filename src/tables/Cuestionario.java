/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

/**
 *
 * @author Luis
 */
public class Cuestionario {

    private int no_cuestinario;
    private int tipo_cuestionario;
    private int resultado;
    private int linea_persona;
    private String valido;
    private int hogar;
  //Agrega ENSMI
    private int codSupervisor;
    private int codEncuestador;
    private int codEditor;
    private String Stipo_cuestionario;
    private String Sresultado;

    public Cuestionario(int no_cuestinario, int tipo_cuestionario, int resultado,
            int linea_persona) {

        this.no_cuestinario = no_cuestinario;
        this.tipo_cuestionario = tipo_cuestionario;
        this.resultado = resultado;
        this.linea_persona= linea_persona;
        this.valido = "";
        
    }
    
    public Cuestionario(int no_cuestinario, int tipo_cuestionario, int resultado,
            int linea_persona, String valido) {

        this.no_cuestinario = no_cuestinario;
        this.tipo_cuestionario = tipo_cuestionario;
        this.resultado = resultado;
        this.linea_persona= linea_persona;
        this.valido = valido;   
    }
    
    public Cuestionario(int no_cuestinario, int tipo_cuestionario, int resultado,
            int linea_persona, String valido, int hogar) {

        this.no_cuestinario = no_cuestinario;
        this.tipo_cuestionario = tipo_cuestionario;
        this.resultado = resultado;
        this.linea_persona= linea_persona;
        this.valido = valido;
        this.hogar = hogar;   
    }
    //ENSMI
    public Cuestionario(int no_cuestinario, int tipo_cuestionario, int resultado,
            int linea_persona, int codSup, int codEnc, int codEdit) {

        this.no_cuestinario = no_cuestinario;
        this.tipo_cuestionario = tipo_cuestionario;
        this.resultado = resultado;
        this.linea_persona= linea_persona;
        this.valido = "";
        
        this.codEditor = codEdit;
        this.codEncuestador = codEnc;
        this.codSupervisor = codSup;
        
        this.Sresultado = "";
        this.Stipo_cuestionario = "";
    }
    
    public Cuestionario(int no_cuestinario, int tipo_cuestionario, int resultado,
            int linea_persona, String valido, int hogar, int codSup, int codEnc, int codEdit) {

        this.no_cuestinario = no_cuestinario;
        this.tipo_cuestionario = tipo_cuestionario;
        this.resultado = resultado;
        this.linea_persona= linea_persona;
        this.valido = valido;
        this.hogar = hogar;   
        
        this.codEditor = codEdit;
        this.codEncuestador = codEnc;
        this.codSupervisor = codSup;
    }
        
    public int getno_cuestinario() {
        return no_cuestinario;
    }

    public void setno_cuestinario(int no_cuestinario) {
        this.no_cuestinario = no_cuestinario;
    }

    public int getresultado() {
        return resultado;
    }

    public void setresultado(int resultado) {
        this.resultado = resultado;
    }

    public int gettipo_cuestionario() {
        return tipo_cuestionario;
    }

    public void settipo_cuestionario(int tipo_cuestionario) {
        this.tipo_cuestionario = tipo_cuestionario;
    }

    public int getlinea_pesona() {
        return linea_persona;
    }

    public void setlinea_pesona(int linea_persona) {
        this.linea_persona = linea_persona;
    }

    public String getvalido() {
        return valido;
    }
    
    public void setvalido(String valido) {
        this.valido = valido;
    } 
    
    public int gethogar() {
        return hogar;
    }
    
    public void sethogar(int hogar) {
        this.hogar = hogar;
    } 
    
    //AGREGADO
    public int getCodSupervisor() {
        return codSupervisor;
    }

    public void setCodSupervisor(int codSupervisor) {
        this.codSupervisor = codSupervisor;
    }

    public int getCodEncuestador() {
        return codEncuestador;
    }

    public void setCodEncuestador(int codEncuestador) {
        this.codEncuestador = codEncuestador;
    }

    public int getCodEditor() {
        return codEditor;
    }

    public void setCodEditor(int codEditor) {
        this.codEditor = codEditor;
    }

    public String getStipo_cuestionario() {
        return Stipo_cuestionario;
    }

    public void setStipo_cuestionario(String Stipo_cuestionario) {
        this.Stipo_cuestionario = Stipo_cuestionario;
    }

    public String getSresultado() {
        return Sresultado;
    }

    public void setSresultado(String Sresultado) {
        this.Sresultado = Sresultado;
    }
    
}
