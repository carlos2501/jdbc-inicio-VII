import modelos.Empleado;
import modelos.Oficina;
import repositorios.EmpleadoRepo;
import repositorios.OficinaRepo;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class Paso1 {
    public static void main(String[] args) throws SQLException {

        EmpleadoRepo repoEmpl = new EmpleadoRepo();
        OficinaRepo repoOficina = new OficinaRepo();

        System.out.println("------------ Lista de empleados ---------------------");
        List<Empleado> listEmpleados = repoEmpl.listarEmpleados();
        for (Empleado emp : listEmpleados) {
            System.out.println(emp.toString());
        }
        System.out.println("------------ Lista de Oficinas ---------------------");
        List<Oficina> listaOfis = repoOficina.listarOficinas();
        for (Oficina oficina : listaOfis) {
            System.out.println(oficina.toString());
        }
        System.out.println("------------ leer un empleado por su id (5) ----------------");
        Optional<Empleado> emp = repoEmpl.leerEmpleado(5);
        // Si el empleado está vacío, poner un mensaje
        if(emp.isPresent()) {
            System.out.println(emp.get().toString());
        }
        System.out.println(emp.toString());
        // con estilo de programación funcional
        emp.ifPresent(empleado -> System.out.println(empleado.toString()));
        System.out.println(emp.toString());
    }
}
