/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

/**
 *
 * @author Luis
 */
public class Hogar {

    private int no_hogar;
    private int no_cuestionarios;
    private int total_miembros;
    private int total_mujeres;
    private int no_ninos;
    private String val;
    private int asociado;
    private int empoderamiento;
    //ENSMI
    private int total_hombres;
    
    public Hogar(int no_hogar, int no_cuestionarios, int total_miembros,
            int total_mujeres, int no_ninos, String val) {

        this.no_hogar = no_hogar;
        this.no_cuestionarios = no_cuestionarios;
        this.total_miembros = total_miembros;
        this.total_mujeres= total_mujeres;
        this.no_ninos = no_ninos;
        this.val = val;

    }
    public Hogar(int no_hogar, int no_cuestionarios, int total_miembros,
            int total_mujeres, int no_ninos) {

        this.no_hogar = no_hogar;
        this.no_cuestionarios = no_cuestionarios;
        this.total_miembros = total_miembros;
        this.total_mujeres= total_mujeres;
        this.no_ninos = no_ninos;
        this.val = "";
       

    }
    public Hogar(int no_hogar, int no_cuestionarios, int total_miembros,
            int total_mujeres, int no_ninos, int empoderamiento, int asociado) {

        this.no_hogar = no_hogar;
        this.no_cuestionarios = no_cuestionarios;
        this.total_miembros = total_miembros;
        this.total_mujeres= total_mujeres;
        this.no_ninos = no_ninos;
        this.val = "";
        this.empoderamiento = empoderamiento;
        this.asociado = asociado;

    }
     public Hogar(int no_hogar, int no_cuestionarios, int total_miembros,
            int total_mujeres, int no_ninos, int empoderamiento, int asociado, int total_hombres) {

        this.no_hogar = no_hogar;
        this.no_cuestionarios = no_cuestionarios;
        this.total_miembros = total_miembros;
        this.total_mujeres= total_mujeres;
        this.no_ninos = no_ninos;
        this.val = "";
        this.empoderamiento = empoderamiento;
        this.asociado = asociado;
        this.total_hombres = total_hombres;
    }
    
//    public Hogar(int no_hogar, int no_cuestionarios, int total_miembros,
//            int total_mujeres, int no_ninos, int no_C_Hogar, int no_C_Mujer, int no_C_Emp) {
//
//        this.no_hogar = no_hogar;
//        this.no_cuestionarios = no_cuestionarios;
//        this.total_miembros = total_miembros;
//        this.total_mujeres= total_mujeres;
//        this.no_ninos = no_ninos;
//        this.val = "";
//        this.no_c_hogar = no_C_Hogar;
//        this.no_c_mujer = no_C_Mujer;
//        this.no_c_emp = no_C_Emp;
//
//    }
    
    public int getno_hogar() {
        return no_hogar;
    }

    public void setno_hogar(int no_hogar) {
        this.no_hogar = no_hogar;
    }

    public int getno_cuestionarios() {
        return no_cuestionarios;
    }

    public void setno_cuestionarios(int no_cuestionarios) {
        this.no_cuestionarios = no_cuestionarios;
    }

    public int gettotal_miembros() {
        return total_miembros;
    }

    public void settotal_miembros(int total_miembros) {
        this.total_miembros = total_miembros;
    }

    public int gettotal_mujeres() {
        return total_mujeres;
    }

    public void settotal_mujeres(int total_mujeres) {
        this.total_mujeres = total_mujeres;
    }

    public int getno_ninos() {
        return no_ninos;
    }

    public void setno_ninos(int no_ninos) {
        this.no_ninos = no_ninos;
    }
    public String getval_hogar() {
        return val;
    }

    public void setval_hogar(String val) {
        this.val = val;
    }
    public int getasociado() {
        return asociado;
    }
    public int getempoderamiento() {
        return empoderamiento;
    }

    public int getTotal_hombres() {
        return total_hombres;
    }

    public void setTotal_hombres(int total_hombres) {
        this.total_hombres = total_hombres;
    }
    
    
}
