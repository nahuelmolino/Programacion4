package UTN.datos;

import static UTN.conexion.Conexion.getConnection;
import UTN.conexion.Conexion;
import UTN.dominio.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
    //Método listar
    public List<Estudiante> listarEstudiantes(){
        List<Estudiante> estudiantes = new ArrayList<>();
        //Creamos algunos objetos que son necesarios para comunicarnos con la base de datos.
        PreparedStatement ps;//Envia la sentencia a la bd
        ResultSet rs; // Obtenemos el resultado de la base de datos
        //creamos un objeto de tipo conexión.
        Connection con = getConnection();
        String sql = "SELECT * from estudiantes.estudiantes ORDER BY idestudiantes2022";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                var estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("idestudiantes2022"));
                estudiante.setNombre(rs.getNString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setEmail(rs.getString("email"));
                estudiante.setTelefono(rs.getString("telefono"));
                //falta agregarlo a la lista.
                estudiantes.add(estudiante);
            }
        } catch (Exception e){
            System.out.println("Ocurrio un error al selecionar datos." + e.getMessage());
        } finally {
            //siempre cierro la conexion
            try{
                con.close();
            } catch (Exception e){
                System.out.println("Ocurrio un error al cerrar la conexion." + e.getMessage());
            }
        }//fin finally
        return estudiantes;

    } //fin metodo Listar.

    //Método para buscar por id -> find by id

    public boolean buscarEstudiantePorId(Estudiante estudiante) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConnection();
        String sql = "SELECT * FROM estudiantes.estudiantes WHERE idestudiantes2022 =?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            rs = ps.executeQuery();
            if (rs.next()) {
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                return true; //Se encontro un registro.

            }// Fin if
        } catch (Exception e) {
            System.out.println("Ocurrió un error al buscar estudiante" + e.getMessage());


        }//fin cath
        finally {
            try {
                con.close();
            }//fin try
            catch (Exception e) {
                System.out.println("Error al cerrar la conexion" + e.getMessage());
            }//fin catch
        } //fin finally
        return  false;
    }

    public static void main(String[] args) {
        //Listar los estudiantes
        var estudianteDao = new EstudianteDAO();
        System.out.println("Listado de estudiantes");
        List <Estudiante> estudiantes = estudianteDao.listarEstudiantes();
        estudiantes.forEach(System.out::println);//Funcion lambda para imprimir.

    }
}
