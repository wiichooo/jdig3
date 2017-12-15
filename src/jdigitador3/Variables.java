/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jdigitador3;

/**
 *
 * @author LUIS
 */
public class Variables {
    
    //GENERAL
    public static String encuesta = "8"; //Valor de la base de datos
    static int Grupo_Digitadores = 3; //Valor registrado en la base de datos
    public static int Tipo_Paquete = 1;
    public static String Prefijo_Tipo_Paquete = "PK";//Hogar; "CS":Comunidad y Servicio
    public static String Prefijo_Tipo_Paquete_Hogar = "PK";
    public static String Prefijo_Tipo_Paquete_CS = "CS";
    public static int Paquete_Hogar = 1;
    public static int Paquete_CS = 2;
    
    public static boolean Paquete_Correcto = false;
    
    public static String EntryHogar = "EMEPAO.ent";
    public static String EntryCyS = "Comunidad.ent";
    
    //EMEPAO
//    public final static int C_HOGAR = 9;
//    public final static int C_GASTOS =11;
//    public final static int C_MUJER = 10;
//    public final static int C_EMPODERAMIENTO = 12;
//    public final static int C_ANTRO = 13;
//    public final static int C_COMUNIDAD = 15;
//    public final static int C_SERVICIOS = 14;
//    public final static int C_HOMBRE = 16;
    
    //ENCUESTA 8
    public final static int C_HOGAR = 21;
    public final static int C_GASTOS =99;
    public final static int C_MUJER = 22;
    public final static int C_EMPODERAMIENTO = 98;
    public final static int C_ANTRO = 97;
    public final static int C_COMUNIDAD = 24;
    public final static int C_SERVICIOS = 25;
    public final static int C_HOMBRE = 23;
    
    /* ESTADOS DE LOS PAQUETES*/
    static String EnD18 = "En Digitación 1";       static int EnDig1 = 9;
    static String PD19 = "Pausa Digitación 1";     static int PDig1 = 10;
    static String D110 = "Digitado 1";             static int Dig1 = 11;
    static String EnD211 = "En Digitación 2";      static int EnDig2 = 12;
    static String PD212 = "Pausa Digitación 2";    static int PDig2 = 13;
    static String D213 = "Digitado 2";             static int Dig2 = 14;
    static String EnV14 = "En Verificación";       static int EnVer = 15;
    static String PV15 = "Pausa Verificación";     static int PVer = 16;
    static String V16 = "Verificado";              static int Ver = 17;
    //Localizacion Archivos (Localmente y en la raiz del servidor FTP)
    static String Primera_Digitacion = "PRIMERA DIGITACION";
    static String Segunda_Digitacion = "SEGUNDA DIGITACION";
    static String Verificacion = "VERIFICACION";
    static String Diccionario = "DICCIONARIO";
    
    //Errores
    static int Error_BD = 1;           static int Error_FTP = 2;          static int Cambio_Correcto = 3;
    static int Error_Cambio = 4;       static int Archivo_No_Creado = 5;  static int No_Hay_Ver = 6;
    static int No_Se_Copio_al_FTP = 7; static int Archivo_Descargado = 8; static int Archivo_Creado = 9; 
    static int Fin_D1 = 10;            static int Fin_D2 = 11;            static int Fin_Ver = 12;   
    static int No_Ver = 13;            static int Error_check = 14;       static int No_Paquetes_Incorrecto = 15;
    static int Observacion_Correcta = 18;
    static int Error_Observacion = 19;
    static int tempo = 0;
    
    
    //VARIABLES HOGAR
    public static String PAQUETE = "HPAQUETE";
    public static String EMP = "HEMPOD";
    public static String ASO = "HASOCIA";
    //VERIFICAR HOGAR
    public static String Hogar = "HHOGAR"; 
    public static String Miembros = "HMEMBRS"; 
    public static String Mujeres = "HMUJERES"; 
    public static String Ninios = "HNINOS"; 
    public static String Hombres = "HHOMBRES";
    
    //VERIFICAR CUESTIONARIOS
    public static String No_Hogar = "HNUM"; 
    public static String No_Mujer = "MNUM";  
    public static String No_Emp = "ENUM";  
    public static String No_GyS = "GNUM";
    public static String No_Hombre = "HMNUM";
    
    public static String Resultado_Hogar = "HRES"; 
    public static String Resultado_Mujer = "MRES"; 
    public static String Resultado_Emp = "ERES";
    public static String Resultado_GyS = "GRES"; 
    public static String Resultado_Hombre = "HOMRES";
    
    public static String Hogar_linea = "HLINEA";
    public static String Persona_Hogar = "HNLINE";   
    public static String Persona_Mujer = "MLINEA"; 
    public static String Persona_Emp = "ELINEA"; 
    public static String Persona_GyS = "GNLINE_1";
    public static String Persona_Hombre = "HOMLINEA";
    
    //DATOS PERSONAS
    public static String HSupervisor_Campo = "HSUP";
    public static String HEditor_Campo = "HEDC";
    public static String HEditor_Oficina = "HEDO";
    public static String HDigitador_A = "HDIGA";
    public static String HDigitador_B = "HDIGB";
    public static String HEncuestador = "HENC";
    
    public static String MSupervisor_Campo = "MSUP";
    public static String MEditor_Campo = "MEDC";
    public static String MEditor_Oficina = "MEDO";
    public static String MDigitador_A = "MDIGA";
    public static String MDigitador_B = "MDIGB";
    public static String MEncuestador = "MENC";
    
    public static String ESupervisor_Campo = "ESUP";
    public static String EEditor_Campo = "EEDC";
    public static String EEditor_Oficina = "EEDO";
    public static String EDigitador_A = "EDIGA";
    public static String EDigitador_B = "EDIGB";
    public static String EEncuestador = "EENC";
    
    public static String GSupervisor_Campo = "GSUP";
    public static String GEditor_Campo = "GEDC";
    public static String GEditor_Oficina = "GEDO";
    public static String GDigitador_A = "GDIGA";
    public static String GDigitador_B = "GDIGB";
    public static String GEncuestador = "GENC";
    
    //Antropometrista
    public static String HAntropometrista = "AANTR";
    
    //COMUNIDAD Y SERVICIOS
    public static String CSPaquete = "Paquete";
    
    public static String No_Comunidad = "CNUMERO";
    public static String CCorrelativo = "CORRELATIVO";
    public static String Resultado_Comunidad = "CRES";
    public static String CSupervisor_Campo = "CSUP";
    public static String CEditor_Campo = "CEDC";
    public static String CEncuestador = "CENCA";
    
    public static String No_Servicios = "SCUESTIONARIO";
    public static String SCorrelativo = "CORRELATIVO";
    public static String Resultado_Servicios = "SPRESENT";
    public static String SSupervisor_Campo = "SPCODSUPERVISOR";
    public static String SEditor_Campo = "SPCODEDITOR";
    public static String SEncuestador = "SPENC1";
    
//    BD Collections
    public static String PDigitacion = "DigitacionDesarrollo"; //PDigitacionEMEPAO
    public static String SDigitacion = "DigitacionDesarrollo";
    public static String VDigitacion = "DigitacionDesarrollo";
    public static String ColHogar = "Hogar.Gastos";
    public static String ColMujer = "Mujer.Empoderamiento";
    
    public static String Ultimo_Verificado = "PP";
    
}
