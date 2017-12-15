/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdigitador3.Variables;
/**
 *
 * @author Luis
 */
public class DBClient {
 
   static String URL;
   static String USER;
   static String PASSWORD;
   static String DRIVER; 
   //encuesta = 1 PILOTO 2 CAPA 3 EMEPAO
   static String encuesta = Variables.encuesta;
   /* Crea una conexion con la base de datos. */
//    private int tipo_paquete = Variables.Tipo_Paquete;
 
   public Connection getConnection(String dir, String name, String user, String pass, String port) throws SQLException {
    Connection con = null;
    URL = "jdbc:mysql://"+dir+":"+port+"/"+name;
    USER = user;
    PASSWORD = pass;
    DRIVER = "com.mysql.jdbc.Driver";
      try {
         Class.forName(DRIVER); 
         con = DriverManager.getConnection(URL, USER, PASSWORD);
      }
      catch(ClassNotFoundException e) {
         System.out.println(e.getMessage() + "Error Conexion");
//         System.exit(-1);
      }
      return con;
   }
   //Obtener usuario 
   public ResultSet getUser(Connection con, String nombre) {
      ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         rs = s.executeQuery("select u.username, u.password, u.tipo_usuario, u.login, p.nombre1, p.apellido1, p.id from "
                 + "Persona as p, Usuario as u where p.id = u.persona and u.username = '" + nombre + "'");
         return rs;
      }       
      catch(SQLException e) {
         System.out.println(e.getMessage());
         return rs;
      }
   }
   //Cambiar el estado de la variable login
   public boolean setUserLogin(Connection con, String id, int grupo, int login) {
      try {
         Statement s = con.createStatement();
         String log = "update Usuario set login = "+login+" where username = '"+id+"' and tipo_usuario = "+grupo;
         s.execute(log);
         return true;
      }       
      catch(SQLException e) {
         System.out.println(e.getMessage());
          return false;
      }
   }
        
    public ResultSet getPaquetes(Connection con, long nombre) {
      ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         String query = "SELECT pk.numero, pk.fecha_viaje, epk.nombre, tp.nombre, tp.id, epk.id, pk.digitador1, pk.digitador2, pk.digitador3"
                 +" FROM Paquete as pk, Estado_Paquete epk, Tipo_Paquete tp"
                 +" where pk.estado_paquete = epk.id and tp.id = pk.tipo_paquete and pk.encuesta = "+ encuesta 
                 +" and (pk.digitador1 = "+nombre
                 +" or pk.digitador2 = "+nombre+" or pk.digitador3 = "+nombre+");";
         
         System.out.println(query);        
         rs = s.executeQuery(query);
         return rs;      
      }
      catch(SQLException e) {
         System.out.println(e.getMessage());
         return rs;
      }
   }
    
    public ResultSet getHogares(Connection con, String nombre) {
      ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         String query = "select distinct h.numero, h.cuestionarios, h.miembros, h.mujeres_elegibles, h.ninos"
                 + ", h.esEmpoderamiento, h.esAsociado, h.hombres_elegibles from Hogar as h, "
                 + "Paquete as pk where pk.numero = h.paquete and pk.encuesta = h.encuesta  and "
                 + "pk.encuesta = "+ encuesta +" and pk.tipo_paquete = h.tipo_paquete and h.tipo_paquete = "+Variables.Tipo_Paquete+""
                 + " and pk.numero = \"" + nombre+"\"";
         System.out.println(query);
         
         rs = s.executeQuery(query);
        
         return rs;
      }
      catch(SQLException e) {
         System.out.println(e.getMessage());
         return null;
      }
   }
    
    public ResultSet getCuestionarios(Connection con, String nombre, String nombre1) {
      ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         String query = "select distinct c.numero, c.tipo_cuestionario, r.resultado, c.persona, "
                 + "(select a.codigo from Asignatura a where a.persona = c.supervisor and a.encuesta = "+ encuesta +" and mostrar = 1) as supervisor,\n" +
                    "(select a.codigo from Asignatura a where a.persona = c.encuestador and a.encuesta = "+ encuesta +" and mostrar = 1) as encuestador,\n" +
                    "(select a.codigo from Asignatura a where a.persona = c.editora and a.encuesta = "+ encuesta +" and mostrar = 1) as editora  "
                  + "from Hogar as h ,Cuestionario as c, Paquete as pk, Resultado_Cuestionario r" +
                    " where pk.numero = h.paquete and h.numero = c.hogar and c.resultado = r.id and r.tipo_cuestionario = c.tipo_cuestionario and c.paquete = pk.numero and c.encuesta_cuestionario = "+ encuesta 
                  + " and c.tipo_paquete = "+Variables.Tipo_Paquete+" and pk.encuesta = "+ encuesta +" and pk.numero = \""+ nombre1+"\" and h.numero = "+nombre+";";
         System.out.println(query);
         
         rs = s.executeQuery(query);
         
         return rs;
      }
      catch(SQLException e) {
         System.out.println(e.getMessage());
         return null;
      }
   }
    
    public boolean registro(Connection con, String NoPaquete, Long IdUsuario, int estadoactual, int nuevoestado,String MD5Checksum){
        ResultSet rs = null;
        try {
        Statement s = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
        rs = s.executeQuery("Select pk.* from Paquete pk, Estado_Paquete spk "
                + "where pk.estado_paquete = spk.id "
                + "and pk.numero = \""+NoPaquete+"\" and spk.id = "+estadoactual+" and pk.encuesta = "+ encuesta +" "
                + "and pk.tipo_paquete = " + Variables.Tipo_Paquete + " and "
                + "(pk.digitador1 = "+IdUsuario+" or pk.digitador2 = "+IdUsuario+" or pk.digitador3 = "+IdUsuario+")");
                  
        
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        if(rs.next()){      
         String insertDP = "insert into Detalle_Paquete (paquete,encuesta, estado_paquete,ubicacion, tipo_paquete, fecha) values ("
                + rs.getObject(1) + "," + rs.getObject(2) + ","
                + nuevoestado + "," + ifNull(rs.getObject(12)) + "," +rs.getObject(24)+",'"+sdf.format(dt)+"')";

         
        String update = "update Paquete as pk, Estado_Paquete spk set pk.estado_paquete = "+nuevoestado+" where" 
                + " pk.estado_paquete = spk.id and pk.encuesta = "+ encuesta +" and tipo_paquete = " +rs.getObject(24)
                + " and pk.numero = \""+NoPaquete+"\" and spk.id = "+estadoactual+" and"
                + " (pk.digitador1 = "+IdUsuario+" or pk.digitador2 = "+IdUsuario+" or pk.digitador3 = "+IdUsuario+")";
        System.out.println(insertDP);
        System.out.println(update);
        s.execute(insertDP);
        s.execute(update);
        return true;
        }
        } catch (SQLException ex) {
           Logger.getLogger(DBClient.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error SQL NO se encontro registro!!");
            return false;
        }
        return false;
    }
 
    public ResultSet getPaqueteVer(Connection con, long nombre, String paquete) {
      ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         rs = s.executeQuery("SELECT pk.digitador1 as d1, pk.digitador2 as d2, pk.digitador3 as d3, checksum_dig1 as cd1, checksum_dig2 as cd2 "
                 + "FROM Paquete as pk, Estado_Paquete epk"
                +" where pk.estado_paquete = epk.id and pk.numero = \""+paquete+"\" and pk.encuesta = "+ encuesta +" "
                 + "and pk.tipo_paquete = "+Variables.Tipo_Paquete+" and pk.digitador3 = "+nombre+";");
         
         return rs;      
      }
      catch(SQLException e) {
        System.out.println(e.getMessage());
         return rs;
      }
   }
        
    public String ifNull(Object a){
    if(a == null) {
            return null;
        }
    else {
            return "'"+a+"'";
        }
    }
    
    public String Checksum(ResultSet rs, int nuevoestado, String CheckSum){
        try {
            //Depende del nuevo estado; Si los valores de los estados cambian, hay que cambiarlo aqui.
            if(CheckSum.equals("")) {
                return ifNull(rs.getObject(22))+"," + ifNull(rs.getObject(23))+"," + ifNull(rs.getObject(24));
            }else if(!CheckSum.equals("") && nuevoestado == 11){
                return "'" + CheckSum +"'," + ifNull(rs.getObject(23))+"," + ifNull(rs.getObject(24));
            }else if(!CheckSum.equals("") && nuevoestado == 14){
                return ifNull(rs.getObject(22))+",'" + CheckSum +"'," + ifNull(rs.getObject(24));
            }else if(!CheckSum.equals("") && nuevoestado == 17){
                return ifNull(rs.getObject(22))+"," + ifNull(rs.getObject(23))+",'" + CheckSum + "'";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public int getNoHogares(Connection con, int numero) {
      ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         rs = s.executeQuery("SELECT pk.hogares FROM Paquete as pk"
                +" where pk.tipo_paquete = "+Variables.Tipo_Paquete+" and pk.encuesta = "+ encuesta +" and pk.numero = \""+numero+"\";");
         rs.next();
         return rs.getInt(1);      
      }
      catch(SQLException e) {
//         System.out.println(e.getMessage());
         return 0;
      }
   }
      
    public ResultSet getALLCuestio(Connection con, int nopk){
    ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         String query = "SELECT distinct c.numero, c.tipo_cuestionario, r.resultado, c.persona, c.hogar, " 
                 + "(select a.codigo from Asignatura a where a.persona = c.supervisor and a.encuesta = "+ encuesta +" and mostrar = 1) as supervisor,\n" +
                    "(select a.codigo from Asignatura a where a.persona = c.encuestador and a.encuesta = "+ encuesta +" and mostrar = 1) as encuestador,\n" +
                    "(select a.codigo from Asignatura a where a.persona = c.editora and a.encuesta = "+ encuesta +" and mostrar = 1) as editora  " +
                        "FROM Cuestionario c, Resultado_Cuestionario r " +
                        "where c.resultado = r.id and r.tipo_cuestionario = c.tipo_cuestionario and c.tipo_paquete = "+Variables.Tipo_Paquete + " "+
                        "and c.encuesta = "+encuesta+" and c.encuesta_cuestionario = "+encuesta+" and c.paquete = \""+nopk+"\";";                
         rs = s.executeQuery(query);
         
         return rs;
      }
      catch(SQLException e) {
//         System.out.println(e.getMessage());
         return null;
      }
    
    }
    
    public boolean inResDigitacion(Connection con, int digitador, int paquete, int digitacion, int errores){
        String insert = "insert into resultado_digitador(id_digitador, paquete, digitacion, errores) values ("
                + digitador   + "," + paquete + "," + digitacion  + "," + errores + ")";
        try {
            Statement s = con.createStatement();
            s.execute(insert);
            return true;
        }catch(Exception e){ return false;}
    
    }

    public String getTimeSpend(Connection con, int nopk, int estado1, int estado2, int estado3) throws ParseException{
    ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         String query = "select time(fecha), estado_paquete from Detalle_Paquete where paquete = \""+nopk+"\" and encuesta = " + encuesta
                 + " and (estado_paquete = "+estado1+" or estado_paquete = "+estado2+" or estado_paquete = "+estado3+");";
         
         System.out.println(query);
         rs = s.executeQuery(query);
         
         SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss"); 
         long tiempo = 0;
         long tiempotmp = 0;
         Date utilDate;

         boolean is17 = false;
         long tmp = 0;
         long tmp2 = 0;
         long tmp3 = 0;
         while(rs.next()) {
            Time id = rs.getTime("time(fecha)");
            int estado = rs.getInt("estado_paquete");
            utilDate = new java.util.Date(id.getTime());
            if(estado == 9 ||estado == 12 || estado == 15 ) {
                 tmp = id.getTime();
             }else if((estado == 10 || estado == 13 || estado == 16) && tmp != 0){
                tmp2 = id.getTime();
                tiempotmp = tmp2 - tmp;
                tiempo += tmp2 - tmp;
                tmp = 0;
            }
             else if((estado == 11 || estado == 14 || estado == 17) && tmp != 0 && is17 == false){
                tmp3 = id.getTime();
                tiempo += tmp3 - tmp;
                tmp2 = 0;
                if(estado == 17 ){
                    is17 = true;
                }
            }
         }
//         int segundos = (int) (tiempo / (1000 % 60));  
//         int minutos = (int) ((tiempo / (60 * 1000)) % 60);
//         int horas = (int) ((tiempo / (60 * 60 * 1000)) % 24); 

         long segundos = tiempo / 1000;

           //obtenemos las horas
         long horas = segundos / 3600;

           //restamos las horas para continuar con minutos
         segundos -= horas*3600;

           //igual que el paso anterior
         long minutos = segundos /60;
         segundos -= minutos*60;        

         String h = String.valueOf(horas);
         String m = String.valueOf(minutos);
         String sg = String.valueOf(segundos);
         if(h.length()<2) {
              h = "0" + String.valueOf(horas);
          }
         if(m.length()<2) {
              m = "0" + String.valueOf(minutos);
          }
         if(sg.length()<2) {
              sg = "0" + String.valueOf(segundos);
          }
         
         String t = h + ":" + m + ":" + sg.substring(0, 2);
         return t;
      }
      catch(SQLException e) {
//         System.out.println(e.getMessage());
         return null;
      }
    
    }

