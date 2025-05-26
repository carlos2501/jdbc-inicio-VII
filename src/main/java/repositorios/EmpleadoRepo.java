package repositorios;


import modelos.Empleado;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Est clase representa un repositorio para gestionar todas las operaciones con la BBDD que se realicen con la
 * tabla de empleado.
 * Debe proporcionar todos los métodos necesarios para manejar la información requerida por las especificaciones de la
 * aplicación
 */
public class EmpleadoRepo {

    private static final String LISTAR_EMPLEADOS = "SELECT * FROM empleado";

    private Connection obtenerConexion() throws SQLException {
        return ConexionBD.creaConexion();
    }

    private Statement obtenerSentencia() throws SQLException {
        return obtenerConexion().createStatement();
    }

    public Empleado leerEmpleado(int id) throws SQLException {
        Empleado empleado = new Empleado();
        return empleado;
    }

    public List<Empleado> listarEmpleados() {
        // Creamos la variable que conterndrá los datos de los empleados
        List<Empleado> empleados = new ArrayList<>();
        // ejecutamos la consulta dentro de un try con recursos para asegurarnos el cierre de estos
        try(Statement stmt = obtenerSentencia();
            ResultSet rs = stmt.executeQuery(LISTAR_EMPLEADOS)) {
            // Recorremos el resulset para asignar cada registro a un empleado
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setCodigoEmpleado(rs.getInt("codigo_empleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido1(rs.getString("apellido1"));
                empleado.setApellido2(rs.getString("apellido2"));
                empleado.setEmail(rs.getString("email"));
                empleado.setExtension(rs.getString("extension"));
                empleado.setCodigoOficina(rs.getString("codigo_oficina"));
                empleado.setCodigoJefe(rs.getInt("codigo_jefe"));
                empleado.setPuesto(rs.getString("puesto"));
                empleados.add(empleado);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Es mejor práctica visualizar el error con logs.
        }
        return empleados;
    }
}
