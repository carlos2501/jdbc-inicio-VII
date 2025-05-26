import util.ConexionBD;

import java.sql.*;

public class Paso1 {
    public static void main(String[] args) throws SQLException {
        String qry7 = """
                SELECT DISTINCT c.nombre_cliente, e.nombre AS nombre_representante, o.ciudad
                FROM cliente c
                    JOIN empleado e ON c.codigo_empleado_rep_ventas = e.codigo_empleado
                    JOIN oficina o ON e.codigo_oficina = o.codigo_oficina
                ORDER BY e.nombre, o.ciudad
                """;
        String qry8 = """
                SELECT e.nombre AS empleado, j.nombre AS jefe
                FROM empleado e
                JOIN empleado j ON e.codigo_jefe = j.codigo_empleado
                ORDER BY e.nombre
                """;
        // Dentro de un try con recursos...
        // Creamos la instancia de la conexión a la BBDD
        // Creamos una instancia de sentencia para poder enviar consultas al servidor de BBDD
        // definimos la consulta, la enviamos la servidor (ejecutamos la sentencia) y obtenemos el resultado devuelto por el servidor
        // En el ResulSet tenemos todos los registros devueltos por el servidor dentro de un iterador

        Connection conex = ConexionBD.creaConexion();
        try {
            Consulta1(conex);
            Consulta2(conex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // ---------- Consulta 7 ----------------D
        try (
             Statement st = conex.createStatement();
             ResultSet rs = st.executeQuery(qry7)
            ) {
            System.out.println("----------- Consulta 7 ----------------");
            while (rs.next()) {
                System.out.println("Cliente: " + rs.getString(1) + " || Rep. ventas: " +
                        rs.getString(2) + " || Ciudad: " + rs.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // -----------  Consulta 8 ---------------------------
        try (
             Statement st = conex.createStatement();
             ResultSet rs = st.executeQuery(qry8)
        ) {
            System.out.println("----------- Consulta 8 ----------------");
            while (rs.next()) {
                System.out.println("Empleado: " + rs.getString(1) + " || Jefe: " +
                        rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       ConexionBD.close();
    }

    static void Consulta1(Connection conn) throws SQLException {
        String qry7 = """
                SELECT DISTINCT c.nombre_cliente, e.nombre AS nombre_representante, o.ciudad
                FROM cliente c
                    JOIN empleado e ON c.codigo_empleado_rep_ventas = e.codigo_empleado
                    JOIN oficina o ON e.codigo_oficina = o.codigo_oficina
                ORDER BY e.nombre, o.ciudad
                """;
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(qry7)
        ) {
            System.out.println("------------ CONSULTA 1 -----------------------");
            while (rs.next()) {
                System.out.println("Cliente: " + rs.getString(1) + " || Rep. ventas: " +
                        rs.getString(2) + " || Ciudad: " + rs.getString(3));
            }
        }
    }

    static void Consulta2(Connection conn) throws SQLException {
        String qry8 = """
                SELECT e.nombre AS empleado, j.nombre AS jefe
                FROM empleado e
                JOIN empleado j ON e.codigo_jefe = j.codigo_empleado
                ORDER BY e.nombre
                """;
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(qry8)
        ) {
            System.out.println("------------ CONSULTA 2 -----------------------");
            while (rs.next()) {
                System.out.println("Empleado: " + rs.getString(1) + " || Jefe: " +
                        rs.getString(2));
            }
        }
    }
}