/*Agregado para obtener datos de las personas.*/
    public ResultSet PersonaByCod(Connection con, String cod){
      ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         rs = s.executeQuery("select asig.codigo, pr.nombre1,pr.apellido1, p.nombre, pr.telefono, pr.telefono " +
            "from Asignatura asig, Persona pr, Puesto p " +
            "where asig.puesto = p.id and asig.persona = pr.id and asig.codigo =" +cod+ " and asig.encuesta =" +encuesta);
         return rs;
      }
      catch(SQLException e) {
//         System.out.println(e.getMessage());
         return null;
      }
    }
    
 public ResultSet GrupoByCod(Connection con, String cod){
      ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         rs = s.executeQuery("select e.numero, ep.grupo, pr.id, pr.nombre1,pr.apellido1 " +
        "from Persona pr, Grupo_Persona ep, Grupo e " +
        "where pr.id = ep.persona " +
        "and e.id = ep.grupo and pr.id ="+cod+" and e.encuesta = "+encuesta);
         return rs;
      }
      catch(SQLException e) {
//         System.out.println(e.getMessage());
         return null;
      }
    }

    public ResultSet ComisionByPaquete(Connection con, String pk){
        ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         rs = s.executeQuery("SELECT v.comision, com.nombre, p.numero, p.viaje, com.fecha_inicio, com.fecha_fin " +
            "FROM Viaje v, Comision com, Paquete p " +
            "where v.comision = com.id and v.id = p.viaje and com.encuesta = "+encuesta+" and"
                 + " p.numero =\"" +pk + "\" and p.tipo_paquete = "+Variables.Tipo_Paquete);
         return rs;
      }
      catch(SQLException e) {
//         System.out.println(e.getMessage());
         return null;
      }
    }
    
    public int idDig1ByPaquete(Connection con, String pk){
        ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         rs = s.executeQuery("select pk.numero, pk.digitador1 from Persona p, Paquete pk "+
        "where pk.digitador1 = p.id and pk.encuesta = "+encuesta+" and pk.numero =\"" +pk+"\" and p.tipo_paquete = "+Variables.Tipo_Paquete);
         rs.next();
         return rs.getInt(2);    
      }
      catch(SQLException e) {
//         System.out.println(e.getMessage());
         return 0;
      }
    }
    
    public int idDig2ByPaquete(Connection con, String pk){
        ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         rs = s.executeQuery("select pk.numero, pk.digitador2 from Persona p, Paquete pk "+
        "where pk.digitador2 = p.id and pk.encuesta = "+encuesta+" and pk.numero =\"" +pk+"\" and p.tipo_paquete = "+Variables.Tipo_Paquete);
         rs.next();
         return rs.getInt(2);    
      }
      catch(SQLException e) {
//         System.out.println(e.getMessage());
         return 0;
      }
    }
    public boolean insertProg(Connection con, int paquete, int hogares){
        String insert = "insert into Progreso(paquete, hogar, fecha) values ("+ paquete + "," + hogares  + ", now())";
        try {
            Statement s = con.createStatement();
            s.execute(insert);
            return true;
        }catch(Exception e){ 
            return false;
        }
    }
    public ResultSet GrupoByPaquete(Connection con, String cod){
      ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         rs = s.executeQuery(""
                 + "SELECT "
                 + " e.numero AS grupo"
                 + " FROM Grupo e,  Viaje v, Paquete p"
                 + " WHERE e.id = v.grupo"
                 + " AND v.id = p.viaje"
                 + " AND p.encuesta = "+encuesta
                 + " AND p.tipo_paquete ="+Variables.Tipo_Paquete
                 + " AND p.estado_paquete = 4"
                 + " AND v.comision = 5"
                 + " AND p.numero = \""+cod+"\"");    
         return rs;
      }
      catch(SQLException e) {
         System.out.println(e.getMessage());
         return null;
      }
    }
    
    public ResultSet getcategorias(Connection con){
      ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         rs = s.executeQuery("SELECT * FROM Inconsistencia where encuesta = "+encuesta+" order by id;");
         return rs;
      }
      catch(SQLException e) {
         System.out.println(e.getMessage());
         return null;
      }
   }

   public int getcategoriaID(Connection con, String nombre){
      ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         rs = s.executeQuery("Select id from Inconsistencia where nombre =  \"" + nombre + "\"" );
         rs.next();
         return rs.getInt(1);
      }
      catch(SQLException e) {
         System.out.println(e.getMessage());
         return 0;
      }
    }
   
   public boolean insertarOb(Connection con, int paquete, String tipo_paquete, int hogar, int cuestionario, int digitador, String pregunta
            , int prioridad, String Desc, String tipo, int cat){
        
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String insert = "insert into Observacion(paquete,tipo_paquete,hogar,cuestionario,digitador,"
                + "fecha,pregunta,prioridad,descripcion,tipo_cuestionario,categoria, encuesta) "
                + "values ("+ paquete + "," + "(Select id from Tipo_Paquete where nombre = \""+tipo_paquete+"\")" + ","+ hogar  + "," + cuestionario + "," + digitador
                + ",'" + sdf.format(dt)+ "','" + pregunta + "'," + prioridad + ",'" + Desc + "'," + tipo+","+cat+"," +encuesta +")";
        try {
            System.out.println(insert);
            Statement s = con.createStatement();
            s.execute(insert);
            return true;
        }catch(Exception e){ return false;}
    }
   
   public boolean insertarObCu(Connection con, int cuestionario, int digitador, String pregunta
            , int prioridad, String Desc, String tipo, int cat, String tipoObs){
        
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String insert = "insert into Observacion(cuestionario,digitador,"
                + "fecha,pregunta,prioridad,descripcion,tipo_cuestionario,categoria, encuesta,tipo) "
                + "values (" +"(select numero from Cuestionario where numero like \""+ini(tipo)+"%0" +cuestionario +"\" and encuesta = " +encuesta +" and encuesta_cuestionario = " +encuesta +")" 
                + "," + digitador
                + ",'" + sdf.format(dt)+ "','" + pregunta + "'," + prioridad + ",'" + Desc + "'," + tipo+","+cat+"," +encuesta +",'"+tipoObs+"')";
        try {
            System.out.println(insert);
            Statement s = con.createStatement();
            s.execute(insert);
            return true;
        }catch(Exception e){ return false;}
    }
    public String getPrimerDigitador(Connection con, String paquete, int tipo_paquete){
      ResultSet rs = null;
      try {
         Statement s = con.createStatement();
         rs = s.executeQuery("Select digitador1 from Paquete where numero =  \"" + paquete + "\" and tipo_paquete = "+tipo_paquete+" and encuesta="+encuesta);
         rs.next();
         return rs.getString(1);
      }
      catch(SQLException e) {
         System.out.println(e.getMessage());
         return "";
      }
    }

    private String ini(String tipo) {
        switch(Integer.valueOf(tipo)){
                case Variables.C_COMUNIDAD:
                    return "C";
                case Variables.C_SERVICIOS:
                    return "S";
                case Variables.C_HOMBRE:
                    return "H";
                case Variables.C_MUJER:
                    return "M";
                case Variables.C_HOGAR :
                    return "";
        };
       return "";
    }
}

