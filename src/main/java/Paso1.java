import modelos.Empleado;
import repositorios.EmpleadoRepo;
import util.ConexionBD;

import java.sql.*;
import java.util.List;

public class Paso1 {
    public static void main(String[] args) throws SQLException {

        EmpleadoRepo repoEmpl = new EmpleadoRepo();

        System.out.println("------------ Lista de empleados ---------------------");
        List<Empleado> listEmpleados = repoEmpl.listarEmpleados();
        for (Empleado emp : listEmpleados) {
            System.out.println(emp.toString());
        };
    }
}
